package com.sls.listService.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataListRecord {
    private String reference;
    private List<DataListEntityRecord> entities;
}
