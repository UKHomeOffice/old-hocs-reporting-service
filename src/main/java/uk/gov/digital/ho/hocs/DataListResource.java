package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.DataListRecord;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicListEntityRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.model.DataList;

import java.util.Arrays;

@RestController
@Slf4j
public class DataListResource {
    private final DataListService dataListService;
    private final LegacyService legacyService;

    @Autowired
    public DataListResource(DataListService dataListService, LegacyService legacyService) {
        this.dataListService = dataListService;
        this.legacyService = legacyService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity createList(@RequestBody DataList dataList) {
        log.info("Creating list \"{}\"", dataList.getName());
        try {
            dataListService.createList(dataList);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("List \"{}\" not created", dataList.getName());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
    public ResponseEntity<DataListRecord> getListByReference(@PathVariable("name") String name) {
        log.info("List \"{}\" requested", name);
        try {
            DataListRecord list = dataListService.getListByName(name);
            return ResponseEntity.ok(list);
        } catch (ListNotFoundException e)
        {
            log.info("List \"{}\" not found", name);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/legacy/topic/DCU", "/legacy/topic/UKVI"}, method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromDCU(@RequestParam("file") MultipartFile file, @PathVariable("name") String name) {
        log.info("Parsing list \"TopicListDCU\"");
        if (!file.isEmpty()) {
            switch (name) {
                case "DCU":
                    DataList dataListDCU = legacyService.createDCUTopicsListFromCSV(file, "DCU_Topics", "DCU");
                    return createList(dataListDCU);
                case "UKVI":
                    DataList dataListUKVI = legacyService.createUKVITopicsListFromCSV(file, "UKVI_Topics", "UKVI");
                    return createList(dataListUKVI);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = {"/legacy/topic/TopicList", "/service/homeoffice/ctsv2/topicList"}, method = RequestMethod.GET)
    public ResponseEntity<TopicListEntityRecord[]> getLegacyListByReference() {
        log.info("List \"Legacy TopicList\" requested");
        try {
            TopicListEntityRecord[] dcuList = legacyService.getLegacyTopicListByName("DCU_Topics");
            TopicListEntityRecord[] ukviList = legacyService.getLegacyTopicListByName("UKVI_Topics");

            return ResponseEntity.ok(concat(dcuList, ukviList));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TopicList\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}