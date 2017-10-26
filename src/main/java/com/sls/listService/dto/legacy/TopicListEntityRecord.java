package com.sls.listService.dto.legacy;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.sls.listService.DataList;
import com.sls.listService.DataListEntity;
import com.sls.listService.DataListEntityProperty;
import com.sls.listService.dto.DataListEntityRecordProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TopicListEntityRecord {

    private String name;

    private String caseType;

    @JsonUnwrapped
    private List<HashMap<String, String>> topicListItems = new ArrayList<>();

    public static TopicListEntityRecord create(DataListEntity listEntity) {

        ArrayList<HashMap<String, String>> entityRecordList = new ArrayList<>();
        if (listEntity.getSubEntities() != null && !listEntity.getSubEntities().isEmpty()) {
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

        return new TopicListEntityRecord(listEntity.getText(), property.getValue(), entityRecordList);
    }


    public static TopicListEntityRecord[] asArray(DataList list) {

        List<TopicListEntityRecord> entities = new ArrayList<>();
        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            entities = list.getEntities()
                    .stream()
                    .map(TopicListEntityRecord::create)
                    .collect(Collectors.toList());
        }

        return entities.toArray(new TopicListEntityRecord[0]);

    }

}
