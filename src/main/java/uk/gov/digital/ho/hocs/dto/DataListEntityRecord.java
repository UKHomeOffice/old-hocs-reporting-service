package uk.gov.digital.ho.hocs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.DataListEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class DataListEntityRecord {

    private String text;

    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DataListEntityRecordProperty> properties = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DataListEntityRecord> subEntities = new ArrayList<>();

    public static DataListEntityRecord create(DataListEntity dle) {
        List<DataListEntityRecordProperty> properties = dle.getProperties().stream().map(DataListEntityRecordProperty::create).collect(Collectors.toList());
        List<DataListEntityRecord> subEntities = dle.getSubEntities().stream().map(DataListEntityRecord::create).collect(Collectors.toList());

        return new DataListEntityRecord(dle.getText(), dle.getValue(), properties, subEntities);
    }

}
