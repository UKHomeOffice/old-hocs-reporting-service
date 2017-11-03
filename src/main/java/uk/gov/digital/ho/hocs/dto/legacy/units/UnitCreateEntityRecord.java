package uk.gov.digital.ho.hocs.dto.legacy.units;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.DataListEntity;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UnitCreateEntityRecord {

    private String action;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String unitDisplayName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String unitRefName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teamDisplayName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teamRefName;

    public static UnitCreateEntityRecord createUnit(DataListEntity unit) {

       String action = "addUnit";
       String unitDisplayName = unit.getText();
       String unitRefName = unit.getValue();
       String teamDisplayName = null;
       String teamRefName = null;

        return new UnitCreateEntityRecord(action, unitDisplayName, unitRefName, teamDisplayName, teamRefName);
    }

    public static UnitCreateEntityRecord createTeam(DataListEntity team, String unitReferenceName) {

        String action = "addTeam";
        String unitDisplayName = null;
        String unitRefName = unitReferenceName;
        String teamDisplayName = team.getText();
        String teamRefName = team.getValue();

        return new UnitCreateEntityRecord(action, unitDisplayName, unitRefName, teamDisplayName, teamRefName);
    }

}
