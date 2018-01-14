package uk.gov.digital.ho.hocs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
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
