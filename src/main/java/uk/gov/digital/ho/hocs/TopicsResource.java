package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicGroupRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.legacy.topics.DCUFileParser;
import uk.gov.digital.ho.hocs.legacy.topics.UKVIFileParser;

import java.util.List;

@RestController
@Slf4j
public class TopicsResource {
    private final TopicsService topicsService;

    @Autowired
    public TopicsResource(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    @RequestMapping(value = "topics/{name}", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromDCU(@RequestParam("file") MultipartFile file, @PathVariable("name") String name) {
        if (!file.isEmpty()) {
            log.info("Parsing list \"TopicListDCU\"");
            try {
                switch (name) {
                    case "DCU":
                        topicsService.createTopicsListFromCSV(new DCUFileParser(file), "DCU");
                        return ResponseEntity.ok().build();
                    case "UKVI":
                        topicsService.createTopicsListFromCSV(new UKVIFileParser(file), "UKVI");
                        return ResponseEntity.ok().build();
                }
            } catch (EntityCreationException e) {
                    log.info("{} groups not created", name);
                    log.info(e.getMessage());
                    return ResponseEntity.badRequest().build();
                }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/topics/topicList", method = RequestMethod.GET)
    public ResponseEntity<List<TopicGroupRecord>> getLegacyListByReference() {
        log.info("List \"Legacy TopicList\" requested");
        try {
            List<TopicGroupRecord> dcu = topicsService.getTopicByCaseType("DCU");
            List<TopicGroupRecord> ukvi = topicsService.getTopicByCaseType("UKVI");

            dcu.addAll(ukvi);
            return ResponseEntity.ok(dcu);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TopicList\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}