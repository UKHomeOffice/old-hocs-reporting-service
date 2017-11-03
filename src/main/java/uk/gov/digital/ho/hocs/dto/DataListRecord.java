package uk.gov.digital.ho.hocs.dto;

import uk.gov.digital.ho.hocs.model.DataList;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataListRecord {
    private String name;
    private List<DataListEntityRecord> entities;

    public static DataListRecord create(DataList list) {
        List<DataListEntityRecord> entities = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            entities = list.getEntities().stream().map(DataListEntityRecord::create).collect(Collectors.toList());
        }
        return new DataListRecord(list.getName(), entities);
    }
}
