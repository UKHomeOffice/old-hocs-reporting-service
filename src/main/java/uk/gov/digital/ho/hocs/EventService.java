package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;
import uk.gov.digital.ho.hocs.model.CaseProperties;
import uk.gov.digital.ho.hocs.model.Event;

import javax.transaction.Transactional;

@Service
@Slf4j
public class EventService {
    private final AuditEventRepository auditEventRepository;
    private final CasePropertiesRepository propertiesRepository;

    @Autowired
    public EventService(AuditEventRepository eventRepo, CasePropertiesRepository propertiesRepo) {
        this.auditEventRepository = eventRepo;
        this.propertiesRepository = propertiesRepo;
    }

    @Transactional
    public void createEvent(Event event) throws EntityCreationException {
        try {
            createAuditData(event);
            createCaseData(event);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    (((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("event_id_idempotent") ||
                     ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("properties_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            }
            else {
                throw e;
            }
        }
    }

    private void createAuditData(Event event) {
        AuditEvent auditEvent = new AuditEvent(event);
        auditEventRepository.save(auditEvent);
    }

    private void createCaseData(Event event) {
        CaseProperties caseProperties = new CaseProperties(event);
        propertiesRepository.save(caseProperties);
    }
}