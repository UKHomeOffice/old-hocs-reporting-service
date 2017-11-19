package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testCreateEvent() {
        Event event = new Event();

        eventService.createEvent(event);

        verify(mockEventRepo).save(event);
    }

    @Test(expected = EntityCreationException.class)
    public void testRepoDataIntegrityExceptionThrowsEntityCreationException() {
        Event event = new Event();

        when(mockEventRepo.save(event)).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "event_id_idempotent")));

        eventService.createEvent(event);

        verify(mockEventRepo).save(event);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityException() {
        Event event = new Event();

        when(mockEventRepo.save(event)). thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "")));

        eventService.createEvent(event);

        verify(mockEventRepo).save(event);
    }

}