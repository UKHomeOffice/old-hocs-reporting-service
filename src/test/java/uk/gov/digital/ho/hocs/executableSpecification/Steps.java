package uk.gov.digital.ho.hocs.executableSpecification;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.digital.ho.hocs.CaseCurrentPropertiesRepository;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class Steps {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CaseCurrentPropertiesRepository caseCurrentPropertiesRepository;

    Gson gson = new Gson();
    private int caseReference = 0;
    private ResponseEntity<String> responseEntity;

    @Before
    public void setup() {
        caseCurrentPropertiesRepository.deleteAll();
    }

    @Given("^these numbers of each case types are in the service:$")
    public void theseNumbersOfEachCaseTypesAreInTheService(DataTable rawData) throws Throwable {
        List<List<String>> data = rawData.raw();
        Map<String, String> map = new HashMap<>();
        for (List<String> separateData : data) {
            for (int i = 0; i < Integer.valueOf(separateData.get(1)); i++) {
                map.put("correspondenceType", separateData.get(0));
                map.put("caseStatus", separateData.get(2));
                caseReference++;

                Event event = new Event(UUID.randomUUID().toString(), LocalDateTime.now(), separateData.get(0) + valueOf(caseReference), map);

                caseCurrentPropertiesRepository.save(new CaseCurrentProperties(event));
            }
        }
    }

    @When("^requested to return data for case types \"([^\"]*)\"$")
    public void requestedToReturnDataForCaseTypes(String arg0) throws Throwable {
        String json = gson.toJson(Arrays.asList(arg0.split(",")));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(json, headers);
        responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/dashboardSummary", request, String.class);
    }

    @Then("^this case type summary data is returned:$")
    public void thisCaseTypeSummaryDataIsReturned(DataTable caseTypeSummary) throws Throwable {
        Map<String, String> checkData = caseTypeSummary.asMap(String.class, String.class);

        assertThat(responseEntity.getBody()).contains(checkData.values());
        assertThat(responseEntity.getBody()).contains(checkData.keySet());
    }
}


