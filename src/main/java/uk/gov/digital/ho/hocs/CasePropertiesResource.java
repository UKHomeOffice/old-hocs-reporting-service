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
import uk.gov.digital.ho.hocs.model.CaseProperties;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping(value = "/cases")
public class CasePropertiesResource {
    private final CasePropertiesService casePropertiesService;

    private CsvMapper csvMapper;

    @Autowired
    public CasePropertiesResource(CasePropertiesService casePropertiesService) {
        this.casePropertiesService = casePropertiesService;
        this.csvMapper = new CsvMapper();
        ReportingServiceConfiguration.initialiseObjectMapper(this.csvMapper);
    }

    @RequestMapping(value = "/{cutoff}/{unit}/json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CaseProperties[]> getCasePropertiesJson(@PathVariable("unit") String unit, @PathVariable("cutoff") String cutoff) {
        Set<CaseProperties> caseCurrentProperties = this.casePropertiesService.getProperties(unit, cutoff);
        return ResponseEntity.ok(caseCurrentProperties.toArray(new CaseProperties[]{}));
    }

    @RequestMapping(value = "/{cutoff}/{unit}/csv", method = RequestMethod.GET, produces = "text/csv;charset=UTF-8")
    public ResponseEntity<String> getCasePropertiesCSV(@PathVariable("unit") String unit, @PathVariable("cutoff") String cutoff) {
        Set<CaseProperties> caseCurrentProperties = this.casePropertiesService.getProperties(unit, cutoff);

        CsvSchema schema = csvMapper.schemaFor(CaseProperties.class).withHeader();
        String value;
        try {
            value = csvMapper.writer(schema).writeValueAsString(caseCurrentProperties);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"hocs_" + cutoff + ".csv\"")
                .body(value);
    }
}