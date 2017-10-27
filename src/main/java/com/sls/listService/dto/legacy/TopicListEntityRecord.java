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
        ArrayList<HashMap<String, String>> topicListItems = new ArrayList<>();

        // subEntities are called topicListItems
        if (listEntity.getSubEntities() != null && !listEntity.getSubEntities().isEmpty()) {
            for (DataListEntity subEntity : listEntity.getSubEntities()) {

                // They have one sub level with many fields so combine the sub entity and properties into one map
                HashMap<String, String> topicListItem = new HashMap<>();
                topicListItem.put("topicName", subEntity.getText());
                topicListItem.put("topicUnit", subEntity.getValue());

                topicListItem.putAll(subEntity.getProperties()
                        .stream()
                        .map(r -> new DataListEntityRecordProperty(r.getKey(), r.getValue()))
                        .collect(Collectors.toMap(DataListEntityRecordProperty::getKey, DataListEntityRecordProperty::getValue)));
                topicListItems.add(topicListItem);
            }
        }

        // a topiclist must have a caseType which we store as a property at the top level
        DataListEntityProperty property = new DataListEntityProperty();
        if (listEntity.getProperties() != null && !listEntity.getProperties().isEmpty()) {
            property = listEntity.getProperties()
                    .stream()
                    .filter(p -> p.getKey().equals("caseType"))
                    .findFirst().orElse(new DataListEntityProperty());
        }

        return new TopicListEntityRecord(listEntity.getText(), property.getValue(), topicListItems);
    }

    // TopicList is only an array of records, no top level object.
    public static TopicListEntityRecord[] asArray(DataList list) {
        List<TopicListEntityRecord> topicList = new ArrayList<>();
        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            topicList = list.getEntities()
                    .stream()
                    .map(TopicListEntityRecord::create)
                    .collect(Collectors.toList());
        }

        return topicList.toArray(new TopicListEntityRecord[0]);
    }
}