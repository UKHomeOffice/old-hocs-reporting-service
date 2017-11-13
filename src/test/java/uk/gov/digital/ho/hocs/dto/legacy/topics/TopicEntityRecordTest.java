package uk.gov.digital.ho.hocs.dto.legacy.topics;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicEntityRecordTest {

    @Test
    public void create() throws Exception {
        DataListEntity topic = new DataListEntity("TopicName");
        DataListEntityProperty property = new DataListEntityProperty("caseType", "Type");
        Set<DataListEntityProperty> properties = new HashSet<>();
        properties.add(property);
        topic.setProperties(properties);
        DataListEntity subTopic = new DataListEntity("subTopic", "subUnit");
        Set<DataListEntity> subTopics = new HashSet<>();
        subTopics.add(subTopic);
        topic.setSubEntities(subTopics);

        TopicEntityRecord topicEntityRecord = TopicEntityRecord.create(topic);

        assertThat(topicEntityRecord.getName()).isEqualTo("TopicName");
        assertThat(topicEntityRecord.getCaseType()).isEqualTo("Type");
        assertThat(topicEntityRecord.getTopicListItems()).hasSize(1);
    }

    @Test
    public void createNoCaseType() throws Exception {
        DataListEntity topic = new DataListEntity("TopicName");
        DataListEntity subTopic = new DataListEntity("subTopic", "subUnit");
        Set<DataListEntity> subTopics = new HashSet<>();
        subTopics.add(subTopic);
        topic.setSubEntities(subTopics);

        TopicEntityRecord topicEntityRecord = TopicEntityRecord.create(topic);

        assertThat(topicEntityRecord.getName()).isEqualTo("TopicName");
        assertThat(topicEntityRecord.getCaseType()).isEqualTo("");
        assertThat(topicEntityRecord.getTopicListItems()).hasSize(1);
    }

    @Test
    public void createNoEntities() throws Exception {
        DataListEntity topic = new DataListEntity("TopicName");

        TopicEntityRecord topicEntityRecord = TopicEntityRecord.create(topic);

        assertThat(topicEntityRecord.getName()).isEqualTo("TopicName");
        assertThat(topicEntityRecord.getCaseType()).isEqualTo("");
        assertThat(topicEntityRecord.getTopicListItems()).hasSize(0);
    }

    @Test
    public void getStrings() throws Exception {
        List<HashMap<String, String>> topics = new ArrayList<>();

        HashMap<String, String> topic = new HashMap<>();
        topic.put("subTopic", "subUnit");
        topics.add(topic);

        TopicEntityRecord topicEntityRecord = new TopicEntityRecord("TopicName", "Type", topics);

        assertThat(topicEntityRecord.getName()).isEqualTo("TopicName");
        assertThat(topicEntityRecord.getCaseType()).isEqualTo("Type");
        assertThat(topicEntityRecord.getTopicListItems()).hasSize(1);
    }
}