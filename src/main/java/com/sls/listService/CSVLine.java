package com.sls.listService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CSVLine {

    @Getter
    private String parentTopic;

    @Getter
    private String topicName;

    @Getter
    private String topicUnit;

    @Getter
    private String topicTeam;

    public static String toListValue(String text) {
        return text.replaceAll(" ", "_")
                .replaceAll("[^a-zA-Z0-9_]+", "")
                .replaceAll("__", "_")
                .toUpperCase();
    }
}