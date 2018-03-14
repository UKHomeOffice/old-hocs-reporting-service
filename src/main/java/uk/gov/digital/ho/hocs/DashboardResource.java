package uk.gov.digital.ho.hocs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class DashboardResource {


    DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping("/dashboardSummary")
    public HashMap dashboardSummary(@RequestBody List<String> caseTypes) {
        return dashboardService.getSummary(caseTypes);
    }
}
