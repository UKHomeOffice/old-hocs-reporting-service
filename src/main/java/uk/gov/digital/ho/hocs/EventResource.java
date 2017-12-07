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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@Slf4j
public class EventResource {
    private final EventService eventService;

    @Autowired
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }


    @RequestMapping(value = "/event/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity postEvent(@RequestBody Event event) {
        log.info("writing auditEvent {}", event.getUuid());

        String file = "messages.log";
        System.out.println("Writing to file: " + file);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file))) {
            writer.write(event.toString() + "\n");
        } catch (EntityCreationException e) {
            log.info("auditEvent not written {}", event.getUuid());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return ResponseEntity.ok("OK");
    }
}