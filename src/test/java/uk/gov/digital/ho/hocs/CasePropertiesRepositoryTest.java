package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
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
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findOne(id);

        assertThat(returnedCaseProperties.getUuid()).isEqualTo(caseProperties.getUuid());
        assertThat(returnedCaseProperties.getTimestamp()).isEqualTo(caseProperties.getTimestamp());
        assertThat(returnedCaseProperties.getCaseReference()).isEqualTo(caseProperties.getCaseReference());
        // etc...

        assertThat(returnedCaseProperties).isEqualTo(caseProperties);
    }

    @Test
    public void shouldRoundTripSimpleEquals() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> before = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);
        Long id = casePropertiesRepository.save(caseProperties).getId();
        CaseProperties returnedCaseProperties = casePropertiesRepository.findOne(id);

        CaseProperties newCaseProperties = new CaseProperties(event);

        assertThat(returnedCaseProperties).isEqualTo(newCaseProperties);
    }
}