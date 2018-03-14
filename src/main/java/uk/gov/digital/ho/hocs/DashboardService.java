package uk.gov.digital.ho.hocs;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

@Service
public class DashboardService {

    private final CaseCurrentPropertiesRepository currentPropertiesRepository;

    public DashboardService(CaseCurrentPropertiesRepository caseCurrentPropertiesRepository) {
        this.currentPropertiesRepository = caseCurrentPropertiesRepository;
    }

    public HashMap getSummary(List<String> caseTypes) {
        HashMap returnValuesHash = new HashMap();
        ArrayList<HashMap> returnValuesArray = new ArrayList<>();
        caseTypes.forEach(caseType -> {
            long returnCount = currentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(caseType,"Completed");
            returnValuesArray.add(new HashMap<String, String>() {{
                put("name", caseType);
                put("total", valueOf(returnCount));
            }});
        });
        returnValuesHash.put("summary", returnValuesArray);
        return returnValuesHash;
    }
}
