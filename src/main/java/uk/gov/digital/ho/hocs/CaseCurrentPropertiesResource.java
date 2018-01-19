package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @RequestMapping(value = "/{unit}/{start}/{end}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity postEvent(@PathVariable("unit") Set<String> units, @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") String start, @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") String end) {

        log.info("Fetching All Current Properties for \"{}\"", units);


        caseCurrentPropertiesService.getCurrentProperties(LocalDate.parse(start),LocalDate.parse(end),units);

        return ResponseEntity.ok("OK");
    }
}
