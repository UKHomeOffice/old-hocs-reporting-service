package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;


@RestController
@Slf4j
public class EventResource {
    private final EventService eventService;

    @Autowired
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }


    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public ResponseEntity postEvent(@RequestBody Event event) {
        log.info("writing event {}", event.getUuid());
        try {
            eventService.createEvent(event);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("event not written {}", event.getUuid());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}