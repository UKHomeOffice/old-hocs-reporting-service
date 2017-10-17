package com.sls.listService;

import com.sls.listService.dto.DataListEntityRecord;
import com.sls.listService.dto.DataListEntityRecordProperties;
import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.LegacyDataListEntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ListService {
    final ListRepository repo;

    @Autowired
    public ListService(ListRepository repo) {
        this.repo = repo;
    }


    public DataListRecord getListByReference(String reference) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByReference(reference);
            return toDataList(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public LegacyDataListEntityRecord[] getLegacyListByReference(String reference) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByReference(reference);
            return toLegacyDataList(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public void createList(DataList dataList) {
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

    private DataListRecord toDataList(DataList list) {
        List<DataListEntityRecord> entities = new ArrayList<>();

        if (list.getEntities() != null) {
            entities = list.getEntities().stream().map(this::asRecord).collect(Collectors.toList());
        }
        return new DataListRecord(list.getReference(), entities);
    }

    private DataListEntityRecord asRecord(DataListEntity listEntity) {
        List<DataListEntityRecordProperties> properties = new ArrayList<>();
        if (listEntity.getProperties() != null) {
            properties = listEntity.getProperties()
                    .stream()
                    .map(r -> new DataListEntityRecordProperties(r.getProperty(), r.getValue()))
                    .collect(Collectors.toList());
        }

        List<DataListEntityRecord> entityRecords = new ArrayList<>();
        if (listEntity.getSubEntities() != null) {
            entityRecords = listEntity.getSubEntities()
                    .stream()
                    .map(this::asRecord)
                    .collect(Collectors.toList());
        }

        return new DataListEntityRecord(listEntity.getValue(), listEntity.getReference(), entityRecords, properties);
    }

    private LegacyDataListEntityRecord[] toLegacyDataList(DataList list) {
        List<LegacyDataListEntityRecord> entities = new ArrayList<>();

        if (list.getEntities() != null) {
            entities = list.getEntities().stream().map(this::asLegacyRecord).collect(Collectors.toList());
        }
        return entities.toArray(new LegacyDataListEntityRecord[0]);
    }

    private LegacyDataListEntityRecord asLegacyRecord(DataListEntity listEntity) {

        HashMap<String, String> entityRecords = new HashMap<>();

        // A constraint, the legacy lists only support one depth.
        DataListEntity subEntity = new ArrayList<>(listEntity.getSubEntities()).get(0);
        entityRecords.put("topicName", subEntity.getValue());
        entityRecords.put("topicUnit", subEntity.getReference());
        if (subEntity.getProperties() != null) {
            entityRecords.putAll(subEntity.getProperties()
                    .stream()
                    .map(r -> new DataListEntityRecordProperties(r.getProperty(), r.getValue()))
                    .collect(Collectors.toMap(DataListEntityRecordProperties::getProperty, DataListEntityRecordProperties::getValue)));
        }
        ArrayList<HashMap<String, String>> entityRecordList = new ArrayList<>();
        entityRecordList.add(entityRecords);
        return new LegacyDataListEntityRecord(listEntity.getValue(), listEntity.getReference(), entityRecordList);
    }

}