package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.legacy.topics.TopicListEntityRecord;
import com.sls.listService.dto.legacy.units.UnitCreateRecord;
import com.sls.listService.dto.legacy.users.UserCreateEntityRecord;
import com.sls.listService.dto.legacy.users.UserCreateRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/legacy/list/TopicList/DCU", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromDCU(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"TopicListDCU\"");
        if (!file.isEmpty()) {
            DataList dataListDCU = legacyService.createDCUTopicsListFromCSV(file, "TopicListDCU", "DCU");
            return createList(dataListDCU);
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/legacy/list/TopicList/UKVI", method = RequestMethod.POST)
    public ResponseEntity createTopicsListFromUKVI(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"TopicListUKVI\"");
        if (!file.isEmpty()) {
            DataList dataListUKVI = legacyService.createUKVITopicsListFromCSV(file, "TopicListUKVI", "UKVI");
            return createList(dataListUKVI);
        }
        return ResponseEntity.badRequest().build();
    }

    // 'service/homeoffice/...' path is legacy alfresco endpoint used by frontend consumers
    @RequestMapping(value = {"/legacy/list/TopicList", "/service/homeoffice/ctsv2/topicList"}, method = RequestMethod.GET)
    public ResponseEntity<TopicListEntityRecord[]> getLegacyListByReference() {
        log.info("List \"Legacy TopicList\" requested");
        try {
            TopicListEntityRecord[] dcuList = legacyService.getLegacyTopicListByName("TopicListDCU");
            TopicListEntityRecord[] ukviList = legacyService.getLegacyTopicListByName("TopicListUKVI");

            return ResponseEntity.ok(concat(dcuList, ukviList));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TopicList\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @RequestMapping(value = "/legacy/units", method = RequestMethod.POST)
    public ResponseEntity createUnitsAndGroups(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"Teams and Units\"");
        if (!file.isEmpty()) {
            DataList dataListTeamsUnits = legacyService.createTeamsUnitsFromCSV(file, "UnitTeams");
            return createList(dataListTeamsUnits);
        }
        return ResponseEntity.badRequest().build();
    }

    //This is a create script, to be used once per new environment, maybe in the future this could just POST to alfresco directly.
    @RequestMapping(value = "/legacy/units/UnitTeams", method = RequestMethod.GET)
    public ResponseEntity<UnitCreateRecord> getLegacyUnitsByReference() {
        log.info("List \"Legacy UnitTeams\" requested");
        try {
            UnitCreateRecord units = legacyService.getLegacyUnitCreateListByName("UnitTeams");

            return ResponseEntity.ok(units);
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy UnitTeams\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/legacy/users/DCU", method = RequestMethod.POST)
    public ResponseEntity createUsersDCU(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"DCU Users\"");
        return parseUserFile(file, "UsersDCU");
    }

    @RequestMapping(value = "/legacy/users/FOI", method = RequestMethod.POST)
    public ResponseEntity createUsersFOI(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"FOI Users\"");
        return parseUserFile(file, "UsersFOI");
    }

    @RequestMapping(value = "/legacy/users/HMPOCCC", method = RequestMethod.POST)
    public ResponseEntity createUsersHMPOCCC(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"HMPOCCC Users\"");
        return parseUserFile(file, "UsersHMPOCCC");
    }

    @RequestMapping(value = "/legacy/users/HMPOCOL", method = RequestMethod.POST)
    public ResponseEntity createUsersHMPOCOL(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"HMPOCOL Users\"");
        return parseUserFile(file, "UsersHMPOCOL");
    }

    @RequestMapping(value = "/legacy/users/UKVI", method = RequestMethod.POST)
    public ResponseEntity createUsersUKVI(@RequestParam("file") MultipartFile file) {
        log.info("Parsing list \"UKVI Users\"");
        return parseUserFile(file, "UsersUKVI");
    }

    @RequestMapping(value = "/legacy/users/AllUsers", method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyUsersByReference() {
        log.info("List \"Legacy Users\" requested");
        try {
            List<UserCreateEntityRecord> ret = new ArrayList<>();

            ret.addAll(legacyService.getLegacyUsersListByName("UsersDCU").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("UsersFOI").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("UsersHMPOCCC").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("UsersHMPOCOL").getUsers());
            ret.addAll(legacyService.getLegacyUsersListByName("UsersUKVI").getUsers());

            return ResponseEntity.ok(new UserCreateRecord(ret));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy Users\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/legacy/users/TestUsers", method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyTestUsersByReference() {
        log.info("List \"Legacy TestUsers\" requested");
        try {
            return ResponseEntity.ok(legacyService.getLegacyTestUsersListByName("UsersTest"));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy TestUsers\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity parseUserFile(MultipartFile file, String listName) {
        if (!file.isEmpty()) {
            DataList users = legacyService.createUsersFromCSV(file, listName);
            return createList(users);
        }
        return ResponseEntity.badRequest().build();
    }

}