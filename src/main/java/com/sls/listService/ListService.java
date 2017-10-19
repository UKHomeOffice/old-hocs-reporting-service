package com.sls.listService;

import com.sls.listService.dto.DataListEntityRecord;
import com.sls.listService.dto.DataListEntityRecordProperty;
import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.LegacyDataListEntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@RestController
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
            return asDataListRecord(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public LegacyDataListEntityRecord[] getLegacyListByName(String name) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByName(name);
            return asLegacyDataListEntityRecordArray(list);
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

    private static DataListRecord asDataListRecord(DataList list) {
        List<DataListEntityRecord> entities = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            entities = list.getEntities().stream().map(ListService::asDataListEntityRecord).collect(Collectors.toList());
        }
        return new DataListRecord(list.getName(), entities);
    }

    private static DataListEntityRecord asDataListEntityRecord(DataListEntity listEntity) {
        List<DataListEntityRecordProperty> properties = new ArrayList<>();
        if (listEntity.getProperties() != null && !listEntity.getProperties().isEmpty()) {
            properties = listEntity.getProperties()
                    .stream()
                    .map(r -> new DataListEntityRecordProperty(r.getKey(), r.getValue()))
                    .collect(Collectors.toList());
        }

        List<DataListEntityRecord> entityRecords = new ArrayList<>();
        if (listEntity.getSubEntities() != null && !listEntity.getSubEntities().isEmpty()) {
            entityRecords = listEntity.getSubEntities()
                    .stream()
                    .map(ListService::asDataListEntityRecord)
                    .collect(Collectors.toList());
        }

        return new DataListEntityRecord(listEntity.getText(), listEntity.getValue(), properties, entityRecords);
    }

    private static LegacyDataListEntityRecord[] asLegacyDataListEntityRecordArray(DataList list) {

        List<LegacyDataListEntityRecord> entities = new ArrayList<>();
        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            entities = list.getEntities()
                    .stream()
                    .map(ListService::asLegacyDataListEntityRecord)
                    .collect(Collectors.toList());
        }

        return  entities.toArray(new LegacyDataListEntityRecord[0]);

    }

    private static LegacyDataListEntityRecord asLegacyDataListEntityRecord(DataListEntity listEntity) {

        ArrayList<HashMap<String, String>> entityRecordList = new ArrayList<>();
        if(listEntity.getSubEntities() != null && !listEntity.getSubEntities().isEmpty()) {
            List<DataListEntity> entityList = new ArrayList<>(listEntity.getSubEntities());
            for (DataListEntity subEntity : entityList) {

                HashMap<String, String> entityRecords = new HashMap<>();
                entityRecords.put("topicName", subEntity.getText());
                entityRecords.put("topicUnit", subEntity.getValue());
                entityRecords.putAll(subEntity.getProperties()
                        .stream()
                        .map(r -> new DataListEntityRecordProperty(r.getKey(), r.getValue()))
                        .collect(Collectors.toMap(DataListEntityRecordProperty::getKey, DataListEntityRecordProperty::getValue)));

                entityRecordList.add(entityRecords);

            }
        }

        DataListEntityProperty property = new DataListEntityProperty();
        if (listEntity.getProperties() != null && !listEntity.getProperties().isEmpty())
            property = listEntity.getProperties()
                    .stream()
                    .filter(p -> p.getKey().equals("caseType"))
                    .findFirst().orElse(new DataListEntityProperty());

        return new LegacyDataListEntityRecord(listEntity.getText(), property.getValue(), entityRecordList);
    }

    private static List<CSVLine> parseDCUFile(MultipartFile file) {
        List<CSVLine> result = new ArrayList<>();

        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(",");
                String parentTopic = lineArray[0].trim();
                String topicName = lineArray[1].trim();
                String topicTeam = lineArray[2].trim();
                String topicUnit = lineArray[3].trim();
                result.add(new CSVLine(parentTopic, topicName, topicUnit, topicTeam));
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    private static List<CSVLine> parseUKVIFile(MultipartFile file) {
        List<CSVLine> result = new ArrayList<>();

        //TODO

        return result;
    }

    private static DataList createTopicsListFromCSV(List<CSVLine> lines, String listName, String caseType) {
        Map<String, Set<DataListEntity>> topics = new HashMap<>();
        for (CSVLine line : lines.subList(1, lines.size())) {
            if (!topics.containsKey(line.getParentTopic())) {
                Set<DataListEntity> entityList = new HashSet<>();
                topics.put(line.getParentTopic(), entityList);
            }
            Set<DataListEntityProperty> properties = new HashSet<>();
            properties.add(new DataListEntityProperty("topicUnit", line.getTopicUnit()));
            properties.add(new DataListEntityProperty("topicTeam", line.getTopicTeam()));
            DataListEntity entity = new DataListEntity(line.getTopicName(), CSVLine.toListValue(line.getTopicName()), new HashSet<>(), properties);
            topics.get(line.getParentTopic()).add(entity);
        }

        Set<DataListEntity> dataListEntities = new HashSet<>();
        for (Map.Entry<String, Set<DataListEntity>> entity : topics.entrySet()) {
            String parentTopic = entity.getKey();

            Set<DataListEntityProperty> properties = new HashSet<>();
            properties.add(new DataListEntityProperty("caseType", caseType));

            dataListEntities.add(new DataListEntity(parentTopic, CSVLine.toListValue(parentTopic), entity.getValue(), properties));
        }

        return new DataList(listName, dataListEntities);
    }

    public DataList createDCUTopicsListFromCSV(MultipartFile file, String listName, String caseType) {
        List<CSVLine> lines = parseDCUFile(file);

        return createTopicsListFromCSV(lines, listName, caseType);
    }

    public DataList createUKVITopicsListFromCSV(MultipartFile file, String listName, String caseType) {
        List<CSVLine> lines = parseUKVIFile(file);

        return createTopicsListFromCSV(lines, listName, caseType);
    }
}