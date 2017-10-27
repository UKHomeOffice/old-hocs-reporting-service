package com.sls.listService.legacy.topics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public class CSVTopicLine {

    @Getter
    private String parentTopicName;

    @Getter
    private String topicName;

    @Getter
    private String topicUnit;

    @Getter
    private String topicTeam;

    public String getTopicValue(String caseType) {
        return String.format("%s_%s_%s", caseType, parentTopicName, topicName);
    }
}