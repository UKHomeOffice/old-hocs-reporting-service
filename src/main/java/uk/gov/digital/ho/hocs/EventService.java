package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.Event;

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
    public void createEvent(Event event) throws EntityCreationException {
        try {
            repo.save(event);
            createCaseData(event);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("event_id_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }

    private void createCaseData(Event event) {
        if(event.getCaseType() != null) {
            switch (Enum.valueOf(CaseType.class, event.getCaseType())) {
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