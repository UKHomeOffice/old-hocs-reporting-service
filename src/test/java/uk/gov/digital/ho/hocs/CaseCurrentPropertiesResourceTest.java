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
public class CaseCurrentPropertiesResourceTest {

    @Mock
    private CasePropertiesService casePropertiesService;

    @Mock
    private CaseCurrentPropertiesService caseCurrentPropertiesService;

    private EventResource eventResource;


    @Before
    public void setUp() {
        eventResource = new EventResource(casePropertiesService, caseCurrentPropertiesService);
    }


    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {
        Event event = new Event();
        doThrow(new EntityCreationException("")).when(caseCurrentPropertiesService).createCurrentProperties(event);
        ResponseEntity httpResponse = eventResource.postEvent(event);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(caseCurrentPropertiesService).createCurrentProperties(event);
    }

    @Test
    public void shouldReturnOKWhenAbleCreate() throws EntityCreationException {
        Event event = new Event();
        ResponseEntity httpResponse = eventResource.postEvent(event);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(caseCurrentPropertiesService).createCurrentProperties(event);
    }
}
