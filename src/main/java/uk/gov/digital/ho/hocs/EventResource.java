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


@Slf4j
@RestController
@RequestMapping(value = "/event/")
public class EventResource {
    private final EventService eventService;
    private final CasePropertiesService casePropertiesService;
    private final CaseCurrentPropertiesService caseCurrentPropertiesService;

    @Autowired
    public EventResource(EventService eventService, CasePropertiesService casePropertiesService, CaseCurrentPropertiesService caseCurrentPropertiesService) {
        this.eventService = eventService;
        this.casePropertiesService = casePropertiesService;
        this.caseCurrentPropertiesService = caseCurrentPropertiesService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity postEvent(@RequestBody Event event) {
        log.info("Writing Event \"{}\"", event.getUuid());
        try {
            eventService.createEvent(event);
            casePropertiesService.createProperties(event);
            caseCurrentPropertiesService.createCurrentProperties(event);
            return ResponseEntity.ok("OK");
        } catch (EntityCreationException e) {
            log.info("Event \"{}\" not written", event.getUuid());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
