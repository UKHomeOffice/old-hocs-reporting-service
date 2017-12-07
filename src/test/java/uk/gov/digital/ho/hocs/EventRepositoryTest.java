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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Before
    public void setup() {

    }

    @Test
    public void shouldRoundTrip() {
        String uuid = "uuid";
        LocalDateTime dateTime = LocalDateTime.now();
        String nodeRef = "NodeRef";
        String caseRef = "CaseRef";
        String caseType = "CaseType";
        String before = "Before";
        String after = "After";

        AuditEvent auditEvent = new AuditEvent(uuid,dateTime, nodeRef, caseRef, caseType, before, after);
        Long id = eventRepository.save(auditEvent).getId();

        AuditEvent returnedAuditEvent = eventRepository.findOne(id);

        assertThat(returnedAuditEvent.getUuid()).isEqualTo(uuid);
        assertThat(returnedAuditEvent.getTimestamp()).isEqualTo(dateTime);
        assertThat(returnedAuditEvent.getNodeReference()).isEqualTo(nodeRef);
        assertThat(returnedAuditEvent.getCaseReference()).isEqualTo(caseRef);
        assertThat(returnedAuditEvent.getBefore()).isEqualTo(before);
        assertThat(returnedAuditEvent.getAfter()).isEqualTo(after);
    }
}