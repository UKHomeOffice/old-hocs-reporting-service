package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class CaseCurrentPropertiesRepositoryTest {

    @Autowired
    private CaseCurrentPropertiesRepository caseCurrentPropertiesRepository;

    @Before
    public void setup() {

    }

    @Test
    public void shouldRoundTrip() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
        Long id = caseCurrentPropertiesRepository.save(caseProperties).getId();
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getUuid()).isEqualTo(caseProperties.getUuid());
        assertThat(returnedCaseProperties.getTimestamp()).isEqualTo(caseProperties.getTimestamp());
        assertThat(returnedCaseProperties.getCaseReference()).isEqualTo(caseProperties.getCaseReference());
        // etc...

        assertThat(returnedCaseProperties).isEqualTo(caseProperties);
    }

    @Test
    public void shouldRoundTripSimpleEquals() {
        Event event = getValidEvent();

        CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
        Long id = caseCurrentPropertiesRepository.save(caseProperties).getId();
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findById(id).get();

        CaseCurrentProperties newCaseProperties = new CaseCurrentProperties(event);

        assertThat(returnedCaseProperties).isEqualTo(newCaseProperties);
    }

    @Test
    public void shouldUpdate() {
        Event event = getValidEvent();

        String uuid = "uuidNew";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("advice", "do one");
        Event newEvent =  new Event(uuid, dateTime, caseRef, data);

        CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
        Long id = caseCurrentPropertiesRepository.save(caseProperties).getId();
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();

        returnedCaseProperties.update(newEvent);

        Long newId = caseCurrentPropertiesRepository.save(returnedCaseProperties).getId();
        CaseCurrentProperties returnedNewCaseProperties = caseCurrentPropertiesRepository.findById(newId).get();

        // See CasePropertiesRepoTest -> ShouldAllowMultiple to understand the difference.
        assertThat(returnedCaseProperties.getAdvice()).isEqualTo("do one");
        assertThat(returnedNewCaseProperties.getAdvice()).isEqualTo("do one");
        assertThat(returnedNewCaseProperties.getCaseReference()).isEqualTo(returnedCaseProperties.getCaseReference());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotAllowMultipleSameCaseRef() {
        Event event = getValidEvent();

        String uuid = "uuidNew";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("advice", "do one");
        Event newEvent =  new Event(uuid, dateTime, caseRef, data);

        CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
        Long id = caseCurrentPropertiesRepository.save(caseProperties).getId();
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();

        CaseCurrentProperties newCaseProperties = new CaseCurrentProperties(newEvent);
        Long newId = caseCurrentPropertiesRepository.save(newCaseProperties).getId();
    }

    private Event getValidEvent() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }


}