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
public class LegacyTopicListRecord {

    private String topicName;
    private String topicUnit;
    private List<LegacyDataListEntityRecord> entities;

}
