package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import uk.gov.digital.ho.hocs.model.CaseProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CasePropertiesResourceIntTest {

    @Autowired
    private CasePropertiesRepository repository;
    @Autowired
    private TestRestTemplate restTemplate;

    private String testDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

    @Transactional
    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnWhenVaidDateRange() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
        assertThat(actualList.getBody()[0].getUuid()).isEqualTo("UUID1");
    }

    @Test
    public void shouldReturnWhenVaidDateRangeCsv() throws EntityCreationException, IOException {
        Event event = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(1);
        assertThat(caseCurrentProperties.get(0).getUuid()).isEqualTo("UUID1");
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangePlus() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now().plusDays(1), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangePlusCsv() throws EntityCreationException, IOException {
        Event event = getValidEventWithDate(LocalDate.now().plusDays(1), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangeMinus() throws EntityCreationException {
        Event event = getValidEventWithDate(LocalDate.now().minusYears(1).minusDays(1), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenOutsideVaidDateRangeMinusCsv() throws EntityCreationException, IOException {
        Event event = getValidEventWithDate(LocalDate.now().minusYears(1).minusDays(1), "UUID1");
        repository.save(new CaseProperties(event));

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenEmpty() throws EntityCreationException {
        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(0);
    }

    @Test
    public void shouldNotReturnWhenEmptyCsv() throws EntityCreationException, IOException {
        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(0);
    }

    @Test
    public void shouldUpdateWhenSameUUID() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now(), "new uuid");
        event2.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
        assertThat(actualList.getBody()[0].getUuid()).isEqualTo("new uuid");
        assertThat(actualList.getBody()[0].getAdvice()).isEqualTo("do one");
    }

    @Test
    public void shouldUpdateWhenSameUUIDCsv() throws EntityCreationException, IOException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now(), "new uuid");
        event2.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(1);
        assertThat(caseCurrentProperties.get(0).getUuid()).isEqualTo("new uuid");
        assertThat(caseCurrentProperties.get(0).getAdvice()).isEqualTo("do one");
    }

    @Test
    public void shouldUpdateWhenSameTime() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        LocalDateTime dateTime = event1.getTimestamp();
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        Event event2 = new Event("new uuid", dateTime, "CaseRef", data);

        event2.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
        assertThat(actualList.getBody()[0].getUuid()).isEqualTo("new uuid");
        assertThat(actualList.getBody()[0].getAdvice()).isEqualTo("do one");
    }

    @Test
    public void shouldUpdateWhenSameTimeCsv() throws EntityCreationException, IOException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        LocalDateTime dateTime = event1.getTimestamp();
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        Event event2 = new Event("new uuid", dateTime, "CaseRef", data);

        event2.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(1);
        assertThat(caseCurrentProperties.get(0).getUuid()).isEqualTo("new uuid");
        assertThat(caseCurrentProperties.get(0).getAdvice()).isEqualTo("do one");
    }

    @Test
    public void shouldNotUpdateWhenSameTimeSaveUUID() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        event1.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event1, String.class);

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
        assertThat(actualList.getBody()[0].getAdvice()).isNull();
    }

    @Test
    public void shouldNotUpdateWhenSameTimeSameUUIDCsv() throws EntityCreationException, IOException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        event1.getData().put("advice", "do one");
        restTemplate.postForEntity("/event/add", event1, String.class);

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);

        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(1);
        assertThat(caseCurrentProperties.get(0).getAdvice()).isEqualTo("");
    }


    @Test
    public void shouldUpdateWhenDifferentTime() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        String uuid = "new uuid";
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN.plusMinutes(5));
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        data.put("advice", "do one");
        Event event2 = new  Event(uuid, dateTime, "CaseRef", data);
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
        assertThat(actualList.getBody()[0].getUuid()).isEqualTo("new uuid");
        assertThat(actualList.getBody()[0].getAdvice()).isEqualTo("do one");
    }

    @Test
    public void shouldNotUpdateWhenDifferentTimeCsv() throws EntityCreationException, IOException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        String uuid = "new uuid";
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN.plusMinutes(5));
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        data.put("advice", "do one");
        Event event2 = new  Event(uuid, dateTime, "CaseRef", data);
        restTemplate.postForEntity("/event/add", event2, String.class);

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);
        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties.get(0).getUuid()).isEqualTo("new uuid");
        assertThat(caseCurrentProperties.get(0).getAdvice()).contains("do one");
    }

    @Test
    public void shouldReturnWhenOutsideVaidDateRangeMulti() throws EntityCreationException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now(), "UUID2");
        repository.save(new CaseProperties(event2));

        Event event3 = getValidEventWithDate(LocalDate.now().plusDays(1), "UUID3");
        repository.save(new CaseProperties(event3));

        Event event4 = getValidEventWithDate(LocalDate.now().minusDays(1), "UUID4");
        repository.save(new CaseProperties(event4));

        ResponseEntity<CaseProperties[]> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/json", CaseProperties[].class);
        assertThat(actualList.getBody()).hasSize(1);
    }

    @Test
    public void shouldReturnWhenOutsideVaidDateRangeMultiCsv() throws EntityCreationException, IOException {
        Event event1 = getValidEventWithDate(LocalDate.now(), "UUID1");
        repository.save(new CaseProperties(event1));

        Event event2 = getValidEventWithDate(LocalDate.now(), "UUID2");
        repository.save(new CaseProperties(event2));

        Event event3 = getValidEventWithDate(LocalDate.now().plusDays(1), "UUID3");
        repository.save(new CaseProperties(event3));

        Event event4 = getValidEventWithDate(LocalDate.now().minusDays(1), "UUID4");
        repository.save(new CaseProperties(event4));

        ResponseEntity<String> actualList = restTemplate.getForEntity("/cases/" + testDate + "/DCU/csv", String.class);
        List<CaseProperties> caseCurrentProperties = getCasePropertiesCsv(actualList);
        assertThat(caseCurrentProperties).hasSize(1);
    }

    private List<CaseProperties> getCasePropertiesCsv(ResponseEntity<String> actualList) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(CaseProperties.class).withHeader();
        ReportingServiceConfiguration.initialiseObjectMapper(csvMapper);
        ObjectReader reader = csvMapper.readerFor(CaseProperties.class).with(csvSchema);
        MappingIterator<CaseProperties> caseCurrentPropertiesMappingIterator = reader.readValues(actualList.getBody());
        return caseCurrentPropertiesMappingIterator.readAll();
    }

    private Event getValidEventWithDate(LocalDate localDate, String uuid) {
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.now());
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        return new Event(uuid, dateTime, "CaseRef", data);
    }
}
