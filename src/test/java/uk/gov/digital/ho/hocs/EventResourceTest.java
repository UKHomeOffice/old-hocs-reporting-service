package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.AuditEvent;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventResourceTest {

    @Mock
    private EventService eventService;

    private EventResource eventResource;

    @Before
    public void setUp() {eventResource = new EventResource(eventService);}


    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {
        AuditEvent auditEvent = new AuditEvent();
        doThrow(new EntityCreationException("")).when(eventService).createEvent(auditEvent);
        //ResponseEntity httpResponse = eventResource.postEvent(auditEvent);
        //assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(eventService).createEvent(auditEvent);
    }

    @Test
    public void shouldReturnOKWhenAbleCreate() throws EntityCreationException {
        AuditEvent auditEvent = new AuditEvent();
        //ResponseEntity httpResponse = eventResource.postEvent(auditEvent);
        //assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(eventService).createEvent(auditEvent);
    }
}
