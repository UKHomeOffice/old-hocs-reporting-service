package uk.gov.digital.ho.hocs.dto.legacy.units;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UnitRecord {
    private List<UnitEntityRecord> units;

    public static UnitRecord create(List<BusinessGroup> list) {
        List<UnitEntityRecord> units = list.stream().map(g -> UnitEntityRecord.create(g)).collect(Collectors.toList());
        return new UnitRecord(units);
    }

}