package uk.gov.digital.ho.hocs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@ToString
public class Event {

    @Getter
    private String uuid;

    @Getter
    private LocalDateTime timestamp;

    @Getter
    private String nodeReference;

    @Getter
    private String caseReference;

    @Getter
    private String caseType;

    @Getter
    private Map<String,String> before;

    @Getter
    private Map<String,String> after;

}
