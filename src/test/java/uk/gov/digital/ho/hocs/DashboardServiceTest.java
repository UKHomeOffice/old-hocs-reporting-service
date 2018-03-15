package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    private static final String MIN = "MIN";
    private static final String TRO = "TRO";
    private static final String DTEN = "DTEN";
    private static final String ABC = "ABC";
    private static final String DEF = "DEF";
    private static final String COMPLETED = "Completed";
    private static final List<String> EMPTY_CASE_TYPE  = new ArrayList<>();
    private static final List<String> SINGLE_CASE_TYPE  = new ArrayList<String>() {{ add(MIN); }};
    private static final List<String> DUPLICATE_CASE_TYPE  = new ArrayList<String>() {{ add(MIN); add(MIN);}};
    private static final List<String> MULTI_CASE_TYPE  = new ArrayList<String>() {{ add(MIN); add(TRO);add(DTEN);add(ABC);add(DEF);}};

    @Mock
    private CaseCurrentPropertiesRepository caseCurrentPropertiesRepository;

    private DashboardService dashboardService;

    @Before
    public void setUp() {
        dashboardService = new DashboardService(caseCurrentPropertiesRepository);
    }

    @Test
    public void shouldCallRepositoryOnceAndReturnCountForSingleCaseType(){
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED)).thenReturn(5l);

        dashboardService.getSummary(SINGLE_CASE_TYPE);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED);
    }

    @Test
    public void shouldCallRepositoryFiveTimesAndReturnCountForFiveCaseType(){
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED)).thenReturn(5l);

        dashboardService.getSummary(MULTI_CASE_TYPE);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(TRO,COMPLETED);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(DTEN,COMPLETED);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(ABC,COMPLETED);
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(DEF,COMPLETED);
    }

    @Test
    public void shouldCallRepositoryTwiceAndReturnCountForCaseTypeTwice(){
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED)).thenReturn(5l);

        dashboardService.getSummary(DUPLICATE_CASE_TYPE);
        verify(caseCurrentPropertiesRepository, times(2)).countByCorrespondenceTypeAndCaseStatusNot(MIN,COMPLETED);
    }

    @Test
    public void shouldCallRepository(){

        dashboardService.getSummary(EMPTY_CASE_TYPE);
        verify(caseCurrentPropertiesRepository, never()).countByCorrespondenceTypeAndCaseStatusNot(anyString(),
                anyString());
    }
}