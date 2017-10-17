package com.sls.listService.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LegacyDataListEntityRecord {

    @Getter
    private String name;

    @Getter
    private String caseType;

    private List<LegacyDataListEntityRecord> topicListItems = new ArrayList<>();

    @JsonUnwrapped
    private List<DataListEntityRecordProperties> properties = new ArrayList<>();

}
