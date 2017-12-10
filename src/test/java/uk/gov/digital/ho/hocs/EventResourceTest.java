package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventResourceTest {

    @Mock
    private EventService eventService;

    private EventResource eventResource;

    @Before
    public void setUp() {
        eventResource = new EventResource(eventService);}


    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {
        Event event = new Event();
        doThrow(new EntityCreationException("")).when(eventService).createEvent(event);
        ResponseEntity httpResponse = eventResource.postEvent(event);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(eventService).createEvent(event);
    }

    @Test
    public void shouldReturnOKWhenAbleCreate() throws EntityCreationException {
        Event event = new Event();
        ResponseEntity httpResponse = eventResource.postEvent(event);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(eventService).createEvent(event);
    }
}
