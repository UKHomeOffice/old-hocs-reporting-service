package uk.gov.digital.ho.hocs;

import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.model.CaseStatus;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.DashboardSummaryDto;

import java.util.*;

import static java.lang.String.valueOf;

@Service
public class DashboardService {


    private final CaseCurrentPropertiesRepository currentPropertiesRepository;

    public DashboardService(CaseCurrentPropertiesRepository caseCurrentPropertiesRepository) {
        this.currentPropertiesRepository = caseCurrentPropertiesRepository;
    }

    public DashboardSummaryDto getSummary(LinkedHashSet<CaseType> caseTypes) {
        ArrayList<HashMap> returnValuesArray = new ArrayList<>();
        caseTypes.forEach((CaseType caseType) -> {

            long returnCount = currentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(caseType.name(), CaseStatus.Completed.name());

            returnValuesArray.add(new HashMap<String, String>() {{
                put("name", caseType.name());
                put("total", valueOf(returnCount));
            }});
        });
        return DashboardSummaryDto.builder().summary(returnValuesArray).build();
    }
}
