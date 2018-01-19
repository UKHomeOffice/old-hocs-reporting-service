package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
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
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findOne(id);

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

        CaseCurrentProperties caseProperties = new CaseCurrentProperties(event);
        Long id = caseCurrentPropertiesRepository.save(caseProperties).getId();
        CaseCurrentProperties returnedCaseProperties = caseCurrentPropertiesRepository.findOne(id);

        CaseCurrentProperties newCaseProperties = new CaseCurrentProperties(event);

        assertThat(returnedCaseProperties).isEqualTo(newCaseProperties);
    }
}