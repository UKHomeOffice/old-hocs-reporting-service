package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.CaseStatus;
import uk.gov.digital.ho.hocs.model.CaseType;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DashboardResourceIntTest {

    @Autowired
    private CaseCurrentPropertiesRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    private int caseReference = 0;
    private ResponseEntity<String> responseEntity;
    private static final String DASHBOARD_SUMMARY_URL = "/dashboardSummary";
    private static final String CORRESPONDENCE_TYPE = "correspondenceType";
    private static final String CASE_STATUS = "caseStatus";
    private static final String TOTAL = "total";
    private static final String NAME = "name";
    private static final HashMap<String,String> min  = new HashMap<String, String>() {{
        put(TOTAL, "6");
        put(NAME, CaseType.MIN.name());
    }};
    private static final HashMap<String,String> tro  = new HashMap<String, String>() {{
        put(TOTAL, "5");
        put(NAME, CaseType.TRO.name());
    }};
    private static final HashMap<String,String> dten  = new HashMap<String, String>() {{
        put(TOTAL, "0");
        put(NAME, CaseType.DTEN.name());
    }};

    @Transactional
    @Before
    public void setup() {
        repository.deleteAll();
        SeedDatabase();
    }

    @Test
    public void shouldOnlyReturnCaseSummaryForMINCases(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"6\",\n" +
                "    \"name\" : \"MIN\"\n" +
                "  } ]\n" +
                "}";

        postRequest(new ArrayList<CaseType>() {{ add(CaseType.MIN); }});

        assertThat(responseEntity.getBody()).contains(min.values());
        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldNotReturnCaseSummaryForTROCasesWhenOnlyMINCasesRequested(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"6\",\n" +
                "    \"name\" : \"MIN\"\n" +
                "  } ]\n" +
                "}";

        postRequest(new ArrayList<CaseType>() {{ add(CaseType.MIN); }});
        assertThat(responseEntity.getBody()).contains(min.values());
        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
        assertThat(responseEntity.getBody()).doesNotContain(tro.values());
    }

    @Test
    public void shouldReturnCaseSummaryForMultipleCaseTypesWhenRequested(){

        postRequest(new ArrayList<CaseType>() {{ add(CaseType.MIN);add(CaseType.TRO);add(CaseType.DTEN); }});

        assertThat(responseEntity.getBody()).contains(min.values());
        assertThat(responseEntity.getBody()).contains(tro.values());
        assertThat(responseEntity.getBody()).contains(dten.values());
    }

    @Test
    public void shouldReturnZeroNumberWhenNoOpenCaseTypesFound(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"0\",\n" +
                "    \"name\" : \"DTEN\"\n" +
                "  } ]\n" +
                "}";

        postRequest( new ArrayList<CaseType>() {{ add(CaseType.DTEN); }});
        assertThat(responseEntity.getBody()).contains(dten.values());
        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenCaseTypeIsNull() {

        postRequest(new ArrayList<CaseType>() {{ add(null); }});;

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldReturnBodyWithNoCaseTypesOrValues(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ ]\n" +
                "}";

        postRequest(new ArrayList<>());

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    private void postRequest(List<CaseType> caseTypes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(caseTypes, headers);
        responseEntity = restTemplate.postForEntity(DASHBOARD_SUMMARY_URL, request, String.class);
    }

    public void SeedDatabase(){
        creatAndSaveEntryToDatabase(CaseType.MIN, CaseStatus.Completed,3);
        creatAndSaveEntryToDatabase(CaseType.MIN, CaseStatus.New,2);
        creatAndSaveEntryToDatabase(CaseType.MIN, CaseStatus.Draft,4);
        creatAndSaveEntryToDatabase(CaseType.TRO, CaseStatus.Completed,3);
        creatAndSaveEntryToDatabase(CaseType.TRO, CaseStatus.New,4);
        creatAndSaveEntryToDatabase(CaseType.TRO, CaseStatus.Draft,1);
        creatAndSaveEntryToDatabase(CaseType.DTEN, CaseStatus.Completed,1);
    }

    public void creatAndSaveEntryToDatabase(CaseType caseType, CaseStatus caseStatus, int quantity){
        for (int i = 0; i < quantity; i++) {
            Map<String, String> data = new HashMap<>();
            data.put(CORRESPONDENCE_TYPE, caseType.name());
            data.put(CASE_STATUS, caseStatus.name());
            caseReference++;
            Event event = new Event(UUID.randomUUID().toString(), LocalDateTime.now(), caseType + valueOf(caseReference), data);

            repository.save(new CaseCurrentProperties(event));
        }
    }
}