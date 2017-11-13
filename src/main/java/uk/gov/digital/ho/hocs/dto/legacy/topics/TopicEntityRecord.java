package uk.gov.digital.ho.hocs.dto.legacy.topics;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        for (DataListEntity subEntity : listEntity.getSubEntities()) {

            // They have one sub level
            HashMap<String, String> topicListItem = new HashMap<>();
            topicListItem.put("topicName", subEntity.getText());
            topicListItem.put("topicUnit", subEntity.getValue());
            topicListItems.add(topicListItem);
        }

        // a topiclist must have a caseType which we store as a property at the top level
        DataListEntityProperty property = listEntity.getProperties()
                .stream()
                .filter(p -> p.getKey().equals("caseType"))
                .findFirst().orElse(new DataListEntityProperty("caseType", ""));

        return new TopicEntityRecord(listEntity.getText(), property.getValue(), topicListItems);
    }

}