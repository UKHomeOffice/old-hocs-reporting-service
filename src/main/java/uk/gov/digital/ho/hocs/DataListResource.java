package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.digital.ho.hocs.dto.DataListRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.model.DataList;

@RestController
@Slf4j
public class DataListResource {
    private final DataListService dataListService;

    @Autowired
    public DataListResource(DataListService dataListService) {
        this.dataListService = dataListService;
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

}