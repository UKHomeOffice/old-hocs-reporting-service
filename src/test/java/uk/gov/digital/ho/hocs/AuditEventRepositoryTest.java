package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.model.AuditEvent;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class AuditEventRepositoryTest {

    @Autowired
    private AuditEventRepository auditEventRepository;

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

        AuditEvent auditEvent = new AuditEvent(event);
        Long id = auditEventRepository.save(auditEvent).getId();
        AuditEvent returnedAuditEvent = auditEventRepository.findOne(id);

        assertThat(returnedAuditEvent.getUuid()).isEqualTo(auditEvent.getUuid());
        assertThat(returnedAuditEvent.getTimestamp()).isEqualTo(auditEvent.getTimestamp());
        assertThat(returnedAuditEvent.getCaseReference()).isEqualTo(auditEvent.getCaseReference());
        assertThat(returnedAuditEvent.getData()).isEqualTo(auditEvent.getData());

        assertThat(returnedAuditEvent).isEqualTo(auditEvent);
    }

    @Test
    public void shouldRoundTripSimpleEquals() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String caseRef = "CaseRef";
        Map<String, String> data = new HashMap<>();
        Event event = new Event(uuid, dateTime, caseRef, data);

        AuditEvent auditEvent = new AuditEvent(event);
        Long id = auditEventRepository.save(auditEvent).getId();
        AuditEvent returnedAuditEvent = auditEventRepository.findOne(id);

        AuditEvent newAuditEvent = new AuditEvent(event);

        assertThat(returnedAuditEvent).isEqualTo(newAuditEvent);
    }
}