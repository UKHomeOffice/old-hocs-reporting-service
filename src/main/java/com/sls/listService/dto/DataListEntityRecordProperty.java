package com.sls.listService.dto;

import com.sls.listService.DataListEntityProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataListEntityRecordProperty {
    private String key;
    private String value;

    public static DataListEntityRecordProperty create(DataListEntityProperty p) {
        return new DataListEntityRecordProperty(p.getKey(), p.getValue());
    }
}