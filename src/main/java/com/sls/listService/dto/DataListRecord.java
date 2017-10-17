package com.sls.listService.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataListRecord {
    private String reference;
    private List<DataListEntityRecord> entities;
}
