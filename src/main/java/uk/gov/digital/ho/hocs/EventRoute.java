package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;

import java.io.IOException;

@Slf4j
@Profile("rabbitmq")
@Controller
public class EventRoute {
    private final EventService eventService;
    private final CasePropertiesService casePropertiesService;
    private final CaseCurrentPropertiesService caseCurrentPropertiesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public EventRoute(EventService eventService, CasePropertiesService casePropertiesService, CaseCurrentPropertiesService caseCurrentPropertiesService) {
        this.eventService = eventService;
        this.casePropertiesService = casePropertiesService;
        this.caseCurrentPropertiesService = caseCurrentPropertiesService;
    }

    @RabbitListener(queues = "#{eventReceivingQueue.name}")
    public void receiveEvent(byte[] message){
        try {
            Event event = objectMapper.readValue(new String(message), Event.class);
            log.info("Writing Event \"{}\"", event.getUuid());
            try {
                eventService.createEvent(event);
                casePropertiesService.createProperties(event);
                caseCurrentPropertiesService.createCurrentProperties(event);
                } catch (EntityCreationException e) {
                    log.info("Event \"{}\" not written", event.getUuid());
                    log.info(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
