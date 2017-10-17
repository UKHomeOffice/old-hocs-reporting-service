package com.sls.listService.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LegacyDataListEntityRecord {

    private String name;

    private String caseType;

    @JsonUnwrapped
    private List<HashMap<String, String>> topicListItems = new ArrayList<>();

}
