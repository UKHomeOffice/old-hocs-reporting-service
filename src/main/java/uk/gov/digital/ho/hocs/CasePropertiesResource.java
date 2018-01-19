package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/properties/")
public class CasePropertiesResource {
    private final CasePropertiesService casePropertiesService;

    @Autowired
    public CasePropertiesResource(CasePropertiesService casePropertiesService) {
        this.casePropertiesService = casePropertiesService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity postEvent() {

        log.info("Fetching All Properties");

        casePropertiesService.getAllProperties();

        return ResponseEntity.ok("OK");
    }
}
