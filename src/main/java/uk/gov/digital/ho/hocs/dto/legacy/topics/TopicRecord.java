package uk.gov.digital.ho.hocs.dto.legacy.topics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.DataList;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class TopicRecord {
    private List<TopicEntityRecord> topics;

    public static TopicRecord create(DataList list) {
        List<TopicEntityRecord> topicList = list.getEntities().stream().map(TopicEntityRecord::create).collect(Collectors.toList());
        return new TopicRecord(topicList);
    }
}