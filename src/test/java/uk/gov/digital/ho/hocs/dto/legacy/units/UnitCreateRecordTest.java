package uk.gov.digital.ho.hocs.dto.legacy.units;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitCreateRecordTest {

    @Test
    public void createWithEntities() throws Exception {
        List<BusinessGroup> unitList = new ArrayList<>();
        unitList.add(new BusinessGroup());
        UnitCreateRecord record = UnitCreateRecord.create(unitList);
        assertThat(record.getManageGroups()).hasSize(1);
    }

    @Test
    public void createWithoutEntities() throws Exception {
        List<BusinessGroup> unitList = new ArrayList<>();
        UnitCreateRecord record = UnitCreateRecord.create(unitList);
        assertThat(record.getManageGroups()).hasSize(0);
    }

}