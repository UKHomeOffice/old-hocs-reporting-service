package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.LegacyDataListEntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ListResource {
    final ListService service;

    @Autowired
    public ListResource(ListService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list/{reference}", method = RequestMethod.GET)
    public ResponseEntity<DataListRecord> getListByReference(@PathVariable("reference") String reference) {
        log.info("List \"{}\" requested", reference);
        try {
            DataListRecord list = service.getListByReference(reference);
            return ResponseEntity.ok(list);
        } catch (ListNotFoundException e)
        {
            log.info("List \"{}\" not found", reference);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/legacy/list/{reference}", method = RequestMethod.GET)
    public ResponseEntity<LegacyDataListEntityRecord[]> getLegacyListByReference(@PathVariable("reference") String reference) {
        log.info("List \"{}\" requested", reference);
        try {
            LegacyDataListEntityRecord[] list = service.getLegacyListByReference(reference);
            return ResponseEntity.ok(list);
        } catch (ListNotFoundException e)
        {
            log.info("List \"{}\" not found", reference);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity createList(@RequestBody DataList dataList) {
        log.info("Creating list \"{}\"", dataList.getReference());
        try {
            service.createList(dataList);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("List \"{}\" not created", dataList.getReference());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}