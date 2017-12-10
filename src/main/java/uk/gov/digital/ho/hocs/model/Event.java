package uk.gov.digital.ho.hocs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
//TODO: ToString only used by temp file writing code in resource.
@ToString
public class Event {

    @Getter
    private String uuid;

    @Getter
    private LocalDateTime timestamp;

    @Getter
    private String caseReference;

    @Getter
    private Map<String,String> data;
}
