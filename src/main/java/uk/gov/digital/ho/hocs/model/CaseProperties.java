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

    @Column(name = "acpo_consultation")
    private String acpoConsultation;

    @Column(name = "advice")
    private String advice;

    @Column(name = "allocate_header")
    private String allocateHeader;

    @Column(name = "allocate_target")
    private String allocateTarget;

    @Column(name = "allocate_to_responder_target")
    private String allocateToResponderTarget;

    @Column(name = "answering_minister")
    private String answeringMinister;

    @Column(name = "appellant")
    private String appellant;

    @Column(name = "applicant_address_line1")
    private String applicantAddressLine1;

    @Column(name = "applicant_address_line2")
    private String applicantAddressLine2;

    @Column(name = "applicant_address_line3")
    private String applicantAddressLine3;

    @Column(name = "applicant_country")
    private String applicantCountry;

    @Column(name = "applicant_email")
    private String applicantEmail;

    @Column(name = "applicant_forename")
    private String applicantForename;

    @Column(name = "applicant_postcode")
    private String applicantPostcode;

    @Column(name = "applicant_surname")
    private String applicantSurname;

    @Column(name = "applicant_telephone")
    private String applicantTelephone;

    @Column(name = "applicant_title")
    private String applicantTitle;

    @Column(name = "arriving_date_in_uk")
    private String arrivingDateInUK;

    @Column(name = "assigned_team")
    private String assignedTeam;

    @Column(name = "assigned_unit")
    private String assignedUnit;

    @Column(name = "assigned_user")
    private String assignedUser;

    @Column(name = "auto_created_case")
    private String autoCreatedCase;

    @Column(name = "bring_up_date")
    private String bringUpDate;

    @Column(name = "cabinet_office_consultation")
    private String cabinetOfficeConsultation;

    @Column(name = "case_ref")
    private String caseRef;

    @Column(name = "case_response_deadline")
    private String caseResponseDeadline;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "case_task")
    private String caseTask;

    @Column(name = "case_workflow_status")
    private String caseWorkflowStatus;

    @Column(name = "channel")
    private String channel;

    @Column(name = "comment_count")
    private String commentCount;

    @Column(name = "complex")
    private String complex;

    @Column(name = "consent_attached")
    private String consentAttached;

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

    @Column(name = "corresponding_name")
    private String correspondingName;

    @Column(name = "countries_to_be_travelled_through")
    private String countriesToBeTravelledThrough;

    @Column(name = "country_of_destination")
    private String countryOfDestination;

    @Column(name = "created")
    private String created;

    @Column(name = "creator")
    private String creator;

    @Column(name = "date_of_letter")
    private String dateOfLetter;

    @Column(name = "date_received")
    private String dateReceived;

    @Column(name = "defer_due_to")
    private String deferDueTo;

    @Column(name = "delivery_number")
    private String deliveryNumber;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column(name = "departure_date_from_uk")
    private String departureDateFromUK;

    @Column(name = "dispatched_date")
    private String dispatchedDate;

    @Column(name = "dispatch_target")
    private String dispatchTarget;

    @Column(name = "document_added")
    private String documentAdded;

    @Column(name = "draft_date")
    private String draftDate;

    @Column(name = "draft_response_target")
    private String draftResponseTarget;

    @Column(name = "enforcement_notice_deadline")
    private String enforcementNoticeDeadline;

    @Column(name = "enforcement_notice_needed")
    private String enforcementNoticeNeeded;

    @Column(name = "examiner_security_check")
    private String examinerSecurityCheck;

    @Column(name = "fee_included")
    private String feeIncluded;

    @Column(name = "final_approval_target")
    private String finalApprovalTarget;

    @Column(name = "foi_disclosure")
    private String foiDisclosure;

    @Column(name = "foi_is_eir")
    private String foiIsEir;

    @Column(name = "foi_minister_sign_off")
    private String foiMinisterSignOff;

    @Column(name = "hard_copy_received")
    private String hardCopyReceived;

    @Column(name = "hmpo_application_number")
    private String hmpoApplicationNumber;

    @Column(name = "hmpo_complaint_outcome")
    private String hmpoComplaintOutcome;

    @Column(name = "hmpo_passport_number")
    private String hmpoPassportNumber;

    @Column(name = "hmpo_refund_amount")
    private String hmpoRefundAmount;

    @Column(name = "hmpo_refund_decision")
    private String hmpoRefundDecision;

    @Column(name = "hmpo_response")
    private String hmpoResponse;

    @Column(name = "ho_case_officer")
    private String hoCaseOfficer;

    @Column(name = "ho_joined")
    private String hoJoined;

    @Column(name = "home_secretary_reply")
    private String homeSecretaryReply;

    @Column(name = "ico_complaint_officer")
    private String icoComplaintOfficer;

    @Column(name = "ico_outcome")
    private String icoOutcome;

    @Column(name = "ico_outcome_date")
    private String icoOutcomeDate;

    @Column(name = "ico_reference")
    private String icoReference;

    @Column(name = "individual_household")
    private String individualHousehold;

    @Column(name = "is_grouped_master")
    private String isGroupedMaster;

    @Column(name = "is_grouped_slave")
    private String isGroupedSlave;

    @Column(name = "is_linked_case")
    private String isLinkedCase;

    @Column(name = "leaders_address_aboard")
    private String leadersAddressAboard;

    @Column(name = "markup_decision")
    private String markupDecision;

    @Column(name = "markup_minister")
    private String markupMinister;

    @Column(name = "markup_team")
    private String markupTeam;

    @Column(name = "markup_topic")
    private String markupTopic;

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

    @Column(name = "new_information_released")
    private String newInformationReleased;

    @Column(name = "node_dbid")
    private String nodedbid;

    @Column(name = "node_uuid")
    private String nodeuuid;

    @Column(name = "nslg_consultation")
    private String nslgConsultation;

    @Column(name = "number_of_children")
    private String numberOfChildren;

    @Column(name = "office_of_origin")
    private String officeOfOrigin;

    @Column(name = "ogd_name")
    private String ogdName;

    @Column(name = "organisation")
    private String organisation;

    @Column(name = "original_drafter_team")
    private String originalDrafterTeam;

    @Column(name = "original_drafter_unit")
    private String originalDrafterUnit;

    @Column(name = "original_drafter_user")
    private String originalDrafterUser;

    @Column(name = "other_countries_to_be_visited")
    private String otherCountriesToBeVisited;

    @Column(name = "owner_updated_datetime")
    private String ownerUpdatedDatetime;

    @Column(name = "parly_dispatch")
    private String parlyDispatch;

    @Column(name = "party_leader_deputy_last_name")
    private String partyLeaderDeputyLastName;

    @Column(name = "party_leader_deputy_other_names")
    private String partyLeaderDeputyOtherNames;

    @Column(name = "party_leader_deputy_passport_issued_at")
    private String partyLeaderDeputyPassportIssuedAt;

    @Column(name = "party_leader_deputy_passport_issued_on")
    private String partyLeaderDeputyPassportIssuedOn;

    @Column(name = "party_leader_deputy_passport_number")
    private String partyLeaderDeputyPassportNumber;

    @Column(name = "party_leader_last_name")
    private String partyLeaderLastName;

    @Column(name = "party_leader_other_names")
    private String partyLeaderOtherNames;

    @Column(name = "party_leader_passport_issued_at")
    private String partyLeaderPassportIssuedAt;

    @Column(name = "party_leader_passport_issued_on")
    private String partyLeaderPassportIssuedOn;

    @Column(name = "party_leader_passport_number")
    private String partyLeaderPassportNumber;

    @Column(name = "pit_extension")
    private String pitExtension;

    @Column(name = "pit_letter_sent_date")
    private String pitLetterSentDate;

    @Column(name = "pit_qualified_exemptions")
    private String pitQualifiedExemptions;

    @Column(name = "po_target")
    private String poTarget;

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

    @Column(name = "reply_to_applicant")
    private String replyToApplicant;

    @Column(name = "reply_to_correspondent")
    private String replyToCorrespondent;

    @Column(name = "reply_to_country")
    private String replyToCountry;

    @Column(name = "reply_to_email")
    private String replyToEmail;

    @Column(name = "reply_to_name")
    private String replyToName;

    @Column(name = "reply_to_number_ten_copy")
    private String replyToNumberTenCopy;

    @Column(name = "reply_to_postcode")
    private String replyToPostcode;

    @Column(name = "reply_to_telephone")
    private String replyToTelephone;

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

    @Column(name = "round_robin_advice_consultation")
    private String roundRobinAdviceConsultation;

    @Column(name = "royals_consultation")
    private String royalsConsultation;

    @Column(name = "scs_approval_target")
    private String scsApprovalTarget;

    @Column(name = "sec_correspondent_consent_attached")
    private String secCorrespondentConsentAttached;

    @Column(name = "sec_correspondent_type_of_representative")
    private String secCorrespondentTypeOfRepresentative;

    @Column(name = "secondary_correspondent_address_line1")
    private String secondaryCorrespondentAddressLine1;

    @Column(name = "secondary_correspondent_address_line2")
    private String secondaryCorrespondentAddressLine2;

    @Column(name = "secondary_correspondent_address_line3")
    private String secondaryCorrespondentAddressLine3;

    @Column(name = "secondary_correspondent_email")
    private String secondaryCorrespondentEmail;

    @Column(name = "secondary_correspondent_forename")
    private String secondaryCorrespondentForename;

    @Column(name = "secondary_correspondent_postcode")
    private String secondaryCorrespondentPostcode;

    @Column(name = "secondary_correspondent_reply_to")
    private String secondaryCorrespondentReplyTo;

    @Column(name = "secondary_correspondent_surname")
    private String secondaryCorrespondentSurname;

    @Column(name = "secondary_correspondent_telephone")
    private String secondaryCorrespondentTelephone;

    @Column(name = "secondary_correspondent_title")
    private String secondaryCorrespondentTitle;

    @Column(name = "secondary_topic")
    private String secondaryTopic;

    @Column(name = "secondary_type_of_correspondent")
    private String secondaryTypeOfCorrespondent;

    @Column(name = "signed_by_home_sec")
    private String signedByHomeSec;

    @Column(name = "signed_by_lords_minister")
    private String signedByLordsMinister;

    @Column(name = "status_updated_datetime")
    private String statusUpdatedDatetime;

    @Column(name = "task_updated_datetime")
    private String taskUpdatedDatetime;

    @Column(name = "third_party_consent_attached")
    private String thirdPartyConsentAttached;

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

    @Column(name = "third_party_type_of_correspondent")
    private String thirdPartyTypeOfCorrespondent;

    @Column(name = "third_party_type_of_representative")
    private String thirdPartyTypeOfRepresentative;

    @Column(name = "tribunal_outcome")
    private String tribunalOutcome;

    @Column(name = "tribunal_outcome_date")
    private String tribunalOutcomeDate;

    @Column(name = "tsol_rep")
    private String tsolRep;

    @Column(name = "type_of_correspondent")
    private String typeOfCorrespondent;

    @Column(name = "type_of_representative")
    private String typeOfRepresentative;

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

        Map<String,String> after = event.getData();

        acpoConsultation  = SetString("acpoConsultation", after);
        advice  = SetString("advice", after);
        allocateHeader  = SetString("allocateHeader", after);
        allocateTarget  = SetString("allocateTarget", after);
        allocateToResponderTarget  = SetString("allocateToResponderTarget", after);
        answeringMinister  = SetString("answeringMinister", after);
        appellant  = SetString("appellant", after);
        applicantAddressLine1  = SetString("applicantAddressLine1", after);
        applicantAddressLine2  = SetString("applicantAddressLine2", after);
        applicantAddressLine3  = SetString("applicantAddressLine3", after);
        applicantCountry  = SetString("applicantCountry", after);
        applicantEmail  = SetString("applicantEmail", after);
        applicantForename  = SetString("applicantForename", after);
        applicantPostcode  = SetString("applicantPostcode", after);
        applicantSurname  = SetString("applicantSurname", after);
        applicantTelephone  = SetString("applicantTelephone", after);
        applicantTitle  = SetString("applicantTitle", after);
        arrivingDateInUK  = SetString("arrivingDateInUK", after);
        assignedTeam = SetString("assignedTeam", after);
        assignedUnit = SetString("assignedUnit", after);
        assignedUser = SetString("assignedUser", after);
        autoCreatedCase  = SetString("autoCreatedCase", after);
        bringUpDate  = SetString("bringUpDate", after);
        cabinetOfficeConsultation  = SetString("cabinetOfficeConsultation", after);
        caseRef  = SetString("caseRef", after);
        caseResponseDeadline  = SetString("caseResponseDeadline", after);
        caseTask = SetString("caseTask", after);
        caseWorkflowStatus = SetString("caseWorkflowStatus", after);
        caseStatus = SetString("caseStatus", after);
        channel  = SetString("channel", after);
        commentCount  = SetString("commentCount", after);
        complex  = SetString("complex", after);
        consentAttached  = SetString("consentAttached", after);
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
        correspondingName  = SetString("correspondingName", after);
        countriesToBeTravelledThrough  = SetString("countriesToBeTravelledThrough", after);
        countryOfDestination  = SetString("countryOfDestination", after);
        created  = SetString("created", after);
        creator  = SetString("creator", after);
        dateOfLetter  = SetString("dateOfLetter", after);
        dateReceived  = SetString("dateReceived", after);
        deferDueTo  = SetString("deferDueTo", after);
        deliveryNumber  = SetString("deliveryNumber", after);
        deliveryType  = SetString("deliveryType", after);
        departureDateFromUK  = SetString("departureDateFromUK", after);
        dispatchedDate  = SetString("dispatchedDate", after);
        dispatchTarget  = SetString("dispatchTarget", after);
        documentAdded  = SetString("documentAdded", after);
        draftDate  = SetString("draftDate", after);
        draftResponseTarget  = SetString("draftResponseTarget", after);
        enforcementNoticeDeadline  = SetString("enforcementNoticeDeadline", after);
        enforcementNoticeNeeded  = SetString("enforcementNoticeNeeded", after);
        examinerSecurityCheck  = SetString("examinerSecurityCheck", after);
        feeIncluded  = SetString("feeIncluded", after);
        finalApprovalTarget  = SetString("finalApprovalTarget", after);
        foiDisclosure  = SetString("foiDisclosure", after);
        foiIsEir  = SetString("foiIsEir", after);
        foiMinisterSignOff  = SetString("foiMinisterSignOff", after);
        hardCopyReceived  = SetString("hardCopyReceived", after);
        hmpoApplicationNumber  = SetString("hmpoApplicationNumber", after);
        hmpoComplaintOutcome  = SetString("hmpoComplaintOutcome", after);
        hmpoPassportNumber  = SetString("hmpoPassportNumber", after);
        hmpoRefundAmount  = SetString("hmpoRefundAmount", after);
        hmpoRefundDecision  = SetString("hmpoRefundDecision", after);
        hmpoResponse  = SetString("hmpoResponse", after);
        hoCaseOfficer  = SetString("hoCaseOfficer", after);
        hoJoined  = SetString("hoJoined", after);
        homeSecretaryReply  = SetString("homeSecretaryReply", after);
        icoComplaintOfficer  = SetString("icoComplaintOfficer", after);
        icoOutcome  = SetString("icoOutcome", after);
        icoOutcomeDate  = SetString("icoOutcomeDate", after);
        icoReference  = SetString("icoReference", after);
        individualHousehold  = SetString("individualHousehold", after);
        isGroupedMaster  = SetString("isGroupedMaster", after);
        isGroupedSlave  = SetString("isGroupedSlave", after);
        isLinkedCase  = SetString("isLinkedCase", after);
        leadersAddressAboard  = SetString("leadersAddressAboard", after);
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
        newInformationReleased  = SetString("newInformationReleased", after);
        nodedbid  = SetString("node-dbid", after);
        nodeuuid  = SetString("node-uuid", after);
        nslgConsultation  = SetString("nslgConsultation", after);
        numberOfChildren  = SetString("numberOfChildren", after);
        officeOfOrigin  = SetString("officeOfOrigin", after);
        ogdName  = SetString("ogdName", after);
        organisation  = SetString("organisation", after);
        originalDrafterTeam  = SetString("originalDrafterTeam", after);
        originalDrafterUnit  = SetString("originalDrafterUnit", after);
        originalDrafterUser  = SetString("originalDrafterUser", after);
        otherCountriesToBeVisited  = SetString("otherCountriesToBeVisited", after);
        ownerUpdatedDatetime  = SetString("ownerUpdatedDatetime", after);
        parlyDispatch  = SetString("parlyDispatch", after);
        partyLeaderDeputyLastName  = SetString("partyLeaderDeputyLastName", after);
        partyLeaderDeputyOtherNames  = SetString("partyLeaderDeputyOtherNames", after);
        partyLeaderDeputyPassportIssuedAt  = SetString("partyLeaderDeputyPassportIssuedAt", after);
        partyLeaderDeputyPassportIssuedOn  = SetString("partyLeaderDeputyPassportIssuedOn", after);
        partyLeaderDeputyPassportNumber  = SetString("partyLeaderDeputyPassportNumber", after);
        partyLeaderLastName  = SetString("partyLeaderLastName", after);
        partyLeaderOtherNames  = SetString("partyLeaderOtherNames", after);
        partyLeaderPassportIssuedAt  = SetString("partyLeaderPassportIssuedAt", after);
        partyLeaderPassportIssuedOn  = SetString("partyLeaderPassportIssuedOn", after);
        partyLeaderPassportNumber  = SetString("partyLeaderPassportNumber", after);
        pitExtension  = SetString("pitExtension", after);
        pitLetterSentDate  = SetString("pitLetterSentDate", after);
        pitQualifiedExemptions  = SetString("pitQualifiedExemptions", after);
        poTarget  = SetString("poTarget", after);
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
        roundRobinAdviceConsultation  = SetString("roundRobinAdviceConsultation", after);
        royalsConsultation  = SetString("royalsConsultation", after);
        scsApprovalTarget  = SetString("scsApprovalTarget", after);
        secCorrespondentConsentAttached  = SetString("secCorrespondentConsentAttached", after);
        secCorrespondentTypeOfRepresentative  = SetString("secCorrespondentTypeOfRepresentative", after);
        secondaryCorrespondentAddressLine1  = SetString("secondaryCorrespondentAddressLine1", after);
        secondaryCorrespondentAddressLine2  = SetString("secondaryCorrespondentAddressLine2", after);
        secondaryCorrespondentAddressLine3  = SetString("secondaryCorrespondentAddressLine3", after);
        secondaryCorrespondentEmail  = SetString("secondaryCorrespondentEmail", after);
        secondaryCorrespondentForename  = SetString("secondaryCorrespondentForename", after);
        secondaryCorrespondentPostcode  = SetString("secondaryCorrespondentPostcode", after);
        secondaryCorrespondentReplyTo  = SetString("secondaryCorrespondentReplyTo", after);
        secondaryCorrespondentSurname  = SetString("secondaryCorrespondentSurname", after);
        secondaryCorrespondentTelephone  = SetString("secondaryCorrespondentTelephone", after);
        secondaryCorrespondentTitle  = SetString("secondaryCorrespondentTitle", after);
        secondaryTopic  = SetString("secondaryTopic", after);
        secondaryTypeOfCorrespondent  = SetString("secondaryTypeOfCorrespondent", after);
        signedByHomeSec  = SetString("signedByHomeSec", after);
        signedByLordsMinister  = SetString("signedByLordsMinister", after);
        statusUpdatedDatetime  = SetString("statusUpdatedDatetime", after);
        taskUpdatedDatetime  = SetString("taskUpdatedDatetime", after);
        thirdPartyConsentAttached  = SetString("thirdPartyConsentAttached", after);
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
        thirdPartyTypeOfCorrespondent = SetString("thirdPartyTypeOfCorrespondent", after);
        thirdPartyTypeOfRepresentative = SetString("thirdPartyTypeOfRepresentative", after);
        tribunalOutcome = SetString("tribunalOutcome", after);
        tribunalOutcomeDate = SetString("tribunalOutcomeDate", after);
        tsolRep = SetString("tsolRep", after);
        typeOfCorrespondent = SetString("typeOfCorrespondent", after);
        typeOfRepresentative = SetString("typeOfRepresentative", after);
        urnSuffix  = SetString("urnSuffix", after);
    }

    private String SetString(String key, Map<String,String> map) {
       String value = null;

       if(map != null) {
           value = map.getOrDefault(key, null);
       }

       if(value == "null" || value == "") {
           value = null;
       }

       return value;
    }

}