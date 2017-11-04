package uk.gov.digital.ho.hocs.dto;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import static org.assertj.core.api.Assertions.assertThat;

public class DataListEntityRecordPropertyTest {

    @Test
    public void create() throws Exception {
        DataListEntityProperty property = new DataListEntityProperty("Key","Value");
        DataListEntityRecordProperty record = DataListEntityRecordProperty.create(property);

        assertThat(record.getKey()).isEqualTo(property.getKey());
        assertThat(record.getValue()).isEqualTo(property.getValue());
    }

}