package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.DataListRecord;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicListEntityRecord;
import uk.gov.digital.ho.hocs.dto.legacy.units.UnitCreateRecord;
import uk.gov.digital.ho.hocs.dto.legacy.units.UnitRecord;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserCreateEntityRecord;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserCreateRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ListResource {
    private final ListService service;
    private final LegacyService legacyService;

    @Autowired
    public ListResource(ListService service, LegacyService legacyService) {
        this.service = service;
        this.legacyService = legacyService;
    }

    @RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
    public ResponseEntity<DataListRecord> getListByReference(@PathVariable("name") String name) {
        log.info("List \"{}\" requested", name);
        try {
            DataListRecord list = service.getListByName(name);
            return ResponseEntity.ok(list);
        } catch (ListNotFoundException e)
        {
            log.info("List \"{}\" not found", name);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity createList(@RequestBody DataList dataList) {
        log.info("Creating list \"{}\"", dataList.getName());
        try {
            service.createList(dataList);
            return ResponseEntity.ok().build();
        } catch (EntityCreationException e) {
            log.info("List \"{}\" not created", dataList.getName());
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @RequestMapping(value = "/legacy/topic/{name}", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromDCU(@RequestParam("file") MultipartFile file, @PathVariable("name") String name) {
        log.info("Parsing list \"TopicListDCU\"");
        if (!file.isEmpty()) {
            switch (name) {
                case "DCU":
                    DataList dataListDCU = legacyService.createDCUTopicsListFromCSV(file, "DCU_Topics", "DCU");
                    return createList(dataListDCU);
                case "UKVI":
                    DataList dataListUKVI = legacyService.createUKVITopicsListFromCSV(file, "UKVI_Topics", "UKVI");
                    return createList(dataListUKVI);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/legacy/units", method = RequestMethod.POST)
    public ResponseEntity createUnitsAndGroups(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"Teams and Units\"");
        if (!file.isEmpty()) {
            DataList dataListTeamsUnits = legacyService.createTeamsUnitsFromCSV(file, "Units");
            return createList(dataListTeamsUnits);
        }
        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(value = {"/legacy/topic/TopicList", "/service/homeoffice/ctsv2/topicList"}, method = RequestMethod.GET)
    public ResponseEntity<TopicListEntityRecord[]> getLegacyListByReference() {
        log.info("List \"Legacy TopicList\" requested");
        try {
            TopicListEntityRecord[] dcuList = legacyService.getLegacyTopicListByName("DCU_Topics");
            TopicListEntityRecord[] ukviList = legacyService.getLegacyTopicListByName("UKVI_Topics");

            return ResponseEntity.ok(concat(dcuList, ukviList));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TopicList\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/legacy/units", "/s/homeoffice/cts/allTeams"}, method = RequestMethod.GET)
    public ResponseEntity<UnitRecord> getLegacyUnits(){
        log.info("List \"Legacy All Units\" requested");
        try {
            UnitRecord units = legacyService.getLegacyUnitListByName("Units");

            return ResponseEntity.ok(units);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy All Units\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    //This is a create script, to be used once per new environment, maybe in the future this could just POST to alfresco directly.
    @RequestMapping(value = "/legacy/units/CreateUnits", method = RequestMethod.GET)
    public ResponseEntity<UnitCreateRecord> getLegacyUnitsByReference() {
        log.info("List \"Legacy Create Units Script\" requested");
        try {
            UnitCreateRecord units = legacyService.getLegacyUnitCreateListByName("CreateUnits");

            return ResponseEntity.ok(units);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy Create Units Script\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/legacy/users/{name}", method = RequestMethod.POST)
    public ResponseEntity createUsersDCU(@RequestParam("file") MultipartFile file, @PathVariable("name") String name) {
        log.info("Parsing list \"{} Users\"", name);
        if (!file.isEmpty()) {
            DataList users = legacyService.createUsersFromCSV(file, name + "_Users");
            return createList(users);
        }
        return ResponseEntity.badRequest().build();
    }

    //This is a create script, to be used once per new environment, maybe in the future this could just POST to alfresco directly.
    @RequestMapping(value = "/legacy/users/CreateUsers", method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyUsersByReference() {
        log.info("List \"Legacy Users\" requested");
        try {
            List<UserCreateEntityRecord> ret = new ArrayList<>();

            ret.addAll(legacyService.getLegacyUsersListByName("DCU_Users").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("FOI_Users").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("HMPOCCC_Users").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("HMPOCOL_Users").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("UKVI_Users").getUsers());

            return ResponseEntity.ok(new UserCreateRecord(ret));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy Users\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

   /* @RequestMapping(value = {"/legacy/users/{$user}/teams","s/homeoffice/cts/teamUsers"}, method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyTestUsersByReference(String name) {
        log.info("List \"Legacy user teams\" requested");
        try {
            return ResponseEntity.ok(legacyService.getLegacyTeamsByUserName(name));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy user teams\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }*/

    @RequestMapping(value = "/legacy/users/CreateTestUsers", method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyTestUsersByReference() {
        log.info("List \"Legacy TestUsers\" requested");
        try {
            return ResponseEntity.ok(legacyService.getLegacyTestUsersListByName("Test_Users"));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TestUsers\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}