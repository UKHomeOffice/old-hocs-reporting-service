package uk.gov.digital.ho.hocs.dto.legacy.units;

import uk.gov.digital.ho.hocs.model.DataList;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UnitCreateRecord {
    private List<UnitCreateEntityRecord> manageGroups;

    public static UnitCreateRecord create(DataList list) {
        List<UnitCreateEntityRecord> manageGroups = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            manageGroups = list.getEntities().stream().map(UnitCreateRecord::createUnit).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return new UnitCreateRecord(manageGroups);
    }

    // Units and Teams are added at the same level, all in one manageGroups object.
    private static List<UnitCreateEntityRecord> createUnit(DataListEntity unit) {
        List<UnitCreateEntityRecord> list = new ArrayList<>();
        list.add(UnitCreateEntityRecord.createUnit(unit));
        for (DataListEntity team : unit.getSubEntities()) {
            list.add(UnitCreateEntityRecord.createTeam(team, unit.getValue()));
        }
        return list;
    }
}