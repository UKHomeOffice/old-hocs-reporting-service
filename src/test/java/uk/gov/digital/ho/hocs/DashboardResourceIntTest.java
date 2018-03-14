package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.valueOf;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DashboardResourceIntTest {

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
    public void test(){

    }

    public void seedDatabase(){
        int caseReference = 0;
        Map<String, String> data = new HashMap<>();
        data.put("correspondenceType","MIN");
        data.put("caseStatus", "New");
        caseReference++;
        Event event = new Event(UUID.randomUUID().toString(), LocalDateTime.now(),  valueOf(caseReference), data);

        repository.save(new CaseCurrentProperties(event));
    }

}