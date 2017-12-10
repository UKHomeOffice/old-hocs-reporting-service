package uk.gov.digital.ho.hocs.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CasePropertiesTest {

    @Test
    public void createWithEntities() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getUuid()).isEqualTo(uuid);
        assertThat(caseProperties.getTimestamp()).isEqualTo(dateTime);
        assertThat(caseProperties.getCaseReference()).isEqualTo(caseRef);
    }

    @Test
    public void createWithoutEntities() throws Exception {
        Event event = new Event(null, null, null, null);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getUuid()).isEqualTo("");
        assertThat(caseProperties.getTimestamp()).isNotNull();
        assertThat(caseProperties.getCaseReference()).isEqualTo("");

    }

    @Test
    public void eventMapBehaviourValid() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("caseStatus", "good");
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getCaseStatus()).isEqualTo("good");

    }

    @Test
    public void eventMapBehaviourNoValue() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getCaseStatus()).isNull();

    }

    @Test
    public void eventMapBehaviourEmptyString() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("caseStatus", "");
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getCaseStatus()).isNull();

    }

    @Test
    public void eventMapBehaviourNull() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("caseStatus", null);
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getCaseStatus()).isNull();

    }

    @Test
    public void eventMapBehaviourStringNull() throws Exception {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        data.put("caseStatus", "null");
        Event event = new Event(uuid, dateTime, caseRef, data);

        CaseProperties caseProperties = new CaseProperties(event);

        assertThat(caseProperties.getCaseStatus()).isNull();

    }

    @Test
    public void EqualsOnlyByIdentity() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();

        Event eventOne = new Event("uuid", dateTime, "CaseRef", new HashMap<>());
        CaseProperties casePropertiesOne = new CaseProperties(eventOne);

        Event eventTwo = new Event("uuid", dateTime, "otherCaseRef", new HashMap<>());
        CaseProperties casePropertiesTwo = new CaseProperties(eventTwo);

        assertThat(casePropertiesOne).isEqualTo(casePropertiesTwo);
    }

    @Test
    public void NotsEqualsOnlyByIdentityUUID() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();

        Event eventOne = new Event("uuid", dateTime, "CaseRef", new HashMap<>());
        CaseProperties casePropertiesOne = new CaseProperties(eventOne);

        Event eventTwo = new Event("otheruuid", dateTime, "CaseRef", new HashMap<>());
        CaseProperties casePropertiesTwo = new CaseProperties(eventTwo);

        assertThat(casePropertiesOne).isNotEqualTo(casePropertiesTwo);
    }

    @Test
    public void NotsEqualsOnlyByIdentityTimestamp() throws Exception {
        LocalDateTime dateTimeOne = LocalDateTime.now();
        LocalDateTime dateTimeTwo = LocalDateTime.now().minusSeconds(1L);

        Event eventOne = new Event("uuid", dateTimeOne, "CaseRef", new HashMap<>());
        CaseProperties casePropertiesOne = new CaseProperties(eventOne);

        Event eventTwo = new Event("uuid", dateTimeTwo, "CaseRef", new HashMap<>());
        CaseProperties casePropertiesTwo = new CaseProperties(eventTwo);

        assertThat(casePropertiesOne).isNotEqualTo(casePropertiesTwo);
    }

}