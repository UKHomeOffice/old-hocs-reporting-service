package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
public class CaseCurrentPropertiesService {

    private final CaseCurrentPropertiesRepository currentPropertiesRepository;

    public CaseCurrentPropertiesService(CaseCurrentPropertiesRepository caseCurrentPropertiesRepository) {
        this.currentPropertiesRepository = caseCurrentPropertiesRepository;
    }

    public void createCurrentProperties(Event event) throws EntityCreationException {
        try {
            CaseCurrentProperties caseCurrentProperties = currentPropertiesRepository.findByCaseRef(event.getCaseReference());

            if(caseCurrentProperties == null) {
                CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
                currentPropertiesRepository.save(caseProperties);
            } else {
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

    public Set<CaseCurrentProperties> getCurrentProperties(LocalDate start, LocalDate end, Set<String> correspondenceTypes) {
        return currentPropertiesRepository.getAllByTimestampBetweenAndCorrespondenceTypeIn(start, end, correspondenceTypes);
    }
}