package com.sls.listService;

import com.sls.listService.dto.legacy.TopicListEntityRecord;
import com.sls.listService.dto.legacy.UnitCreateRecord;
import com.sls.listService.legacy.CSVList;
import com.sls.listService.legacy.topics.CSVTopicLine;
import com.sls.listService.legacy.topics.DCUFileParser;
import com.sls.listService.legacy.topics.UKVIFileParser;
import com.sls.listService.legacy.units.CSVUnitLine;
import com.sls.listService.legacy.units.UnitFileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
public class LegacyService {

    private final ListRepository repo;

    @Autowired
    public LegacyService(ListRepository repo) {
        this.repo = repo;
    }


    private static DataList createTopicsListFromCSV(CSVList list, String listName, String caseType) {
        Map<String, Set<DataListEntity>> topics = new HashMap<>();

        List<CSVTopicLine> lines = list.getLines();
        for (CSVTopicLine line : lines) {
            if (!topics.containsKey(line.getParentTopicName())) {
                Set<DataListEntity> entityList = new HashSet<>();
                topics.put(line.getParentTopicName(), entityList);
            }
            Set<DataListEntityProperty> properties = new HashSet<>();
            properties.add(new DataListEntityProperty("topicUnit", line.getTopicUnit()));
            if (!line.getTopicTeam().isEmpty()) {
                properties.add(new DataListEntityProperty("topicTeam", line.getTopicTeam()));
            }
            DataListEntity entity = new DataListEntity(line.getTopicName(), CSVTopicLine.toSubListValue(caseType, line.getParentTopicName(), line.getTopicName()), new HashSet<>(), properties);
            topics.get(line.getParentTopicName()).add(entity);
        }

        Set<DataListEntity> dataListEntities = new HashSet<>();
        for (Map.Entry<String, Set<DataListEntity>> entity : topics.entrySet()) {
            String parentTopic = entity.getKey();

            Set<DataListEntityProperty> properties = new HashSet<>();
            properties.add(new DataListEntityProperty("caseType", caseType));

            dataListEntities.add(new DataListEntity(parentTopic, parentTopic, entity.getValue(), properties));
        }

        return new DataList(listName, dataListEntities);
    }

    @Cacheable(value = "legacylist", key = "#name")
    public UnitCreateRecord getLegacyUnitCreateListByName(String name) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByName(name);
            return UnitCreateRecord.create(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    @Cacheable(value = "legacylist", key = "#name")
    public TopicListEntityRecord[] getLegacyTopicListByName(String name) throws ListNotFoundException {
        try {
            DataList list = repo.findOneByName(name);
            return TopicListEntityRecord.asArray(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public DataList createDCUTopicsListFromCSV(MultipartFile file, String listName, String caseType) {
        return createTopicsListFromCSV(new DCUFileParser(file), listName, caseType);
    }

    public DataList createUKVITopicsListFromCSV(MultipartFile file, String listName, String caseType) {
        return createTopicsListFromCSV(new UKVIFileParser(file), listName, caseType);
    }

    public DataList createTeamsUnitsFromCSV(MultipartFile file, String listName) {
        CSVList list = new UnitFileParser(file);

        Map<String, Set<DataListEntity>> units = new HashMap<>();

        List<CSVUnitLine> lines = list.getLines();

        for (CSVUnitLine line : lines) {
            if (!units.containsKey(line.getUnit())) {
                Set<DataListEntity> entityList = new HashSet<>();
                units.put(line.getUnit(), entityList);
            }
            DataListEntity entity = new DataListEntity(line.getTeam(), CSVUnitLine.toSubListValue(line.getUnit(), line.getTeam()));
            units.get(line.getUnit()).add(entity);
        }

        Set<DataListEntity> dataListEntities = new HashSet<>();
        for (Map.Entry<String, Set<DataListEntity>> entity : units.entrySet()) {
            String unit = entity.getKey();

            dataListEntities.add(new DataListEntity(unit, unit, entity.getValue()));
        }


        return new DataList(listName, dataListEntities);
    }
}
