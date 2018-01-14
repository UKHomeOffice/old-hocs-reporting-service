package uk.gov.digital.ho.hocs;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.EventService;
import uk.gov.digital.ho.hocs.EventRoute;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventRouteTest extends CamelTestSupport {

    private static final String commandQueue = "direct:commandQueue";

    private ObjectMapper mapper = new ObjectMapper();

    private final LocalDate startDate = LocalDate.now();
    private final LocalDate expiryDate = startDate.plusDays(7);

    @Mock
    private EventService mockEventService;

    @Override
    public RouteBuilder createRouteBuilder() {
        return new EventRoute(mockEventService,
                                commandQueue,
                                0, 0, 0);
    }

    @Test
    public void shouldFailToUnmarshallUnknownCommand() throws InterruptedException, JsonProcessingException {

        String notAnEvent = "{\"fffffffff\":\"fdf\"}";

        template.sendBody(commandQueue, notAnEvent);

        assertMockEndpointsSatisfied();

        verify(mockEventService, never()).createEvent(any());
    }

    @Test
    public void shouldNotMarkCommandAsProcessedWhenCommandFailure() throws JsonProcessingException, InterruptedException {

        Event event = new Event("1", null, "test", new HashMap<>());

        String commandJson = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(event);

        doThrow(EntityCreationException.class)
                .when(mockEventService).createEvent(any());

        assertThatThrownBy(() -> template.sendBody(commandQueue, commandJson))
                .isInstanceOf(CamelExecutionException.class)
                .hasCauseInstanceOf(EntityCreationException.class);

        assertMockEndpointsSatisfied();

        verify(mockEventService, times(1)).createEvent(any());
    }

    @Test
    public void shouldMarkCommandAsProcessedWhenCommandSucceeds() throws JsonProcessingException, InterruptedException {

        Event event = new Event("1", LocalDateTime.now(), "test", new HashMap<>());

        String commandJson = mapper.writeValueAsString(any());

        template.sendBody(commandQueue, commandJson);

        assertMockEndpointsSatisfied();

        verify(mockEventService, times(1)).createEvent(any());
    }
}