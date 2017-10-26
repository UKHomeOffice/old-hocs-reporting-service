package com.sls.listService.legacy.units;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
@EqualsAndHashCode(of = {"unit", "team"})
public class CSVUnitLine {

    @Getter
    private String unit;

    @Getter
    private String team;

    public static String toSubListValue(String unit, String team) {
        return String.format("%s_%s", unit, team);
    }

}