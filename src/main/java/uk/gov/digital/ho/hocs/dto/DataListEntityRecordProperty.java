package uk.gov.digital.ho.hocs.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

@AllArgsConstructor
@Getter
public class DataListEntityRecordProperty {
    private String key;
    private String value;

    public static DataListEntityRecordProperty create(DataListEntityProperty p) {
        return new DataListEntityRecordProperty(p.getKey(), p.getValue());
    }
}