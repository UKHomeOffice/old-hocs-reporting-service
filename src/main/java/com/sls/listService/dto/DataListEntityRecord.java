package com.sls.listService.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataListEntityRecord {
    private String value;
    private String reference;
    private List<DataListEntityRecord> subEntities;
    private List<DataListEntityRecordProperties> properties;
}
