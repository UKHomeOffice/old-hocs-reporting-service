package uk.gov.digital.ho.hocs.dto;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.DataList;
import uk.gov.digital.ho.hocs.model.DataListEntity;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DataListRecordTest {

    @Test
    public void createWithNoEntities() throws Exception {
        Set<DataListEntity> entities = new HashSet<>();
        DataList datalist = new DataList("TEST List", entities);
        DataListRecord record = DataListRecord.create(datalist);

        assertThat(record.getName()).isEqualTo(datalist.getName());
        assertThat(record.getEntities()).isEmpty();
    }

    @Test
    public void createWithEntities() throws Exception {
        Set<DataListEntity> entities = new HashSet<>();
        entities.add(new DataListEntity("entity1"));

        DataList datalist = new DataList("TEST List", entities);
        DataListRecord record = DataListRecord.create(datalist);

        assertThat(record.getName()).isEqualTo(datalist.getName());
        assertThat(record.getEntities()).hasSize(1);
    }
}