package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepository mockEventRepo;


    private EventService eventService;

    @Before
    public void setUp() {
        eventService = new EventService(mockEventRepo);
    }

    @Test
    /*
     * Test that an event is saved as event and property
     */
    public void testCreateEvent() {
        Event event = getValidEvent();

        eventService.createEvent(event);

        verify(mockEventRepo).save(any(AuditEvent.class));
    }

    @Test()
    public void testRepoDataIntegrityExceptionThrowsEntityCreationExceptionEvent() {
        Event event = getValidEvent();

        when(mockEventRepo.save(any(AuditEvent.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "event_id_idempotent")));

        eventService.createEvent(event);

        verify(mockEventRepo, times(1)).save(any(AuditEvent.class));
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityExceptionEvent() {
        Event event = getValidEvent();

        when(mockEventRepo.save(any(AuditEvent.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "other error")));

        eventService.createEvent(event);

        verify(mockEventRepo).save(any(AuditEvent.class));
    }


    private Event getValidEvent() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }

}