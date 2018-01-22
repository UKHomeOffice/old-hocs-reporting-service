package uk.gov.digital.ho.hocs.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@Entity
@Table(name = "properties")
@Access(AccessType.FIELD)
@Getter
@EqualsAndHashCode(of = {"uuid", "timestamp"}, callSuper = false)
public class CaseProperties extends BaseProperties {

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
    private Boolean acpoConsultation;

    @Column(name = "advice")
    private String advice;

    @Column(name = "allocate_target")
    private LocalDateTime allocateTarget;

    @Column(name = "allocate_to_responder_target")
    private LocalDateTime allocateToResponderTarget;

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
    private LocalDateTime arrivingDateInUK;

    @Column(name = "assigned_team")
    private String assignedTeam;

    @Column(name = "assigned_unit")
    private String assignedUnit;

    @Column(name = "assigned_user")
    private String assignedUser;

    @Column(name = "auto_created_case")
    private Boolean autoCreatedCase;

    @Column(name = "bring_up_date")
    private LocalDateTime bringUpDate;

    @Column(name = "cabinet_office_consultation")
    private Boolean cabinetOfficeConsultation;

    @Column(name = "case_ref")
    private String caseRef;

    @Column(name = "case_response_deadline")
    private LocalDateTime caseResponseDeadline;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "case_task")
    private String caseTask;

    @Column(name = "channel")
    private String channel;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "complex")
    private Boolean complex;

    @Column(name = "consent_attached")
    private Boolean consentAttached;

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
    private LocalDateTime created;

    @Column(name = "creator")
    private String creator;

    @Column(name = "date_of_letter")
    private LocalDateTime dateOfLetter;

    @Column(name = "date_received")
    private LocalDateTime dateReceived;

    @Column(name = "defer_due_to")
    private String deferDueTo;

    @Column(name = "delivery_number")
    private String deliveryNumber;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column(name = "departure_date_from_uk")
    private LocalDateTime departureDateFromUK;

    @Column(name = "dispatched_date")
    private LocalDateTime dispatchedDate;

    @Column(name = "dispatch_target")
    private LocalDateTime dispatchTarget;

    @Column(name = "document_added")
    private String documentAdded;

    @Column(name = "draft_date")
    private LocalDateTime draftDate;

    @Column(name = "draft_response_target")
    private LocalDateTime draftResponseTarget;

    @Column(name = "enforcement_notice_deadline")
    private LocalDateTime enforcementNoticeDeadline;

    @Column(name = "enforcement_notice_needed")
    private Boolean enforcementNoticeNeeded;

    @Column(name = "examiner_security_check")
    private Boolean examinerSecurityCheck;

    @Column(name = "fee_included")
    private Boolean feeIncluded;

    @Column(name = "final_approval_target")
    private LocalDateTime finalApprovalTarget;

    @Column(name = "foi_disclosure")
    private Boolean foiDisclosure;

    @Column(name = "foi_is_eir")
    private Boolean foiIsEir;

    @Column(name = "foi_minister_sign_off")
    private Boolean foiMinisterSignOff;

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
    private Boolean hoJoined;

    @Column(name = "home_secretary_reply")
    private Boolean homeSecretaryReply;

    @Column(name = "ico_complaint_officer")
    private String icoComplaintOfficer;

    @Column(name = "ico_outcome")
    private String icoOutcome;

    @Column(name = "ico_outcome_date")
    private LocalDateTime icoOutcomeDate;

    @Column(name = "ico_reference")
    private String icoReference;

    @Column(name = "individual_household")
    private Boolean individualHousehold;

    @Column(name = "is_grouped_master")
    private Boolean isGroupedMaster;

    @Column(name = "is_grouped_slave")
    private Boolean isGroupedSlave;

    @Column(name = "is_linked_case")
    private Boolean isLinkedCase;

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
    private LocalDateTime modified;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "mp_ref")
    private String mpRef;

    @Column(name = "name")
    private String name;

    @Column(name = "new_information_released")
    private Boolean newInformationReleased;

    @Column(name = "node_dbid")
    private String nodedbid;

    @Column(name = "node_uuid")
    private String nodeuuid;

    @Column(name = "nslg_consultation")
    private Boolean nslgConsultation;

    @Column(name = "number_of_children")
    private Integer numberOfChildren;

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
    private LocalDateTime ownerUpdatedDatetime;

    @Column(name = "parly_dispatch")
    private Boolean parlyDispatch;

    @Column(name = "party_leader_deputy_last_name")
    private String partyLeaderDeputyLastName;

    @Column(name = "party_leader_deputy_other_names")
    private String partyLeaderDeputyOtherNames;

    @Column(name = "party_leader_deputy_passport_issued_at")
    private String partyLeaderDeputyPassportIssuedAt;

    @Column(name = "party_leader_deputy_passport_issued_on")
    private LocalDateTime partyLeaderDeputyPassportIssuedOn;

    @Column(name = "party_leader_deputy_passport_number")
    private String partyLeaderDeputyPassportNumber;

    @Column(name = "party_leader_last_name")
    private String partyLeaderLastName;

    @Column(name = "party_leader_other_names")
    private String partyLeaderOtherNames;

    @Column(name = "party_leader_passport_issued_at")
    private String partyLeaderPassportIssuedAt;

    @Column(name = "party_leader_passport_issued_on")
    private LocalDateTime partyLeaderPassportIssuedOn;

    @Column(name = "party_leader_passport_number")
    private String partyLeaderPassportNumber;

    @Column(name = "pit_extension")
    private Boolean pitExtension;

    @Column(name = "pit_letter_sent_date")
    private LocalDateTime pitLetterSentDate;

    @Column(name = "pit_qualified_exemptions")
    private String pitQualifiedExemptions;

    @Column(name = "po_target")
    private LocalDateTime poTarget;

    @Column(name = "pq_api_created_case")
    private Boolean pqApiCreatedCase;

    @Column(name = "priority")
    private Boolean priority;

    @Column(name = "reply_to_address_line1")
    private String replyToAddressLine1;

    @Column(name = "reply_to_address_line2")
    private String replyToAddressLine2;

    @Column(name = "reply_to_address_line3")
    private String replyToAddressLine3;

    @Column(name = "reply_to_applicant")
    private Boolean replyToApplicant;

    @Column(name = "reply_to_correspondent")
    private Boolean replyToCorrespondent;

    @Column(name = "reply_to_country")
    private String replyToCountry;

    @Column(name = "reply_to_email")
    private String replyToEmail;

    @Column(name = "reply_to_name")
    private String replyToName;

    @Column(name = "reply_to_number_ten_copy")
    private Boolean replyToNumberTenCopy;

    @Column(name = "reply_to_postcode")
    private String replyToPostcode;

    @Column(name = "reply_to_telephone")
    private String replyToTelephone;

    @Column(name = "responder_hub_target")
    private String responderHubTarget;

    @Column(name = "response_date")
    private LocalDateTime responseDate;

    @Column(name = "return_case_at")
    private Boolean returnCaseAt;

    @Column(name = "returned_count")
    private Integer returnedCount;

    @Column(name = "reviewed_by_perm_sec")
    private Boolean reviewedByPermSec;

    @Column(name = "reviewed_by_spads")
    private Boolean reviewedBySpads;

    @Column(name = "round_robin")
    private Boolean roundRobin;

    @Column(name = "round_robin_advice_consultation")
    private Boolean roundRobinAdviceConsultation;

    @Column(name = "royals_consultation")
    private Boolean royalsConsultation;

    @Column(name = "scs_approval_target")
    private LocalDateTime scsApprovalTarget;

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
    private Boolean secondaryCorrespondentReplyTo;

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
    private Boolean signedByHomeSec;

    @Column(name = "signed_by_lords_minister")
    private Boolean signedByLordsMinister;

    @Column(name = "status_updated_datetime")
    private LocalDateTime statusUpdatedDatetime;

    @Column(name = "task_updated_datetime")
    private LocalDateTime taskUpdatedDatetime;

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
    private Boolean thirdPartyCorrespondentReplyTo;

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
    private LocalDateTime tribunalOutcomeDate;

    @Column(name = "tsol_rep")
    private String tsolRep;

    @Column(name = "type_of_correspondent")
    private String typeOfCorrespondent;

    @Column(name = "type_of_representative")
    private String typeOfRepresentative;

    @Column(name = "urn_suffix")
    private String urnSuffix;

    public CaseProperties(Event event) {
        update(event);
    }

    public void update(Event event) {
        if (event.getUuid() != null) {
            this.uuid = event.getUuid();
        }
        if (event.getTimestamp() != null) {
            this.timestamp = event.getTimestamp();
        } else {
            this.timestamp = LocalDateTime.now();
        }
        if (event.getCaseReference() != null) {
            this.caseReference = event.getCaseReference();
        }

        Map<String, String> after = event.getData();

        acpoConsultation = SetBool("acpoConsultation", after);
        advice = SetString("advice", after);
        allocateTarget = SetDate("allocateTarget", after);
        allocateToResponderTarget = SetDate("allocateToResponderTarget", after);
        answeringMinister = SetString("answeringMinister", after);
        appellant = SetString("appellant", after);
        applicantAddressLine1 = SetString("applicantAddressLine1", after);
        applicantAddressLine2 = SetString("applicantAddressLine2", after);
        applicantAddressLine3 = SetString("applicantAddressLine3", after);
        applicantCountry = SetString("applicantCountry", after);
        applicantEmail = SetString("applicantEmail", after);
        applicantForename = SetString("applicantForename", after);
        applicantPostcode = SetString("applicantPostcode", after);
        applicantSurname = SetString("applicantSurname", after);
        applicantTelephone = SetString("applicantTelephone", after);
        applicantTitle = SetString("applicantTitle", after);
        arrivingDateInUK = SetDate("arrivingDateInUK", after);
        assignedTeam = SetString("assignedTeam", after);
        assignedUnit = SetString("assignedUnit", after);
        assignedUser = SetString("assignedUser", after);
        autoCreatedCase = SetBool("autoCreatedCase", after);
        bringUpDate = SetDate("bringUpDate", after);
        cabinetOfficeConsultation = SetBool("cabinetOfficeConsultation", after);
        caseRef = SetString("caseRef", after);
        caseResponseDeadline = SetDate("caseResponseDeadline", after);
        caseTask = SetString("caseTask", after);
        caseStatus = SetString("caseStatus", after);
        channel = SetString("channel", after);
        commentCount = SetInt("commentCount", after);
        complex = SetBool("complex", after);
        consentAttached = SetBool("consentAttached", after);
        correspondentAddressLine1 = SetString("correspondentAddressLine1", after);
        correspondentAddressLine2 = SetString("correspondentAddressLine2", after);
        correspondentAddressLine3 = SetString("correspondentAddressLine3", after);
        correspondentCountry = SetString("correspondentCountry", after);
        correspondentEmail = SetString("correspondentEmail", after);
        correspondentForename = SetString("correspondentForename", after);
        correspondentPostcode = SetString("correspondentPostcode", after);
        correspondentSurname = SetString("correspondentSurname", after);
        correspondentTelephone = SetString("correspondentTelephone", after);
        correspondentTitle = SetString("correspondentTitle", after);
        correspondenceType = SetString("correspondenceType", after);
        correspondingName = SetString("correspondingName", after);
        countriesToBeTravelledThrough = SetString("countriesToBeTravelledThrough", after);
        countryOfDestination = SetString("countryOfDestination", after);
        created = SetDate("created", after);
        creator = SetString("creator", after);
        dateOfLetter = SetDate("dateOfLetter", after);
        dateReceived = SetDate("dateReceived", after);
        deferDueTo = SetString("deferDueTo", after);
        deliveryNumber = SetString("deliveryNumber", after);
        deliveryType = SetString("deliveryType", after);
        departureDateFromUK = SetDate("departureDateFromUK", after);
        dispatchedDate = SetDate("dispatchedDate", after);
        dispatchTarget = SetDate("dispatchTarget", after);
        documentAdded = SetString("documentAdded", after);
        draftDate = SetDate("draftDate", after);
        draftResponseTarget = SetDate("draftResponseTarget", after);
        enforcementNoticeDeadline = SetDate("enforcementNoticeDeadline", after);
        enforcementNoticeNeeded = SetBool("enforcementNoticeNeeded", after);
        examinerSecurityCheck = SetBool("examinerSecurityCheck", after);
        feeIncluded = SetBool("feeIncluded", after);
        finalApprovalTarget = SetDate("finalApprovalTarget", after);
        foiDisclosure = SetBool("foiDisclosure", after);
        foiIsEir = SetBool("foiIsEir", after);
        foiMinisterSignOff = SetBool("foiMinisterSignOff", after);
        hardCopyReceived = SetString("hardCopyReceived", after);
        hmpoApplicationNumber = SetString("hmpoApplicationNumber", after);
        hmpoComplaintOutcome = SetString("hmpoComplaintOutcome", after);
        hmpoPassportNumber = SetString("hmpoPassportNumber", after);
        hmpoRefundAmount = SetString("hmpoRefundAmount", after);
        hmpoRefundDecision = SetString("hmpoRefundDecision", after);
        hmpoResponse = SetString("hmpoResponse", after);
        hoCaseOfficer = SetString("hoCaseOfficer", after);
        hoJoined = SetBool("hoJoined", after);
        homeSecretaryReply = SetBool("homeSecretaryReply", after);
        icoComplaintOfficer = SetString("icoComplaintOfficer", after);
        icoOutcome = SetString("icoOutcome", after);
        icoOutcomeDate = SetDate("icoOutcomeDate", after);
        icoReference = SetString("icoReference", after);
        individualHousehold = SetBool("individualHousehold", after);
        isGroupedMaster = SetBool("isGroupedMaster", after);
        isGroupedSlave = SetBool("isGroupedSlave", after);
        isLinkedCase = SetBool("isLinkedCase", after);
        leadersAddressAboard = SetString("leadersAddressAboard", after);
        markupDecision = SetString("markupDecision", after);
        markupMinister = SetString("markupMinister", after);
        markupTopic = SetString("markupTopic", after);
        markupTeam = SetString("markupTeam", after);
        markupUnit = SetString("markupUnit", after);
        member = SetString("member", after);
        minutesCollated = SetString("minutesCollated", after);
        modified = SetDate("modified", after);
        modifier = SetString("modifier", after);
        mpRef = SetString("mpRef", after);
        name = SetString("name", after);
        newInformationReleased = SetBool("newInformationReleased", after);
        nodedbid = SetString("node-dbid", after);
        nodeuuid = SetString("node-uuid", after);
        nslgConsultation = SetBool("nslgConsultation", after);
        numberOfChildren = SetInt("numberOfChildren", after);
        officeOfOrigin = SetString("officeOfOrigin", after);
        ogdName = SetString("ogdName", after);
        organisation = SetString("organisation", after);
        originalDrafterTeam = SetString("originalDrafterTeam", after);
        originalDrafterUnit = SetString("originalDrafterUnit", after);
        originalDrafterUser = SetString("originalDrafterUser", after);
        otherCountriesToBeVisited = SetString("otherCountriesToBeVisited", after);
        ownerUpdatedDatetime = SetDate("ownerUpdatedDatetime", after);
        parlyDispatch = SetBool("parlyDispatch", after);
        partyLeaderDeputyLastName = SetString("partyLeaderDeputyLastName", after);
        partyLeaderDeputyOtherNames = SetString("partyLeaderDeputyOtherNames", after);
        partyLeaderDeputyPassportIssuedAt = SetString("partyLeaderDeputyPassportIssuedAt", after);
        partyLeaderDeputyPassportIssuedOn = SetDate("partyLeaderDeputyPassportIssuedOn", after);
        partyLeaderDeputyPassportNumber = SetString("partyLeaderDeputyPassportNumber", after);
        partyLeaderLastName = SetString("partyLeaderLastName", after);
        partyLeaderOtherNames = SetString("partyLeaderOtherNames", after);
        partyLeaderPassportIssuedAt = SetString("partyLeaderPassportIssuedAt", after);
        partyLeaderPassportIssuedOn = SetDate("partyLeaderPassportIssuedOn", after);
        partyLeaderPassportNumber = SetString("partyLeaderPassportNumber", after);
        pitExtension = SetBool("pitExtension", after);
        pitLetterSentDate = SetDate("pitLetterSentDate", after);
        pitQualifiedExemptions = SetString("pitQualifiedExemptions", after);
        poTarget = SetDate("poTarget", after);
        pqApiCreatedCase = SetBool("pqApiCreatedCase", after);
        priority = SetBool("priority", after);
        replyToAddressLine1 = SetString("replyToAddressLine1", after);
        replyToAddressLine2 = SetString("replyToAddressLine2", after);
        replyToAddressLine3 = SetString("replyToAddressLine3", after);
        replyToApplicant = SetBool("replyToApplicant", after);
        replyToCorrespondent = SetBool("replyToCorrespondent", after);
        replyToCountry = SetString("replyToCountry", after);
        replyToEmail = SetString("replyToEmail", after);
        replyToName = SetString("replyToName", after);
        replyToPostcode = SetString("replyToPostcode", after);
        replyToTelephone = SetString("replyToTelephone", after);
        replyToNumberTenCopy = SetBool("replyToNumberTenCopy", after);
        responderHubTarget = SetString("responderHubTarget", after);
        responseDate = SetDate("responderHubTarget", after);
        returnCaseAt = SetBool("returnCaseAt", after);
        returnedCount = SetInt("returnedCount", after);
        reviewedByPermSec = SetBool("reviewedByPermSec", after);
        reviewedBySpads = SetBool("reviewedBySpads", after);
        roundRobin = SetBool("roundRobin", after);
        roundRobinAdviceConsultation = SetBool("roundRobinAdviceConsultation", after);
        royalsConsultation = SetBool("royalsConsultation", after);
        scsApprovalTarget = SetDate("scsApprovalTarget", after);
        secCorrespondentConsentAttached = SetString("secCorrespondentConsentAttached", after);
        secCorrespondentTypeOfRepresentative = SetString("secCorrespondentTypeOfRepresentative", after);
        secondaryCorrespondentAddressLine1 = SetString("secondaryCorrespondentAddressLine1", after);
        secondaryCorrespondentAddressLine2 = SetString("secondaryCorrespondentAddressLine2", after);
        secondaryCorrespondentAddressLine3 = SetString("secondaryCorrespondentAddressLine3", after);
        secondaryCorrespondentEmail = SetString("secondaryCorrespondentEmail", after);
        secondaryCorrespondentForename = SetString("secondaryCorrespondentForename", after);
        secondaryCorrespondentPostcode = SetString("secondaryCorrespondentPostcode", after);
        secondaryCorrespondentReplyTo = SetBool("secondaryCorrespondentReplyTo", after);
        secondaryCorrespondentSurname = SetString("secondaryCorrespondentSurname", after);
        secondaryCorrespondentTelephone = SetString("secondaryCorrespondentTelephone", after);
        secondaryCorrespondentTitle = SetString("secondaryCorrespondentTitle", after);
        secondaryTopic = SetString("secondaryTopic", after);
        secondaryTypeOfCorrespondent = SetString("secondaryTypeOfCorrespondent", after);
        signedByHomeSec = SetBool("signedByHomeSec", after);
        signedByLordsMinister = SetBool("signedByLordsMinister", after);
        statusUpdatedDatetime = SetDate("statusUpdatedDatetime", after);
        taskUpdatedDatetime = SetDate("taskUpdatedDatetime", after);
        thirdPartyConsentAttached = SetString("thirdPartyConsentAttached", after);
        thirdPartyCorrespondentAddressLine1 = SetString("thirdPartyCorrespondentAddressLine1", after);
        thirdPartyCorrespondentAddressLine2 = SetString("thirdPartyCorrespondentAddressLine2", after);
        thirdPartyCorrespondentAddressLine3 = SetString("thirdPartyCorrespondentAddressLine3", after);
        thirdPartyCorrespondentCountry = SetString("thirdPartyCorrespondentCountry", after);
        thirdPartyCorrespondentEmail = SetString("thirdPartyCorrespondentEmail", after);
        thirdPartyCorrespondentForename = SetString("thirdPartyCorrespondentForename", after);
        thirdPartyCorrespondentOrganisation = SetString("thirdPartyCorrespondentOrganisation", after);
        thirdPartyCorrespondentPostcode = SetString("thirdPartyCorrespondentPostcode", after);
        thirdPartyCorrespondentReplyTo = SetBool("thirdPartyCorrespondentReplyTo", after);
        thirdPartyCorrespondentSurname = SetString("thirdPartyCorrespondentSurname", after);
        thirdPartyCorrespondentTelephone = SetString("thirdPartyCorrespondentTelephone", after);
        thirdPartyCorrespondentTitle = SetString("thirdPartyCorrespondentTitle", after);
        thirdPartyTypeOfCorrespondent = SetString("thirdPartyTypeOfCorrespondent", after);
        thirdPartyTypeOfRepresentative = SetString("thirdPartyTypeOfRepresentative", after);
        tribunalOutcome = SetString("tribunalOutcome", after);
        tribunalOutcomeDate = SetDate("tribunalOutcomeDate", after);
        tsolRep = SetString("tsolRep", after);
        typeOfCorrespondent = SetString("typeOfCorrespondent", after);
        typeOfRepresentative = SetString("typeOfRepresentative", after);
        urnSuffix = SetString("urnSuffix", after);
    }

}