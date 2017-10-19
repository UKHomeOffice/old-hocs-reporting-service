package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.LegacyDataListEntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@Slf4j
public class ListResource {
    private final ListService service;

    @Autowired
    public ListResource(ListService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
    public ResponseEntity<DataListRecord> getListByReference(@PathVariable("name") String name) {
        log.info("List \"{}\" requested", name);
        try {
            DataListRecord list = service.getListByName(name);
            return ResponseEntity.ok(list);
        } catch (ListNotFoundException e)
        {
            log.info("List \"{}\" not found", name);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity createList(@RequestBody DataList dataList) {
        log.info("Creating list \"{}\"", dataList.getName());
        try {
            service.createList(dataList);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("List \"{}\" not created", dataList.getName());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @RequestMapping(value = "/legacy/list/TopicList", method = RequestMethod.GET)
    public ResponseEntity<LegacyDataListEntityRecord[]> getLegacyListByReference() {
        log.info("List \"Legacy TopicList\" requested");
        try {
            LegacyDataListEntityRecord[] dcuList = service.getLegacyListByName("TopicListDCU");
            LegacyDataListEntityRecord[] ukviList = service.getLegacyListByName("TopicListUKVI");

            return ResponseEntity.ok(concat(dcuList, ukviList));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TopicList\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/legacy/list/TopicList/DCU", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromCSV(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"TopicListDCU\"");
        if (!file.isEmpty()) {
            DataList dataList = service.createDCUTopicsListFromCSV(file, "TopicListDCU", "DCU");
            return createList(dataList);
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/legacy/list/TopicList/UKVI", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromUKVI(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"TopicListUKVI\"");
        if (!file.isEmpty()) {
            DataList dataList = service.createUKVITopicsListFromCSV(file, "TopicListUKVI", "UKVI");
            return createList(dataList);
        }
        return ResponseEntity.badRequest().build();
    }
}