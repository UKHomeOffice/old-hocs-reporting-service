package uk.gov.digital.ho.hocs.dto.legacy.topics;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.DataList;
import uk.gov.digital.ho.hocs.model.DataListEntity;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicRecordTest {

    @Test
    public void createWithEntities() throws Exception {
        Set<DataListEntity> entities = new HashSet<>();
        DataList dataList = new DataList("TEST List", entities);

        TopicRecord record = TopicRecord.create(dataList);
        assertThat(record.getTopics()).hasSize(0);
    }

}