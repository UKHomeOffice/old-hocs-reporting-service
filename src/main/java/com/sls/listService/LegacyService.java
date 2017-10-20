package com.sls.listService;

import com.sls.listService.legacy.CSVLine;
import com.sls.listService.legacy.CSVList;
import com.sls.listService.legacy.DCUCSVList;
import com.sls.listService.legacy.UKVICSVList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<CSVLine> lines = list.getLines();
        for (CSVLine line : lines) {
            if (!topics.containsKey(line.getParentTopicName())) {
                Set<DataListEntity> entityList = new HashSet<>();
                topics.put(line.getParentTopicName(), entityList);
            }
            Set<DataListEntityProperty> properties = new HashSet<>();
            properties.add(new DataListEntityProperty("topicUnit", line.getTopicUnit()));
            if (!line.getTopicTeam().isEmpty()) {
                properties.add(new DataListEntityProperty("topicTeam", line.getTopicTeam()));
            }
            DataListEntity entity = new DataListEntity(line.getTopicName(), CSVLine.toSubListValue(caseType, line.getParentTopicName(), line.getTopicName()), new HashSet<>(), properties);
            topics.get(line.getParentTopicName()).add(entity);
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
        CSVList list = new DCUCSVList(file);
        return createTopicsListFromCSV(list, listName, caseType);
    }

    public DataList createUKVITopicsListFromCSV(MultipartFile file, String listName, String caseType) {
        CSVList list = new UKVICSVList(file);
        return createTopicsListFromCSV(list, listName, caseType);
    }
}
