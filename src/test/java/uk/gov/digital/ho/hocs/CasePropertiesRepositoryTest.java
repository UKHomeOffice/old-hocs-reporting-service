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
import uk.gov.digital.ho.hocs.model.CaseProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class CasePropertiesRepositoryTest {

    @Autowired
    private CasePropertiesRepository casePropertiesRepository;

    @Before
    public void setup() {

    }

    @Test
    public void shouldRoundTrip() {
        Event event = getValidEvent();

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getUuid()).isEqualTo(caseProperties.getUuid());
        assertThat(returnedCaseProperties.getTimestamp()).isEqualTo(caseProperties.getTimestamp());
        assertThat(returnedCaseProperties.getCaseReference()).isEqualTo(caseProperties.getCaseReference());
        // etc...

        assertThat(returnedCaseProperties).isEqualTo(caseProperties);
    }

    @Test
    public void shouldRoundTripSimpleEquals() {
        Event event = getValidEvent();

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findById(id).get();

        CaseProperties newCaseProperties = new CaseProperties(event);

        assertThat(returnedCaseProperties).isEqualTo(newCaseProperties);
    }



    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotAllowIdentical() {
        Event event = getValidEvent();

        Event newEvent = event;

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();

        CaseProperties newCaseProperties = new CaseProperties(newEvent);
        Long newId = casePropertiesRepository.save(newCaseProperties).getId();
    }

    @Test
    public void shouldAllowMultipleSameCaseRef() {
        Event event = getValidEvent();

        String uuid = "uuidNew";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("advice", "do one");
        Event newEvent =  new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();

        CaseProperties newCaseProperties = new CaseProperties(newEvent);
        Long newId = casePropertiesRepository.save(newCaseProperties).getId();
        CaseProperties returnedNewCaseProperties = casePropertiesRepository.findById(newId).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();
        assertThat(returnedNewCaseProperties.getAdvice()).isEqualTo("do one");
        assertThat(returnedNewCaseProperties.getCaseReference()).isEqualTo(returnedCaseProperties.getCaseReference());

    }

    @Test
    public void shouldAllowMultipleUnrelated() {
        Event event = getValidEvent();

        String uuid = "uuidNew";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRefNew";
        Map<String, String> data = new HashMap<>();
        data.put("advice", "do one");
        Event newEvent =  new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findById(id).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();

        CaseProperties newCaseProperties = new CaseProperties(newEvent);
        Long newId = casePropertiesRepository.save(newCaseProperties).getId();
        CaseProperties returnedNewCaseProperties = casePropertiesRepository.findById(newId).get();

        assertThat(returnedCaseProperties.getAdvice()).isNull();
        assertThat(returnedNewCaseProperties.getAdvice()).isEqualTo("do one");
        assertThat(returnedNewCaseProperties.getCaseReference()).isNotEqualTo(returnedCaseProperties.getCaseReference());

    }

    private Event getValidEvent() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        return new Event(uuid, dateTime, caseRef, data);
    }
}