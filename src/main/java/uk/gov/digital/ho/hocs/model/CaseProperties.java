package uk.gov.digital.ho.hocs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "properties")
@Access(AccessType.FIELD)
@Getter
@EqualsAndHashCode(of = {"uuid", "timestamp"})
public class CaseProperties {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "msg_uuid")
    private String uuid = "";

    @Column(name = "msg_timestamp")
    private LocalDateTime timestamp;

    @Column(name = "case_reference")
    private String caseReference = "";

    @Column(name = "advice")
    private String advice;

    @Column(name = "allocate_to_responder_target")
    private String allocateToResponderTarget;

    @Column(name = "assigned_team")
    private String assignedTeam;

    @Column(name = "assigned_unit")
    private String assignedUnit;

    @Column(name = "assigned_user")
    private String assignedUser;

    @Column(name = "auto_created_case")
    private String autoCreatedCase;

    @Column(name = "case_ref")
    private String caseRef;

    @Column(name = "case_response_deadline")
    private String caseResponseDeadline;

    @Column(name = "case_task")
    private String caseTask;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "channel")
    private String channel;

    @Column(name = "comment_count")
    private String commentCount;

    @Column(name = "correspondent_address_line1")
    private String correspondentAddressLine1;

    @Column(name = "correspondent_address_line2")
    private String correspondentAddressLine2;

    @Column(name = "correspondent_address_line3")
    private String correspondentAddressLine3;

    @Column(name = "correspondent_country")
    private String correspondentCountry;

    @Column(name = "correspondent_email")
    private String correspondentEmail;

    @Column(name = "correspondent_forename")
    private String correspondentForename;

    @Column(name = "correspondent_postcode")
    private String correspondentPostcode;

    @Column(name = "correspondent_surname")
    private String correspondentSurname;

    @Column(name = "correspondent_telephone")
    private String correspondentTelephone;

    @Column(name = "correspondent_title")
    private String correspondentTitle;

    @Column(name = "correspondence_type")
    private String correspondenceType;

    @Column(name = "created")
    private String created;

    @Column(name = "creator")
    private String creator;

    @Column(name = "date_of_letter")
    private String dateOfLetter;

    @Column(name = "date_received")
    private String dateReceived;

    @Column(name = "draft_response_target")
    private String draftResponseTarget;

    @Column(name = "hmpo_response")
    private String hmpoResponse;

    @Column(name = "home_secretary_reply")
    private String homeSecretaryReply;

    @Column(name = "is_grouped_master")
    private String isGroupedMaster;

    @Column(name = "is_grouped_slave")
    private String isGroupedSlave;

    @Column(name = "is_linked_case")
    private String isLinkedCase;

    @Column(name = "markup_decision")
    private String markupDecision;

    @Column(name = "markup_minister")
    private String markupMinister;

    @Column(name = "markup_topic")
    private String markupTopic;

    @Column(name = "markup_team")
    private String markupTeam;

    @Column(name = "markup_unit")
    private String markupUnit;

    @Column(name = "member")
    private String member;

    @Column(name = "minutes_collated")
    private String minutesCollated;

    @Column(name = "modified")
    private String modified;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "mp_ref")
    private String mpRef;

    @Column(name = "name")
    private String name;

    @Column(name = "nodedbid")
    private String nodedbid;

    @Column(name = "nodeuuid")
    private String nodeuuid;

    @Column(name = "ogd_name")
    private String ogdName;

    @Column(name = "original_drafter_team")
    private String originalDrafterTeam;

    @Column(name = "original_drafter_unit")
    private String originalDrafterUnit;

    @Column(name = "original_drafter_user")
    private String originalDrafterUser;

    @Column(name = "owner_updated_datetime")
    private String ownerUpdatedDatetime;

    @Column(name = "parly_dispatch")
    private String parlyDispatch;

    @Column(name = "pq_api_created_case")
    private String pqApiCreatedCase;

    @Column(name = "priority")
    private String priority;

    @Column(name = "reply_to_address_line1")
    private String replyToAddressLine1;

    @Column(name = "reply_to_address_line2")
    private String replyToAddressLine2;

    @Column(name = "reply_to_address_line3")
    private String replyToAddressLine3;

    @Column(name = "reply_to_country")
    private String replyToCountry;

    @Column(name = "reply_to_email")
    private String replyToEmail;

    @Column(name = "reply_to_name")
    private String replyToName;

    @Column(name = "reply_to_postcode")
    private String replyToPostcode;

    @Column(name = "reply_to_telephone")
    private String replyToTelephone;

    @Column(name = "reply_to_number_ten_copy")
    private String replyToNumberTenCopy;

    @Column(name = "responder_hub_target")
    private String responderHubTarget;

    @Column(name = "return_case_at")
    private String returnCaseAt;

    @Column(name = "returned_count")
    private String returnedCount;

    @Column(name = "reviewed_by_perm_sec")
    private String reviewedByPermSec;

    @Column(name = "reviewed_by_spads")
    private String reviewedBySpads;

    @Column(name = "round_robin")
    private String roundRobin;

    @Column(name = "secondary_correspondent_reply_to")
    private String secondaryCorrespondentReplyTo;

    @Column(name = "secondary_topic")
    private String secondaryTopic;

    @Column(name = "signed_by_home_sec")
    private String signedByHomeSec;

    @Column(name = "signed_by_lords_minister")
    private String signedByLordsMinister;

    @Column(name = "status_updated_datetime")
    private String statusUpdatedDatetime;

    @Column(name = "task_updated_datetime")
    private String taskUpdatedDatetime;

    @Column(name = "third_party_correspondent_address_line1")
    private String thirdPartyCorrespondentAddressLine1;

    @Column(name = "third_party_correspondent_address_line2")
    private String thirdPartyCorrespondentAddressLine2;

    @Column(name = "third_party_correspondent_address_line3")
    private String thirdPartyCorrespondentAddressLine3;

    @Column(name = "third_party_correspondent_country")
    private String thirdPartyCorrespondentCountry;

    @Column(name = "third_party_correspondent_email")
    private String thirdPartyCorrespondentEmail;

    @Column(name = "third_party_correspondent_forename")
    private String thirdPartyCorrespondentForename;

    @Column(name = "third_party_correspondent_organisation")
    private String thirdPartyCorrespondentOrganisation;

    @Column(name = "third_party_correspondent_postcode")
    private String thirdPartyCorrespondentPostcode;

    @Column(name = "third_party_correspondent_reply_to")
    private String thirdPartyCorrespondentReplyTo;

    @Column(name = "third_party_correspondent_surname")
    private String thirdPartyCorrespondentSurname;

    @Column(name = "third_party_correspondent_telephone")
    private String thirdPartyCorrespondentTelephone;

    @Column(name = "third_party_correspondent_title")
    private String thirdPartyCorrespondentTitle;

    @Column(name = "urn_suffix")
    private String urnSuffix;

    public CaseProperties(Event event) {
        if(event.getUuid() != null) {
            this.uuid = event.getUuid();
        }
        if(event.getTimestamp() != null) {
            this.timestamp = event.getTimestamp();
        }
        else {
            this.timestamp = LocalDateTime.now();
        }
        if(event.getCaseReference() != null) {
            this.caseReference = event.getCaseReference();
        }

        Map<String,String> after = event.getAfter();

        advice  = SetString("advice", after);
        allocateToResponderTarget  = SetString("allocateToResponderTarget", after);
        assignedTeam = SetString("assignedTeam", after);
        assignedUnit = SetString("assignedUnit", after);
        assignedUser = SetString("assignedUser", after);
        autoCreatedCase  = SetString("autoCreatedCase", after);
        caseRef  = SetString("caseRef", after);
        caseResponseDeadline  = SetString("caseResponseDeadline", after);
        caseTask = SetString("caseTask", after);
        caseStatus = SetString("caseStatus", after);
        channel  = SetString("channel", after);
        commentCount  = SetString("commentCount", after);
        correspondentAddressLine1 = SetString("correspondentAddressLine1", after);
        correspondentAddressLine2 = SetString("correspondentAddressLine2", after);
        correspondentAddressLine3 = SetString("correspondentAddressLine3", after);
        correspondentCountry = SetString("correspondentCountry", after);
        correspondentEmail = SetString("correspondentEmail", after);
        correspondentForename = SetString("correspondentForename", after);
        correspondentPostcode = SetString("correspondentPostcode", after);
        correspondentSurname  = SetString("correspondentSurname", after);
        correspondentTelephone  = SetString("correspondentTelephone", after);
        correspondentTitle  = SetString("correspondentTitle", after);
        correspondenceType  = SetString("correspondenceType", after);
        created  = SetString("created", after);
        creator  = SetString("creator", after);
        dateOfLetter  = SetString("dateOfLetter", after);
        dateReceived  = SetString("dateReceived", after);
        draftResponseTarget  = SetString("draftResponseTarget", after);
        hmpoResponse  = SetString("hmpoResponse", after);
        homeSecretaryReply  = SetString("homeSecretaryReply", after);
        isGroupedMaster  = SetString("isGroupedMaster", after);
        isGroupedSlave  = SetString("isGroupedSlave", after);
        isLinkedCase  = SetString("isLinkedCase", after);
        markupDecision  = SetString("markupDecision", after);
        markupMinister  = SetString("markupMinister", after);
        markupTopic  = SetString("markupTopic", after);
        markupTeam= SetString("markupTopic", after);
        markupUnit  = SetString("markupUnit", after);
        member  = SetString("member", after);
        minutesCollated  = SetString("minutesCollated", after);
        modified  = SetString("modified", after);
        modifier  = SetString("modifier", after);
        mpRef  = SetString("mpRef", after);
        name  = SetString("name", after);
        nodedbid  = SetString("node-dbid", after);
        nodeuuid  = SetString("node-uuid", after);
        ogdName  = SetString("ogdName", after);
        originalDrafterTeam  = SetString("originalDrafterTeam", after);
        originalDrafterUnit  = SetString("originalDrafterUnit", after);
        originalDrafterUser  = SetString("originalDrafterUser", after);
        ownerUpdatedDatetime  = SetString("ownerUpdatedDatetime", after);
        parlyDispatch  = SetString("parlyDispatch", after);
        pqApiCreatedCase  = SetString("pqApiCreatedCase", after);
        priority  = SetString("priority", after);
        replyToAddressLine1 = SetString("replyToAddressLine1", after);
        replyToAddressLine2 = SetString("replyToAddressLine2", after);
        replyToAddressLine3 = SetString("replyToAddressLine3", after);
        replyToCountry = SetString("replyToCountry", after);
        replyToEmail = SetString("replyToEmail", after);
        replyToName = SetString("replyToName", after);
        replyToPostcode = SetString("replyToPostcode", after);
        replyToTelephone = SetString("replyToTelephone", after);
        replyToNumberTenCopy  = SetString("replyToNumberTenCopy", after);
        responderHubTarget  = SetString("responderHubTarget", after);
        returnCaseAt  = SetString("returnCaseAt", after);
        returnedCount = SetString("returnedCount", after);
        reviewedByPermSec  = SetString("reviewedByPermSec", after);
        reviewedBySpads  = SetString("reviewedBySpads", after);
        roundRobin  = SetString("roundRobin", after);
        secondaryCorrespondentReplyTo  = SetString("secondaryCorrespondentReplyTo", after);
        secondaryTopic  = SetString("secondaryTopic", after);
        signedByHomeSec  = SetString("signedByHomeSec", after);
        signedByLordsMinister  = SetString("signedByLordsMinister", after);
        statusUpdatedDatetime  = SetString("statusUpdatedDatetime", after);
        taskUpdatedDatetime  = SetString("taskUpdatedDatetime", after);
        thirdPartyCorrespondentAddressLine1 = SetString("thirdPartyCorrespondentAddressLine1", after);
        thirdPartyCorrespondentAddressLine2 = SetString("thirdPartyCorrespondentAddressLine2", after);
        thirdPartyCorrespondentAddressLine3 = SetString("thirdPartyCorrespondentAddressLine3", after);
        thirdPartyCorrespondentCountry = SetString("thirdPartyCorrespondentCountry", after);
        thirdPartyCorrespondentEmail = SetString("thirdPartyCorrespondentEmail", after);
        thirdPartyCorrespondentForename = SetString("thirdPartyCorrespondentForename", after);
        thirdPartyCorrespondentOrganisation = SetString("thirdPartyCorrespondentOrganisation", after);
        thirdPartyCorrespondentPostcode = SetString("thirdPartyCorrespondentPostcode", after);
        thirdPartyCorrespondentReplyTo = SetString("thirdPartyCorrespondentReplyTo", after);
        thirdPartyCorrespondentSurname = SetString("thirdPartyCorrespondentSurname", after);
        thirdPartyCorrespondentTelephone = SetString("thirdPartyCorrespondentTelephone", after);
        thirdPartyCorrespondentTitle = SetString("thirdPartyCorrespondentTitle", after);
        urnSuffix  = SetString("urnSuffix", after);
    }

    private String SetString(String key, Map<String,String> map) {
       String value = null;

       if(map != null) {
           value = map.getOrDefault(key, null);
       }

       if(value.equals("null") || value.equals("")) {
           value = null;
       }

       return value;
    }

}