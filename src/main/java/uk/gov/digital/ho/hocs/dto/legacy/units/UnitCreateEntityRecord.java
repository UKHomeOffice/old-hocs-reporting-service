package uk.gov.digital.ho.hocs.dto.legacy.units;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    public static UnitCreateEntityRecord createGroup(String action, String unitDisplayName, String unitRefName, String teamDisplayName, String teamRefName) {

        return new UnitCreateEntityRecord(action, unitDisplayName, unitRefName, teamDisplayName, teamRefName);
    }
}
