package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CaseCurrentPropertiesServiceTest {

    @Mock
    private CaseCurrentPropertiesRepository mockCurrentPropertiesRepo;

    private CaseCurrentPropertiesService currentPropertiesService;

    @Before
    public void setUp() {
        currentPropertiesService = new CaseCurrentPropertiesService(mockCurrentPropertiesRepo);
    }

    @Test
    /*
     * Test that an event is saved as event
     */
    public void testCreateEvent() {
        Event event = getValidEventWithCaseTask();
        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test()
    public void testUpdateEvent() {
        Event event = getValidEventWithCaseTask();
        currentPropertiesService.createCurrentProperties(event);

        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(new CaseCurrentProperties(event));

        Event newEvent = getValidEventWithTime(LocalDateTime.now().plusHours(1));
        currentPropertiesService.createCurrentProperties(newEvent);

        verify(mockCurrentPropertiesRepo, times(2)).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(2)).save(any(CaseCurrentProperties.class));
    }

    @Test()
    public void testRepoDataIntegrityExceptionThrowsEntityCreationExceptionProperty() {
        Event event = getValidEventWithCaseTask();
        when(mockCurrentPropertiesRepo.save(any(CaseCurrentProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "current_properties_id_idempotent")));

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityExceptionProperty() {
        Event event = getValidEventWithCaseTask();
        when(mockCurrentPropertiesRepo.save(any(CaseCurrentProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "other error")));

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testReturnsWhenValidUnit() {
        LocalDateTime now =  LocalDateTime.now();
        Event event = getValidEventWithTime(now);

        when(mockCurrentPropertiesRepo.getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class))).thenReturn(new HashSet<>(Arrays.asList(new CaseCurrentProperties(event))));

        currentPropertiesService.getCurrentProperties("DCU");

        verify(mockCurrentPropertiesRepo).getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class));
    }

    @Test
    public void testReturnsWhenInvalidUnit() {
        LocalDateTime now =  LocalDateTime.now();
        Event event = getValidEventWithTime(now);

        when(mockCurrentPropertiesRepo.getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class))).thenReturn(new HashSet<>(Arrays.asList(new CaseCurrentProperties(event))));

        currentPropertiesService.getCurrentProperties("NOTVALID@");

        verify(mockCurrentPropertiesRepo, times(0)).getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class));
    }

    @Test
    public void shouldNotWriteOlderMessage() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "CaseRef1");
        when(mockCurrentPropertiesRepo.findByCaseReference(event1.getCaseReference())).thenReturn(new CaseCurrentProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now().minusDays(1), "CaseRef1");
        currentPropertiesService.createCurrentProperties(event2);

        verify(mockCurrentPropertiesRepo, times(0)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void shouldNotWriteSameMessage() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "CaseRef1");
        when(mockCurrentPropertiesRepo.findByCaseReference(event1.getCaseReference())).thenReturn(new CaseCurrentProperties(event1));

        currentPropertiesService.createCurrentProperties(event1);

        verify(mockCurrentPropertiesRepo, times(0)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void shouldWriteNewerMessage() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "CaseRef1");
        when(mockCurrentPropertiesRepo.findByCaseReference(event1.getCaseReference())).thenReturn(new CaseCurrentProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now().plusDays(1), "CaseRef1");
        currentPropertiesService.createCurrentProperties(event2);

        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testCreateTaskEntryDetailsForFOI_UKVICase() {
        Event event = getValidEventWithCaseTask("Create case");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(new CaseCurrentProperties(event));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testDoesNotCreateTaskEntryDetailsForNonFOI_UKVICase() {
        Event event = getValidNonFOIEvent();
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo, times(0)).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(0)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testTaskEntryDetailsDoesNotUpdateWhenCaseTaskNotChanged() {
        Event event = getValidEventWithCaseTask("Create case");
        CaseCurrentProperties caseCurrentProperties = getValidCaseCurrentProperties("Create case");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(caseCurrentProperties);
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(0)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromCreateCaseToDraftResponse() {
        Event event = getValidEventWithCaseTask("Draft response");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("Create case"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromDraftResponseToQaReview() {
        Event event = getValidEventWithCaseTask("QA review");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("Draft response"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromQaReviewToCQTApproval() {
        Event event = getValidEventWithCaseTask("CQT approval");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("QA review"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromCQTApprovalToPrivateOfficeApproval() {
        Event event = getValidEventWithCaseTask("Private Office approval");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("CQT approval"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }


    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromQaReviewToSCSApproval() {
        Event event = getValidEventWithCaseTask("SCS approval");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("QA review"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromSCSApprovalToPressOfficeReview() {
        Event event = getValidEventWithCaseTask("Press Office review");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("SCS approval"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromPressOfficeReviewToSpadsApproval() {
        Event event = getValidEventWithCaseTask("SpAds approval");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("Press Office review"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromSpadsApprovalToFoiMinisterSignOff() {
        Event event = getValidEventWithCaseTask("FOI Minister sign-off");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("SpAds approval"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromFoiMinisterSignOffToDispatchResponse() {
        Event event = getValidEventWithCaseTask("Dispatch response");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("FOI Minister sign-off"));
        currentPropertiesService.createTaskEntryDetails(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test
    public void testUpdateTaskEntryDetailsWhenCaseGoesFromDispatchResponseToComplete() {
        Event event = getValidEventWithCaseTask("None");
        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(getValidCaseCurrentProperties("Dispatch response"));
        currentPropertiesService.createTaskEntryDetails(event);


        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    private Event getValidEventWithCaseTask() {
        return getValidEventWithTime(LocalDateTime.now());
    }

    private Event getValidEventWithTime(LocalDateTime localDate) {
        String uuid = "uuid";
        LocalDateTime dateTime = localDate;
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }

    private Event getValidEventWithDate(LocalDate localDate, String caseRef) {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        return new Event(uuid, dateTime, caseRef, data);
    }

        private Event getValidEventWithCaseTask(String caseTask) {
        String uuid = "uuid";
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType", "FOI");
        data.put("caseTask", caseTask);
        return new Event(uuid, LocalDateTime.now(), caseRef, data);
    }

    private Event getValidNonFOIEvent() {
        String uuid = "uuid";
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType", "MIN");
        data.put("caseTask", "Create case");
        return new Event(uuid, LocalDateTime.now(), caseRef, data);
    }

    private CaseCurrentProperties getValidCaseCurrentProperties(String caseTask) {
        CaseCurrentProperties taskEntryDetails = new CaseCurrentProperties();
        switch (caseTask) {
//            taskEntryDetails.setCaseReference("FOI/00001/18");
            case "Create case":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                break;
            case "Draft response":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                break;
            case "QA review":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                break;
            case "CQT approval":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setUkviCQTApproval(LocalDateTime.now());
                break;
            case "Private Office approval":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setUkviCQTApproval(LocalDateTime.now());
                taskEntryDetails.setUkviPrivateOfficeApproval(LocalDateTime.now());
                break;
            case "SCS approval":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                break;
            case "Press Office review":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiPressOfficeReview(LocalDateTime.now());
                break;
            case "SpAds approval":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiPressOfficeReview(LocalDateTime.now());
                taskEntryDetails.setFoiSpadsApproval(LocalDateTime.now());
                break;
            case "FOI Minister sign-off":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiPressOfficeReview(LocalDateTime.now());
                taskEntryDetails.setFoiSpadsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiFoiMinisterSignoff(LocalDateTime.now());
                break;
            case "Dispatch response":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiPressOfficeReview(LocalDateTime.now());
                taskEntryDetails.setFoiSpadsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiFoiMinisterSignoff(LocalDateTime.now());
                taskEntryDetails.setDispatchResponse(LocalDateTime.now());
                break;
            case "None":
                taskEntryDetails.setCreateCase(LocalDateTime.now());
                taskEntryDetails.setDraftResponse(LocalDateTime.now());
                taskEntryDetails.setQAReview(LocalDateTime.now());
                taskEntryDetails.setFoiScsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiPressOfficeReview(LocalDateTime.now());
                taskEntryDetails.setFoiSpadsApproval(LocalDateTime.now());
                taskEntryDetails.setFoiFoiMinisterSignoff(LocalDateTime.now());
                taskEntryDetails.setDispatchResponse(LocalDateTime.now());
                taskEntryDetails.setCompleted(LocalDateTime.now());
                break;
            default:
        }

        return taskEntryDetails;
    }
}