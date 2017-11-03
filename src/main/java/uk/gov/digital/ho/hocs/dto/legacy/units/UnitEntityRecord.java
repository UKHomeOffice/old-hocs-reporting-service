package uk.gov.digital.ho.hocs.dto.legacy.units;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UnitEntityRecord {

    private String authorityName;

    private String displayName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UnitEntityRecord> teams;

    public static UnitEntityRecord create(BusinessGroup unit) {
        List<UnitEntityRecord> teams = unit.getSubGroups().stream().map(UnitEntityRecord::create).collect(Collectors.toList());
        return new UnitEntityRecord("GROUP_" + unit.getReferenceName(), unit.getDisplayName(), teams);
    }
}
