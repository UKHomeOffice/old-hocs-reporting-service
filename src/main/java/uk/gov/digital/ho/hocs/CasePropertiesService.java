package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.util.Set;

@Service
@Slf4j
public class CasePropertiesService {

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
                throw e;
            }
        }
    }

    public Set<CaseProperties> getAllProperties() {
        return casePropertiesRepository.findAllBy();
    }
}