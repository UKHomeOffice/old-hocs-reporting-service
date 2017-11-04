package uk.gov.digital.ho.hocs.dto;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DataListEntityRecordTest {

    @Test
    public void createWithOneConstructor() throws Exception {
        DataListEntity dataListEntity = new DataListEntity("Text");
        DataListEntityRecord record = DataListEntityRecord.create(dataListEntity);

        assertThat(record.getText()).isEqualTo(dataListEntity.getText());
        assertThat(record.getValue()).isEqualTo("TEXT");
        assertThat(record.getSubEntities()).hasSize(0);
        assertThat(record.getProperties()).hasSize(0);
    }

    @Test
    public void createWithNoEntitiesNoProperties() throws Exception {
        DataListEntity dataListEntity = new DataListEntity("Text", "Value");
        DataListEntityRecord record = DataListEntityRecord.create(dataListEntity);

        assertThat(record.getText()).isEqualTo(dataListEntity.getText());
        assertThat(record.getValue()).isEqualTo("VALUE");
        assertThat(record.getSubEntities()).hasSize(0);
        assertThat(record.getProperties()).hasSize(0);
    }

    @Test
    public void createWithEntitiesWithProperties() throws Exception {
        Set<DataListEntity> subEntities = new HashSet<>();
        DataListEntity subEntity = new DataListEntity("SubText1", "SubValue1");
        subEntities.add(subEntity);

        Set<DataListEntityProperty> properties = new HashSet<>();
        DataListEntityProperty property = new DataListEntityProperty("Key1", "Value1");
        properties.add(property);

        DataListEntity dataListEntity = new DataListEntity("Text", "Value");
        dataListEntity.setSubEntities(subEntities);
        dataListEntity.setProperties(properties);
        DataListEntityRecord record = DataListEntityRecord.create(dataListEntity);

        assertThat(record.getText()).isEqualTo(dataListEntity.getText());
        assertThat(record.getValue()).isEqualTo("VALUE");
        assertThat(record.getSubEntities()).hasSize(1);
        assertThat(record.getProperties()).hasSize(1);
    }

}