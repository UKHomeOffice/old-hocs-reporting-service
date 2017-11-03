package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.legacy.units.UnitCreateRecord;
import uk.gov.digital.ho.hocs.dto.legacy.units.UnitRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

@RestController
@Slf4j
public class BusinessGroupResource {
    private final BusinessGroupService businessGroupService;

    @Autowired
    public BusinessGroupResource(BusinessGroupService businessGroupService) {
        this.businessGroupService = businessGroupService;
    }

    @RequestMapping(value = "/unit", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody BusinessGroup businessGroup) {
        log.info("Creating Group \"{}\"", businessGroup.getReferenceName());
        try {
            businessGroupService.createGroup(businessGroup);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("Group \"{}\" not created", businessGroup.getReferenceName());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/units", method = RequestMethod.POST)
    public ResponseEntity createUnitsAndGroups(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            log.info("Parsing Group File");
            try {
                businessGroupService.createGroupsFromCSV(file);
                return ResponseEntity.ok().build();
            } catch (EntityCreationException e) {
                log.info("Groups not created");
                log.info(e.getMessage());
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = {"/units", "/s/homeoffice/cts/allTeams"}, method = RequestMethod.GET)
    public ResponseEntity<UnitRecord> getLegacyUnits(){
        log.info("All Groups requested");
        try {
            UnitRecord groups = businessGroupService.getAllGroups();
            return ResponseEntity.ok(groups);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy All Groups\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //This is a create script, to be used once per new environment, maybe in the future this could just POST to alfresco directly.
    @RequestMapping(value = "/legacy/units/export", method = RequestMethod.GET)
    public ResponseEntity<UnitCreateRecord> getLegacyUnitsByReference() {
        log.info("List \"Legacy Create Units Script\" requested");
        try {
            UnitCreateRecord units = businessGroupService.getLegacyUnitCreateList();
            return ResponseEntity.ok(units);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy Create Units Script\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}