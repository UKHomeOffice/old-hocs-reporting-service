package uk.gov.digital.ho.hocs;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import uk.gov.digital.ho.hocs.model.CaseProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CasePropertiesServiceTest {

    @Mock
    private CasePropertiesRepository mockPropertiesRepo;

    private CasePropertiesService casePropertiesService;

    @Before
    public void setUp() {
        casePropertiesService = new CasePropertiesService(mockPropertiesRepo);
    }

    @Test
    /*
     * Test that an event is saved as event and property
     */
    public void testCreateEvent() {
        Event event = getValidEvent();
        casePropertiesService.createProperties(event);

        verify(mockPropertiesRepo).save(any(CaseProperties.class));
    }

    @Test()
    public void testRepoDataIntegrityExceptionThrowsEntityCreationExceptionProperty() {
        Event event = getValidEvent();

        when(mockPropertiesRepo.save(any(CaseProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "properties_id_idempotent")));

        casePropertiesService.createProperties(event);

        verify(mockPropertiesRepo, times(1)).save(any(CaseProperties.class));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepoUnhandledExceptionThrowsDataIntegrityExceptionProperty() {
        Event event = getValidEvent();

        when(mockPropertiesRepo.save(any(CaseProperties.class))).thenThrow(new DataIntegrityViolationException("Thrown DataIntegrityViolationException", new ConstraintViolationException("", null, "other error")));

        casePropertiesService.createProperties(event);

        verify(mockPropertiesRepo).save(any(CaseProperties.class));
    }

    @Test
    public void testReturnsWhenValidUnit() {
        LocalDateTime now =  LocalDateTime.now();
        Event event = getValidEventWithTime(now);

        when(mockPropertiesRepo.getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class))).thenReturn(new HashSet<>(Arrays.asList(new CaseProperties(event))));

        casePropertiesService.getProperties("DCU", "1999-12-01");

        verify(mockPropertiesRepo).getAllByTimestampBetweenAndCorrespondenceTypeIn(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class));
    }

    private Event getValidEvent() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }

    private Event getValidEventWithTime(LocalDateTime localDate) {
        String uuid = "uuid";
        LocalDateTime dateTime = localDate;
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }
}