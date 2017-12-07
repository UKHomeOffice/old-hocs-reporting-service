package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventServiceTest {

    @Mock
    private EventRepository mockEventRepo;

    private EventService eventService;

    @Before
    public void setUp() {
        eventService = new EventService(mockEventRepo);
    }

    @Test
    public void testCreateEvent() {
        AuditEvent auditEvent = new AuditEvent();

        eventService.createEvent(auditEvent);

        verify(mockEventRepo).save(auditEvent);
    }

    @Test(expected = EntityCreationException.class)
    public void testRepoDataIntegrityExceptionThrowsEntityCreationException() {
        AuditEvent auditEvent = new AuditEvent();

        when(mockEventRepo.save(auditEvent)).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "event_id_idempotent")));

        eventService.createEvent(auditEvent);

        verify(mockEventRepo).save(auditEvent);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityException() {
        AuditEvent auditEvent = new AuditEvent();

        when(mockEventRepo.save(auditEvent)). thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "")));

        eventService.createEvent(auditEvent);

        verify(mockEventRepo).save(auditEvent);
    }

}