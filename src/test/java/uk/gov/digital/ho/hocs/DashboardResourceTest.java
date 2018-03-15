package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardResourceTest {

    private DashboardResource dashboardResource;

    @Mock
    private DashboardService dashboardService;

    private static final String MIN = "MIN";
    private static final String SUMMARY = "summary";
    private static final String TOTAL = "total";
    private static final String NAME = "name";

    @Before
    public void setUp() {
        dashboardResource = new DashboardResource(dashboardService);
    }

    @Test
    public void shouldReturnCountResultWhenDashboardSummaryRequested() {
        List<String> caseTypes = new ArrayList<String>() {{
            add(MIN);
        }};
        when(dashboardService.getSummary(caseTypes)).
                thenReturn(new HashMap() {{
                    put(SUMMARY, new ArrayList<HashMap>() {
                                {
                                    add(new HashMap<String, String>() {{
                                            put(TOTAL, "1");
                                            put(NAME, MIN);
                                        }}
                                    );
                                }
                            }
                    );
                }});

        HashMap hashMapResponse = dashboardResource.dashboardSummary(caseTypes);
        System.out.println(hashMapResponse);
        ArrayList arraylist = new ArrayList<HashMap>() {
            {
                add(new HashMap<String, String>() {{
                    put(TOTAL, "1");
                    put(NAME, MIN);
                }});

            }
        };
        assertThat(hashMapResponse.get(SUMMARY)).isEqualTo(arraylist);
    }
}