package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
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
        Event event = getValidEvent();
        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test()
    public void testUpdateEvent() {
        Event event = getValidEvent();
        currentPropertiesService.createCurrentProperties(event);

        when(mockCurrentPropertiesRepo.findByCaseReference(event.getCaseReference())).thenReturn(new CaseCurrentProperties(event));

        Event newEvent = getValidEventWithTime(LocalDateTime.now().plusHours(1));
        currentPropertiesService.createCurrentProperties(newEvent);

        verify(mockCurrentPropertiesRepo, times(2)).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(2)).save(any(CaseCurrentProperties.class));
    }

    @Test()
    public void testRepoDataIntegrityExceptionThrowsEntityCreationExceptionProperty() {
        Event event = getValidEvent();
        when(mockCurrentPropertiesRepo.save(any(CaseCurrentProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "current_properties_id_idempotent")));

        currentPropertiesService.createCurrentProperties(event);

        verify(mockCurrentPropertiesRepo).findByCaseReference(event.getCaseReference());
        verify(mockCurrentPropertiesRepo, times(1)).save(any(CaseCurrentProperties.class));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityExceptionProperty() {
        Event event = getValidEvent();
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


    private Event getValidEvent() {
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

}