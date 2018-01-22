package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CaseCurrentPropertiesResourceIntTest {

    @Autowired
    private CaseCurrentPropertiesRepository repository;
    @Autowired
    private TestRestTemplate restTemplate;


    @Transactional
    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnWhenVaidDateRange() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now(), "CaseRef1");
        repository.save(new CaseCurrentProperties(event));

        ResponseEntity<CaseCurrentProperties[]> actualList = restTemplate.getForEntity("/currentProperties/DCU", CaseCurrentProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangePlus() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now().plusDays(1), "CaseRef1");
        repository.save(new CaseCurrentProperties(event));

        ResponseEntity<CaseCurrentProperties[]> actualList = restTemplate.getForEntity("/currentProperties/DCU", CaseCurrentProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangeMinus() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now().minusYears(1).minusDays(1), "CaseRef1");
        repository.save(new CaseCurrentProperties(event));

        ResponseEntity<CaseCurrentProperties[]> actualList = restTemplate.getForEntity("/currentProperties/DCU", CaseCurrentProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenEmpty() throws EntityCreationException {
        ResponseEntity<CaseCurrentProperties[]> actualList = restTemplate.getForEntity("/currentProperties/DCU", CaseCurrentProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldReturnWhenOutsideVaidDateRangeMulti() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "CaseRef1");
        repository.save(new CaseCurrentProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now(), "CaseRef2");
        repository.save(new CaseCurrentProperties(event2));

        Event event3 = getValidEventWithDate(LocalDate.now().plusDays(1), "CaseRef3");
        repository.save(new CaseCurrentProperties(event3));

        Event event4 = getValidEventWithDate(LocalDate.now().minusDays(1), "CaseRef4");
        repository.save(new CaseCurrentProperties(event4));

        ResponseEntity<CaseCurrentProperties[]> actualList = restTemplate.getForEntity("/currentProperties/DCU", CaseCurrentProperties[].class);
        assertThat(actualList.getBody()).hasSize(2);
    }

    private Event getValidEventWithDate(LocalDate localDate, String caseRef) {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        return new Event(uuid, dateTime, caseRef, data);
    }
}
