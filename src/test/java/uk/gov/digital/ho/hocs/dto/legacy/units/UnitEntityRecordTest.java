package uk.gov.digital.ho.hocs.dto.legacy.units;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitEntityRecordTest {
    @Test
    public void createUnit() throws Exception {

        BusinessGroup group = new BusinessGroup("Disp", "Auth");
        Set<BusinessGroup> units = new HashSet<>();
        units.add(new BusinessGroup());
        group.setSubGroups(units);
        UnitEntityRecord unit = UnitEntityRecord.create(group);

        assertThat(unit.getAuthorityName()).isEqualTo("GROUP_AUTH");
        assertThat(unit.getDisplayName()).isEqualTo("Disp");
        assertThat(unit.getTeams()).hasSize(1);
    }

    @Test
    public void getStrings() throws Exception {
        List<UnitEntityRecord> units = new ArrayList<>();
        units.add(new UnitEntityRecord("", "", new ArrayList<>()));
        UnitEntityRecord unit = new UnitEntityRecord("Auth", "Disp", units);

        assertThat(unit.getAuthorityName()).isEqualTo("Auth");
        assertThat(unit.getDisplayName()).isEqualTo("Disp");
        assertThat(unit.getTeams()).hasSize(1);
    }

}