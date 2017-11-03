package uk.gov.digital.ho.hocs.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BusinessGroupRecord {

    private String displayName;

    private String referenceName;

    private List<BusinessGroupRecord> subGroups;

    public static BusinessGroupRecord create(BusinessGroup group) {
        List<BusinessGroupRecord> subGroupRecords = new ArrayList<>();
        if (group.getSubGroups() != null && !group.getSubGroups().isEmpty()) {
            subGroupRecords = group.getSubGroups().stream().map(BusinessGroupRecord::create).collect(Collectors.toList());
        }

        return new BusinessGroupRecord(group.getDisplayName(), group.getReferenceName(), subGroupRecords);
    }

}
