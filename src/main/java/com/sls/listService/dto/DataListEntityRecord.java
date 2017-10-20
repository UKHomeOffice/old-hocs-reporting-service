package com.sls.listService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sls.listService.DataListEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DataListEntityRecord {

    private String text;

    private String value;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DataListEntityRecordProperty> properties = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DataListEntityRecord> subEntities = new ArrayList<>();

    public static DataListEntityRecord create(DataListEntity listEntity) {
        List<DataListEntityRecordProperty> properties = new ArrayList<>();
        if (listEntity.getProperties() != null && !listEntity.getProperties().isEmpty()) {
            properties = listEntity.getProperties()
                    .stream()
                    .map(r -> new DataListEntityRecordProperty(r.getKey(), r.getValue()))
                    .collect(Collectors.toList());
        }

        List<DataListEntityRecord> entityRecords = new ArrayList<>();
        if (listEntity.getSubEntities() != null && !listEntity.getSubEntities().isEmpty()) {
            entityRecords = listEntity.getSubEntities()
                    .stream()
                    .map(DataListEntityRecord::create)
                    .collect(Collectors.toList());
        }

        return new DataListEntityRecord(listEntity.getText(), listEntity.getValue(), properties, entityRecords);
    }
}
