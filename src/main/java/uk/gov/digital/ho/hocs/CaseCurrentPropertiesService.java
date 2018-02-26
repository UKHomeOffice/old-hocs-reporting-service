package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CaseCurrentPropertiesService {

    private static HashMap<String,String[]> caseTypesMapping;

    // This code should be in the data-service but we also want to get rid of getCurrentProperties code.
    static {
        caseTypesMapping = new HashMap<>();
        caseTypesMapping.put("DCU", new String[]{"MIN","TRO","DTEN"});
        caseTypesMapping.put("UKVI", new String[]{"IMCB","IMCM","UTEN"});
        caseTypesMapping.put("FOI", new String[]{"FOI", "FTC", "FTCI", "FSC", "FSCI"});
        caseTypesMapping.put("HMPOCOR", new String[]{"COM","COM1","COM2","DGEN","GNR"});
        caseTypesMapping.put("HMPOCOL", new String[]{"COL"});
    }

    private final CaseCurrentPropertiesRepository currentPropertiesRepository;

    public CaseCurrentPropertiesService(CaseCurrentPropertiesRepository caseCurrentPropertiesRepository) {
        this.currentPropertiesRepository = caseCurrentPropertiesRepository;
    }

    void createCurrentProperties(Event event) throws EntityCreationException {
        try {
            CaseCurrentProperties caseCurrentProperties = currentPropertiesRepository.findByCaseReference(event.getCaseReference());

            if(caseCurrentProperties == null) {
                currentPropertiesRepository.save(new CaseCurrentProperties(event));
            } else if(event.getTimestamp().isAfter(caseCurrentProperties.getTimestamp())) {
                log.info("Entry already found, updating: " + event.getCaseReference());
                caseCurrentProperties.update(event);
                currentPropertiesRepository.save(caseCurrentProperties);
            }
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    (((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("current_properties_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            }
            else {
                throw e;
            }
        }
    }

    Set<CaseCurrentProperties> getCurrentProperties(String unit) {

        String[] correspondenceTypes = caseTypesMapping.get(unit);

        if (correspondenceTypes == null) {
            log.error("Unit {} not found", unit);
            return new HashSet<>();
        } else {

            log.info("Fetching All Current Properties for \"{}\"", Arrays.toString(correspondenceTypes));

            LocalDate now = LocalDate.now();
            LocalDateTime today = LocalDateTime.of(now, LocalTime.MAX);

            int monthsBack =4;
            // Start at the first day of the month
            LocalDateTime start = LocalDateTime.of(now.minusMonths(monthsBack).getYear(),
                                                   now.minusMonths(monthsBack).getMonth(),
                                                   1,0,0);

            return currentPropertiesRepository.getAllByTimestampBetweenAndCorrespondenceTypeIn(start, today, correspondenceTypes);
        }
    }


}