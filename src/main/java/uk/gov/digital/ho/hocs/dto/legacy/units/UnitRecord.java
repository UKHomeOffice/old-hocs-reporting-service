package uk.gov.digital.ho.hocs.dto.legacy.units;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.DataList;
import uk.gov.digital.ho.hocs.DataListEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UnitRecord {
    private List<UnitEntityRecord> units;

    public static UnitRecord create(DataList list) {
        List<UnitEntityRecord> units = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            units = list.getEntities().stream().map(UnitRecord::create).collect(Collectors.toList());
        }
        return new UnitRecord(units);
    }

    private static UnitEntityRecord create(DataListEntity unit) {
        return UnitEntityRecord.create(unit);
    }

}