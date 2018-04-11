package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;
import uk.gov.digital.ho.hocs.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CaseCurrentPropertiesService {

    private static HashMap<String,String[]> caseTypesMapping;
    public static final String CORRESPONDENCE_TYPE = "correspondenceType";
    public static final String CASE_TASK = "caseTask";
    public static final String CREATE_CASE = "Create case";
    public static final String DRAFT_RESPONSE = "Draft response";
    public static final String QA_REVIEW = "QA review";
    public static final String CQT_APPROVAL = "CQT approval";
    public static final String PRIVATE_OFFICE_APPROVAL = "Private Office approval";
    public static final String SCS_APPROVAL = "SCS approval";
    public static final String PRESS_OFFICE_REVIEW = "Press Office review";
    public static final String SP_ADS_APPROVAL = "SpAds approval";
    public static final String FOI_MINISTER_SIGN_OFF = "FOI Minister sign-off";
    public static final String DISPATCH_RESPONSE = "Dispatch response";
    public static final String NONE = "None";

    // This code should be in the data-service but we also want to get rid of getCurrentProperties code.
    static {
        caseTypesMapping = new HashMap<>();
        caseTypesMapping.put("DCU", new String[]{"MIN","TRO","DTEN"});
        caseTypesMapping.put("UKVI", new String[]{"IMCB","IMCM","UTEN"});
        caseTypesMapping.put("FOI", new String[]{"FOI", "FTC", "FTCI", "FSC", "FSCI"});
        caseTypesMapping.put("HMPOCOR", new String[]{"COM","COM1","COM2","DGEN","GNR"});
        caseTypesMapping.put("HMPOCOL", new String[]{"COL"});
    }

    private final CaseCurrentPropertiesRepository currentPropertiesRepository;

    public CaseCurrentPropertiesService(CaseCurrentPropertiesRepository caseCurrentPropertiesRepository) {
        this.currentPropertiesRepository = caseCurrentPropertiesRepository;
    }

    void createCurrentProperties(Event event) throws EntityCreationException {
        try {
            CaseCurrentProperties caseCurrentProperties = currentPropertiesRepository.findByCaseReference(event.getCaseReference());

            if(caseCurrentProperties == null) {
                currentPropertiesRepository.save(new CaseCurrentProperties(event));
            } else if(event.getTimestamp().isAfter(caseCurrentProperties.getTimestamp())) {
                log.info("Entry already found, updating: " + event.getCaseReference());
                caseCurrentProperties.update(event);
                currentPropertiesRepository.save(caseCurrentProperties);
            }
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    (((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("current_properties_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            }
            else {
                throw e;
            }
        }
    }

    Set<CaseCurrentProperties> getCurrentProperties(String unit) {

        String[] correspondenceTypes = caseTypesMapping.get(unit);

        if (correspondenceTypes == null) {
            log.error("Unit {} not found", unit);
            return new HashSet<>();
        } else {

            log.info("Fetching All Current Properties for \"{}\"", Arrays.toString(correspondenceTypes));

            LocalDate now = LocalDate.now();
            LocalDateTime today = LocalDateTime.of(now, LocalTime.MAX);

            int monthsBack =4;
            // Start at the first day of the month
            LocalDateTime start = LocalDateTime.of(now.minusMonths(monthsBack).getYear(),
                                                   now.minusMonths(monthsBack).getMonth(),
                                                   1,0,0);

            return currentPropertiesRepository.getAllByTimestampBetweenAndCorrespondenceTypeIn(start, today, correspondenceTypes);
        }
    }

    public void createTaskEntryDetails(Event event) throws EntityCreationException {
        String caseType[] = {"FOI", "FTC", "FTCI", "FSC", "FSCI", "IMCB", "IMCM", "UTEN"};
        try {
                if (Arrays.asList(caseType).contains(event.getData().get(CORRESPONDENCE_TYPE).toString())) {
                    CaseCurrentProperties caseCurrentProperties = currentPropertiesRepository.findByCaseReference(event.getCaseReference());
                    log.info("task entry Details = " + caseCurrentProperties);

                    checkCaseTaskTimeStamp(event, caseCurrentProperties);
                }

        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    (((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("task_entry_details_id_idempotent"))) {
                // Do Nothing.
                log.info("Received duplicate message {}, {}", event.getUuid(), event.getTimestamp());
            } else {
                throw e;
            }
        }
    }

    private void checkCaseTaskTimeStamp(Event event, CaseCurrentProperties caseCurrentProperties) {
        log.info(CASE_TASK + " = " + event.getData().get(CASE_TASK));
        switch (event.getData().get(CASE_TASK)) {
            case CREATE_CASE:
                log.info(CREATE_CASE);
                if (caseCurrentProperties.getCreateCase() == null) {
                    caseCurrentProperties.setCreateCase(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case DRAFT_RESPONSE:
                log.info(DRAFT_RESPONSE);
                if (caseCurrentProperties.getDraftResponse() == null) {
                    caseCurrentProperties.setDraftResponse(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case QA_REVIEW:
                log.info(QA_REVIEW);
                if (caseCurrentProperties.getQAReview() == null) {
                    caseCurrentProperties.setQAReview(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case CQT_APPROVAL:
                log.info(CQT_APPROVAL);
                if (caseCurrentProperties.getUkviCQTApproval() == null) {
                    caseCurrentProperties.setUkviCQTApproval(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case PRIVATE_OFFICE_APPROVAL:
                log.info(PRIVATE_OFFICE_APPROVAL);
                if (caseCurrentProperties.getUkviPrivateOfficeApproval() == null) {
                    caseCurrentProperties.setUkviPrivateOfficeApproval(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case SCS_APPROVAL:
                log.info(SCS_APPROVAL);
                if (caseCurrentProperties.getFoiScsApproval() == null) {
                    caseCurrentProperties.setFoiScsApproval(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case PRESS_OFFICE_REVIEW:
                log.info(PRESS_OFFICE_REVIEW);
                if (caseCurrentProperties.getFoiPressOfficeReview() == null) {
                    caseCurrentProperties.setFoiPressOfficeReview(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case SP_ADS_APPROVAL:
                log.info(SP_ADS_APPROVAL);
                if (caseCurrentProperties.getFoiSpadsApproval() == null) {
                    caseCurrentProperties.setFoiSpadsApproval(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case FOI_MINISTER_SIGN_OFF:
                log.info(FOI_MINISTER_SIGN_OFF);
                if (caseCurrentProperties.getFoiFoiMinisterSignoff() == null) {
                    caseCurrentProperties.setFoiFoiMinisterSignoff(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case DISPATCH_RESPONSE:
                log.info(DISPATCH_RESPONSE);
                if (caseCurrentProperties.getDispatchResponse() == null) {
                    caseCurrentProperties.setDispatchResponse(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            case NONE:
                log.info(NONE);
                if (caseCurrentProperties.getCompleted() == null) {
                    caseCurrentProperties.setCompleted(event.getTimestamp());
                    currentPropertiesRepository.save(caseCurrentProperties);
                }
                break;
            default:
                log.error("no task found");
        }
    }
}