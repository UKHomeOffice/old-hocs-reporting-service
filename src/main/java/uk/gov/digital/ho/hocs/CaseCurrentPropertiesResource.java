package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping(value = "/currentProperties/")
public class CaseCurrentPropertiesResource {
    private final CaseCurrentPropertiesService caseCurrentPropertiesService;

    @Autowired
    public CaseCurrentPropertiesResource(CaseCurrentPropertiesService caseCurrentPropertiesService) {
        this.caseCurrentPropertiesService = caseCurrentPropertiesService;
    }

    @RequestMapping(value = "/{unit}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CaseCurrentProperties[]> getCaseCurrentProperties(@PathVariable("unit") String unit) {
        Set<CaseCurrentProperties> caseCurrentProperties = this.caseCurrentPropertiesService.getCurrentProperties(unit);
        return ResponseEntity.ok(caseCurrentProperties.toArray(new CaseCurrentProperties[]{}));
    }
}