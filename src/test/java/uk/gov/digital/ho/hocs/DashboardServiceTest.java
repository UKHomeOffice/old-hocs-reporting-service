package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    private static final String MIN = "MIN";
    private static final String COMPLETED = "Completed";
    private static final List<String> CASE_TYPES  = new ArrayList<String>() {{ add("MIN"); }};

    @Mock
    private CaseCurrentPropertiesRepository caseCurrentPropertiesRepository;

    private DashboardService dashboardService;

    @Before
    public void setUp() {
        dashboardService = new DashboardService(caseCurrentPropertiesRepository);
    }

    @Test
    public void shouldReturnCount(){
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED)).thenReturn(5l);

        dashboardService.getSummary(CASE_TYPES);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED);

    }
}