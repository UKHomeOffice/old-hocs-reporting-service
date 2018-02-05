package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;

import java.time.LocalDateTime;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping(value = "/cases/current")
public class CaseCurrentPropertiesResource {
    private final CaseCurrentPropertiesService caseCurrentPropertiesService;

    private CsvMapper csvMapper;

    @Autowired
    public CaseCurrentPropertiesResource(CaseCurrentPropertiesService caseCurrentPropertiesService) {
        this.caseCurrentPropertiesService = caseCurrentPropertiesService;
        this.csvMapper = new CsvMapper();
        ReportingServiceConfiguration.initialiseObjectMapper(this.csvMapper);
    }

    @RequestMapping(value = "/{unit}/json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CaseCurrentProperties[]> getCaseCurrentPropertiesJson(@PathVariable("unit") String unit) {
        Set<CaseCurrentProperties> caseCurrentProperties = this.caseCurrentPropertiesService.getCurrentProperties(unit);
        return ResponseEntity.ok(caseCurrentProperties.toArray(new CaseCurrentProperties[]{}));
    }

    @RequestMapping(value = "/{unit}/csv", method = RequestMethod.GET, produces = "text/csv;charset=UTF-8")
    public ResponseEntity<String> getCaseCurrentPropertiesCSV(@PathVariable("unit") String unit) {
        Set<CaseCurrentProperties> caseCurrentProperties = this.caseCurrentPropertiesService.getCurrentProperties(unit);

        CsvSchema schema = csvMapper.schemaFor(CaseCurrentProperties.class).withHeader();
        String value;
        try {
            value = csvMapper.writer(schema).writeValueAsString(caseCurrentProperties);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"Hocs_Reporting_Current_" + getFormattedTime() + ".csv\"")
                .body(value);
    }

    private String getFormattedTime() {
        return LocalDateTime.now().toString();
    }
}