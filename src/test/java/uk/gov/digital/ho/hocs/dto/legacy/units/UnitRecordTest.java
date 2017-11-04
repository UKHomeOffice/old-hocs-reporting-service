package uk.gov.digital.ho.hocs.dto.legacy.units;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitRecordTest {

    @Test
    public void createWithEntities() throws Exception {
        List<BusinessGroup> unitList = new ArrayList<>();
        unitList.add(new BusinessGroup());
        UnitRecord record = UnitRecord.create(unitList);
        assertThat(record.getUnits()).hasSize(1);
    }

}