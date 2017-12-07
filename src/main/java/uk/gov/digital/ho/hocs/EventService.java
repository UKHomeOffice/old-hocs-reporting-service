package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;
import uk.gov.digital.ho.hocs.model.CaseType;

import javax.transaction.Transactional;

@Service
@Slf4j
public class EventService {
    private final EventRepository repo;

    @Autowired
    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void createEvent(AuditEvent auditEvent) throws EntityCreationException {
        try {
            repo.save(auditEvent);
            createCaseData(auditEvent);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("event_id_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }

    private void createCaseData(AuditEvent auditEvent) {
        if(auditEvent.getCaseType() != null) {
            switch (Enum.valueOf(CaseType.class, auditEvent.getCaseType())) {
                case DCU:

                    break;
                case FOI:
                    break;
                case HMPO:
                    break;
                case UKVI:
                    break;
                default:
                    throw new EntityCreationException("Unable to match caseType");
            }
        }
    }
}