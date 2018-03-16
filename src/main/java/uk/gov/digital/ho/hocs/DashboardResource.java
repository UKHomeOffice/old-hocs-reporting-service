package uk.gov.digital.ho.hocs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.DashboardSummaryDto;

import java.util.LinkedHashSet;

@RestController
public class DashboardResource {

    DashboardService dashboardService;
    DashboardSummaryDto dashboardSummaryDto;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping("/dashboardSummary")
    public ResponseEntity<DashboardSummaryDto> dashboardSummary(@RequestBody LinkedHashSet<CaseType> caseTypes) {

        LinkedHashSet<CaseType> nullLinkedHashSet = new LinkedHashSet<>();
            nullLinkedHashSet.add(null);

        if(caseTypes.equals(nullLinkedHashSet)){
            return ResponseEntity.unprocessableEntity().build();
        } else {
            dashboardSummaryDto = dashboardService.getSummary(caseTypes);
        }

        return ResponseEntity.ok().body(dashboardSummaryDto);
    }
}
