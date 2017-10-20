package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.LegacyDataListEntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListService {
    private final ListRepository repo;

    @Autowired
    public ListService(ListRepository repo) {
        this.repo = repo;
    }

    public DataListRecord getListByName(String name) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByName(name);
            return DataListRecord.create(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public LegacyDataListEntityRecord[] getLegacyListByName(String name) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByName(name);
            return LegacyDataListEntityRecord.asArray(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public void createList(DataList dataList) throws EntityCreationException {
        try {
            repo.save(dataList);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("list_name_idempotent") ||
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("entity_name_ref_idempotent") ||
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("entity_id_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }
}