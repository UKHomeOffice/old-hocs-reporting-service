package com.sls.listService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sls.listService.DataListEntityProperties;
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

    @JsonIgnore
    private List<LegacyTopicListItem> topicListItems = new ArrayList<>();

    @JsonIgnore
    private List<DataListEntityProperties> properties = new ArrayList<>();

}
