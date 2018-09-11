package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseProperties;
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
public class CasePropertiesService {

    private static HashMap<String,String[]> caseTypesMapping;

    // This code should be in the data-service but we also want to get rid of getCurrentProperties code.
    static {
        caseTypesMapping = new HashMap<>();
        caseTypesMapping.put("DCU", new String[]{"MIN","TRO","DTEN"});
        caseTypesMapping.put("UKVI", new String[]{"IMCB","IMCM","UTEN"});
        caseTypesMapping.put("FOI", new String[]{"FOI", "FTC", "FTCI", "FSC", "FSCI"});
        caseTypesMapping.put("HMPOCOR", new String[]{"COM","COM1","COM2","DGEN", "GNR"});
        caseTypesMapping.put("HMPOCOL", new String[]{"COL"});
    }

    private final CasePropertiesRepository casePropertiesRepository;

    @Autowired
    public CasePropertiesService(CasePropertiesRepository casePropertiesRepository) {
        this.casePropertiesRepository = casePropertiesRepository;
    }

    public void createProperties(Event event) throws EntityCreationException {
        try {
            CaseProperties caseProperties = new CaseProperties(event);
            casePropertiesRepository.save(caseProperties);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ( ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("properties_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            }
            else {
                log.error(e.getMessage());
                throw e;
            }
        }
    }

    Set<CaseProperties> getProperties(String unit, String cutoff) {
        return getProperties(unit, LocalDate.parse(cutoff), 4);
    }

    Set<CaseProperties> getProperties(String unit, LocalDate localDateCutOff, int monthsBack) {

        String[] correspondenceTypes = caseTypesMapping.get(unit);

        if (correspondenceTypes == null) {
            log.error("Unit {} not found", unit);
            return new HashSet<>();
        } else {


            log.info("Fetching All Properties for \"{}\" up to {}", Arrays.toString(correspondenceTypes), localDateCutOff.toString());

            LocalDateTime today = LocalDateTime.of(localDateCutOff, LocalTime.MAX);

            // Start at the first day of the month
            LocalDateTime start = LocalDateTime.of(localDateCutOff.minusMonths(monthsBack).getYear(),
                    localDateCutOff.minusMonths(monthsBack).getMonth(),
                    1,0,0);

            return casePropertiesRepository.getAllByTimestampBetweenAndCorrespondenceTypeIn(start, today, correspondenceTypes);
        }
    }

    Set<CaseProperties> getAllProperties(String unit) {

        String[] correspondenceTypes = caseTypesMapping.get(unit);

        if (correspondenceTypes == null) {
            log.error("Unit {} not found", unit);
            return new HashSet<>();
        } else {


            log.info("Fetching All Properties for \"{}\"", Arrays.toString(correspondenceTypes));


            return casePropertiesRepository.getAllCorrespondenceTypeIn(correspondenceTypes);
        }
    }

    Set<CaseProperties> getAllCaseProperties(String caseRef) {

            log.info("Fetching All Properties for Case \"{}\"", caseRef);

            return casePropertiesRepository.getAllByCaseRef(caseRef);
    }

}