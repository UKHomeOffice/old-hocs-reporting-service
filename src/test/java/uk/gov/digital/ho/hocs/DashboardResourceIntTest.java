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
    private static final String MIN = "MIN";
    private static final String TRO = "TRO";
    private static final String DTEN = "DTEN";
    private static final String COMPLETED = "Completed";
    private static final String NEW = "New";
    private static final String DRAFT = "Draft";
    private static final String DASHBOARD_SUMMARY_URL = "/dashboardSummary";
    private static final String CORRESPONDENCE_TYPE = "correspondenceType";
    private static final String CASE_STATUS = "caseStatus";

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

        postRequest(new ArrayList<String>() {{ add(MIN); }});

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

        postRequest(new ArrayList<String>() {{ add(MIN); }});

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnCaseSummaryForMutlipleCaseTypesWhenRequested(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"6\",\n" +
                "    \"name\" : \"MIN\"\n" +
                "  }, {\n" +
                "    \"total\" : \"5\",\n" +
                "    \"name\" : \"TRO\"\n" +
                "  }, {\n" +
                "    \"total\" : \"0\",\n" +
                "    \"name\" : \"DTEN\"\n" +
                "  } ]\n" +
                "}";

        postRequest(new ArrayList<String>() {{ add(MIN);add(TRO);add(DTEN); }});

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnZeroNumberWhenNoOpenCaseTypesFound(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"0\",\n" +
                "    \"name\" : \"DTEN\"\n" +
                "  } ]\n" +
                "}";

        postRequest( new ArrayList<String>() {{ add(DTEN); }});

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnEmptyHashMapWhenEmptyStringUsedAsCaseType(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"0\",\n" +
                "    \"name\" : \"\"\n" +
                "  } ]\n" +
                "}";

        postRequest(new ArrayList<String>() {{ add(""); }});

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnEmptyHashMapWhenNullUsedAsCaseType(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ {\n" +
                "    \"total\" : \"0\",\n" +
                "    \"name\" : null\n" +
                "  } ]\n" +
                "}";

        postRequest(new ArrayList<String>() {{ add(null); }});

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    @Test
    public void shouldReturnBodyWithNoCaseTypesOrValues(){

        String expectedBodyAsJson = "{\n" +
                "  \"summary\" : [ ]\n" +
                "}";

        postRequest(new ArrayList<>());

        assertThat(responseEntity.getBody()).isEqualTo(expectedBodyAsJson);
    }

    private void postRequest(List<String> caseTypes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(caseTypes, headers);
        responseEntity = restTemplate.postForEntity(DASHBOARD_SUMMARY_URL, request, String.class);
    }

    public void SeedDatabase(){
        creatAndSaveEntryToDatabase(MIN, COMPLETED,3);
        creatAndSaveEntryToDatabase(MIN, NEW,2);

        creatAndSaveEntryToDatabase(MIN, DRAFT,4);
        creatAndSaveEntryToDatabase(TRO, COMPLETED,3);
        creatAndSaveEntryToDatabase(TRO, NEW,4);
        creatAndSaveEntryToDatabase(TRO, DRAFT,1);
        creatAndSaveEntryToDatabase(DTEN, COMPLETED,1);
    }

    public void creatAndSaveEntryToDatabase(String caseType, String caseStatus, int quantity){
        for (int i = 0; i < quantity; i++) {
            Map<String, String> data = new HashMap<>();
            data.put(CORRESPONDENCE_TYPE, caseType);
            data.put(CASE_STATUS, caseStatus);
            caseReference++;
            Event event = new Event(UUID.randomUUID().toString(), LocalDateTime.now(), caseType + valueOf(caseReference), data);

            repository.save(new CaseCurrentProperties(event));
        }
    }
}