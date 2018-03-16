package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.DashboardSummaryDto;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardResourceTest {

    private DashboardResource dashboardResource;

    @Mock
    private DashboardService dashboardService;

    private static final String TOTAL = "total";
    private static final String NAME = "name";

    @Before
    public void setUp() {
        dashboardResource = new DashboardResource(dashboardService);
    }

    @Test
    public void shouldReturnCountResultWhenDashboardSummaryRequested() {
        LinkedHashSet<CaseType> caseTypes = new LinkedHashSet<CaseType>() {{
            add(CaseType.MIN);
        }};
        ArrayList arraylist = new ArrayList<HashMap>() {
            {
                add(new HashMap<String, String>() {{
                    put(TOTAL, "1");
                    put(NAME, CaseType.MIN.name());
                }});

            }
        };
        when(dashboardService.getSummary(caseTypes)).
                thenReturn(DashboardSummaryDto.builder().summary(arraylist).build());

        ResponseEntity dashboardSummary = dashboardResource.dashboardSummary(caseTypes);
        assertThat(dashboardSummary.getBody()).isEqualToComparingFieldByField(DashboardSummaryDto.builder().summary(arraylist).build());
    }
}