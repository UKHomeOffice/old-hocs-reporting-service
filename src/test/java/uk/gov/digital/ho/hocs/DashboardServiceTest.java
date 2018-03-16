package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.model.CaseStatus;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.DashboardSummaryDto;

import java.util.LinkedHashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {


    private static final LinkedHashSet<CaseType> EMPTY_CASE_TYPE = new LinkedHashSet<>();
    private static final LinkedHashSet<CaseType> SINGLE_CASE_TYPE = new LinkedHashSet<CaseType>() {{
        add(CaseType.MIN);
    }};
    private static final LinkedHashSet<CaseType> DUPLICATE_CASE_TYPE = new LinkedHashSet<CaseType>() {{
        add(CaseType.MIN);
        add(CaseType.MIN);
    }};
    private static final LinkedHashSet<CaseType> MULTI_CASE_TYPE = new LinkedHashSet<CaseType>() {{
        add(CaseType.MIN);
        add(CaseType.TRO);
        add(CaseType.DTEN);
    }};

    @Mock
    private CaseCurrentPropertiesRepository caseCurrentPropertiesRepository;

    private DashboardService dashboardService;
    private DashboardSummaryDto dashboardSummaryDto;

    @Before
    public void setUp() {
        dashboardService = new DashboardService(caseCurrentPropertiesRepository);
    }

    @Test
    public void shouldCallRepositoryOnceAndReturnCountForSingleCaseType() {
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name())).thenReturn(5l);

        dashboardSummaryDto = dashboardService.getSummary(SINGLE_CASE_TYPE);

        assertThat(dashboardSummaryDto.toString()).isEqualTo("DashboardSummaryDto(summary=[{total=5, name=MIN}])");

        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name());
    }

    @Test
    public void shouldCallRepositoryThreeTimesAndReturnCountForThreeCaseType() {
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name())).thenReturn(3l);
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(CaseType.TRO.name(), CaseStatus.Completed.name())).thenReturn(2l);
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(CaseType.DTEN.name(), CaseStatus.Completed.name())).thenReturn(1l);

        dashboardSummaryDto = dashboardService.getSummary(MULTI_CASE_TYPE);

        assertThat(dashboardSummaryDto.toString()).isEqualTo("DashboardSummaryDto(summary=[{total=3, name=MIN}, {total=2, name=TRO}, {total=1, name=DTEN}])");

        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name());
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(CaseType.TRO.name(), CaseStatus.Completed.name());
        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(CaseType.DTEN.name(), CaseStatus.Completed.name());
    }

    @Test
    public void shouldCallRepositoryOnceWhenDuplicateCaseTypeEnteredAndReturnCountForCaseTypeOnce() {
        when(caseCurrentPropertiesRepository.countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name())).thenReturn(4l);

        dashboardSummaryDto = dashboardService.getSummary(DUPLICATE_CASE_TYPE);

        assertThat(dashboardSummaryDto.toString()).isEqualTo("DashboardSummaryDto(summary=[{total=4, name=MIN}])");

        verify(caseCurrentPropertiesRepository, times(1)).countByCorrespondenceTypeAndCaseStatusNot(CaseType.MIN.name(), CaseStatus.Completed.name());
    }

    @Test
    public void shouldCallRepository() {

        dashboardSummaryDto = dashboardService.getSummary(EMPTY_CASE_TYPE);
        verify(caseCurrentPropertiesRepository, never()).countByCorrespondenceTypeAndCaseStatusNot(
                any(),
                any());

        assertThat(dashboardSummaryDto.toString()).isEqualTo("DashboardSummaryDto(summary=[])");
    }
}