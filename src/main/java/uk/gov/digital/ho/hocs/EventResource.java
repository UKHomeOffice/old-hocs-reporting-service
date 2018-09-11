package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.Event;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/event/")
public class EventResource {
    private final CasePropertiesService casePropertiesService;
    private final CaseCurrentPropertiesService caseCurrentPropertiesService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EventResource(CasePropertiesService casePropertiesService, CaseCurrentPropertiesService caseCurrentPropertiesService) {
        this.casePropertiesService = casePropertiesService;
        this.caseCurrentPropertiesService = caseCurrentPropertiesService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/*")
    public ResponseEntity postEvent(@RequestBody String eventString) {
        log.info(eventString);
        Event event = null;
        try {
            event = objectMapper.readValue(eventString, Event.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        log.info("Writing Event \"{}\"", event.getUuid());
        try {
            casePropertiesService.createProperties(event);
            caseCurrentPropertiesService.createCurrentProperties(event);
            caseCurrentPropertiesService.createTaskEntryDetails(event);
            return ResponseEntity.ok("OK");
        } catch (EntityCreationException e) {
            log.info("Event \"{}\" not written", event.getUuid());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
