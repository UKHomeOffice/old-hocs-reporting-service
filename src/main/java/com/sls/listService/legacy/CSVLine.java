package com.sls.listService.legacy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public class CSVLine {

    @Getter
    private String parentTopicName;

    @Getter
    private String topicName;

    @Getter
    private String topicUnit;

    @Getter
    private String topicTeam;

    public static String toSubListValue(String caseType, String parentText, String subText) {
        return String.format("%s_%s_%s", caseType, toListValue(parentText), toListValue(subText));
    }

    public static String toListValue(String text) {
        return text.replaceAll(" ", "_")
                .replaceAll("[^a-zA-Z0-9_]+", "")
                .replaceAll("__", "_")
                .toUpperCase();
    }

    public static String[] splitLine(String row) {
        String[] tokens = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (String t : tokens) {
            log.info(t);
        }

        return tokens;
    }
}