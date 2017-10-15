package com.sls.listService;

import com.sls.listService.dto.DataListEntityRecord;
import com.sls.listService.dto.DataListRecord;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log
public class ListResource {
    final ListRepository repo;

    @Autowired
    public ListResource(ListRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/list/{reference}", method = RequestMethod.GET)
    public DataListRecord getListByReference(@PathVariable("reference") String reference) {
        log.info(String.format("DataListRecord {0} requested", reference));
        DataList list = repo.findOneByReference(reference);

        return toDataList(list);
    }

    private DataListRecord toDataList(DataList list) {
        List<DataListEntityRecord> entities =  list.getEntities().stream().map(m -> new DataListEntityRecord(m.getValue(),m.getReference(), null)).collect(Collectors.toList());
        return new DataListRecord(list.getReference(), entities);
    }
}