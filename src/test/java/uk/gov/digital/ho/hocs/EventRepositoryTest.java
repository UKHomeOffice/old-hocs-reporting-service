package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.digital.ho.hocs.model.Event;

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

        Event event = new Event(uuid,dateTime, nodeRef, caseRef, caseType, before, after);
        Long id = eventRepository.save(event).getId();

        Event returnedEvent = eventRepository.findOne(id);

        assertThat(returnedEvent.getUuid()).isEqualTo(uuid);
        assertThat(returnedEvent.getTimestamp()).isEqualTo(dateTime);
        assertThat(returnedEvent.getNodeReference()).isEqualTo(nodeRef);
        assertThat(returnedEvent.getCaseReference()).isEqualTo(caseRef);
        assertThat(returnedEvent.getBefore()).isEqualTo(before);
        assertThat(returnedEvent.getAfter()).isEqualTo(after);
    }
}