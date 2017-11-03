package uk.gov.digital.ho.hocs.dto.legacy.units;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UnitRecord {
    private List<UnitEntityRecord> units;

    public static UnitRecord create(List<BusinessGroup> list) {
        List<UnitEntityRecord> records = new ArrayList<>();

        list.forEach( g -> records.add(UnitEntityRecord.create(g)));

        return new UnitRecord(records);
    }

}