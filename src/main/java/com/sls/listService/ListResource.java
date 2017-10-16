package com.sls.listService;

import com.sls.listService.dto.DataListEntityRecord;
import com.sls.listService.dto.DataListRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ListResource {
    final ListRepository repo;

    @Autowired
    public ListResource(ListRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/list/{reference}", method = RequestMethod.GET)
    public DataListRecord getListByReference(@PathVariable("reference") String reference) {
        log.info("List \"{}\" requested", reference);
        DataList list = repo.findOneByReference(reference);
        return toDataList(list);
    }

    private DataListRecord toDataList(DataList list) {
        List<DataListEntityRecord> entities = new ArrayList<>();
        if(list.getEntities() != null) {
            entities = list.getEntities().stream().map(r -> asRecord(r)).collect(Collectors.toList());
        }
        return new DataListRecord(list.getReference(), entities);
    }

    private DataListEntityRecord asRecord(DataListEntity listEntity){
        List<DataListEntityRecord> entityRecords = new ArrayList<>();
        if(listEntity.getSubEntities() != null){
            entityRecords = listEntity.getSubEntities().stream().map(r -> asRecord(r)).collect(Collectors.toList());
        }
        return new DataListEntityRecord(listEntity.getValue(), listEntity.getReference(), entityRecords);
    }
}