package uk.gov.digital.ho.hocs.dto.legacy.topics;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.dto.DataListEntityRecordProperty;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class TopicEntityRecord {

    private String name;

    private String caseType;

    @JsonUnwrapped
    private List<HashMap<String, String>> topicListItems = new ArrayList<>();

    public static TopicEntityRecord create(DataListEntity listEntity) {
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

        return new TopicEntityRecord(listEntity.getText(), property.getValue(), topicListItems);
    }

}