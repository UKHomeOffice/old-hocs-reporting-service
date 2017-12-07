package uk.gov.digital.ho.hocs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class AuditEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "uuid", nullable = false)
    @Getter
    private String uuid;

    @Column(name = "timestamp", nullable = false)
    @Getter
    private LocalDateTime timestamp;

    @Column(name = "node_reference", nullable = false)
    @Getter
    private String nodeReference;

    @Column(name = "case_reference", nullable = false)
    @Getter
    private String caseReference;

    @Column(name = "case_type", nullable = false)
    @Getter
    private String caseType;

    @Column(name = "before", nullable = false)
    @Getter
    private String before;

    @Column(name = "after", nullable = false)
    @Getter
    private String after;

    public AuditEvent(String uuid, LocalDateTime timeStamp, String nodeReference, String caseReference, String caseType, String before, String after){
        this.uuid = uuid;
        this.timestamp = timeStamp;
        this.nodeReference = nodeReference;
        this.caseReference = caseReference;
        this.caseType = caseType;
        this.before = before;
        this.after = after;
    }

}