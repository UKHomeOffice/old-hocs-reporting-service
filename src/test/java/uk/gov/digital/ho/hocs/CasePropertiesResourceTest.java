package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CasePropertiesResourceTest {

    @Mock
    private CasePropertiesService casePropertiesService;

    @Mock
    private CaseCurrentPropertiesService caseCurrentPropertiesService;

    private EventResource eventResource;

    @Before
    public void setUp() {
        eventResource = new EventResource(casePropertiesService, caseCurrentPropertiesService);}


    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {
        Event event = new Event();
        doThrow(new EntityCreationException("")).when(casePropertiesService).createProperties(any(Event.class));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = objectMapper.writeValueAsString(event);
            ResponseEntity httpResponse = eventResource.postEvent(msg);
            assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        verify(casePropertiesService).createProperties(any(Event.class));
    }

    @Test
    public void shouldReturnOKWhenAbleCreate() throws EntityCreationException {
        Event event = new Event();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String msg = objectMapper.writeValueAsString(event);
            ResponseEntity httpResponse = eventResource.postEvent(msg);
            assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        verify(casePropertiesService).createProperties(any(Event.class));
    }
}
