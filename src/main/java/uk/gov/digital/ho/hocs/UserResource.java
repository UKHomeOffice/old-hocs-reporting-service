package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserCreateRecord;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;

@RestController
@Slf4j
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{name}", method = RequestMethod.POST)
    public ResponseEntity createUsers(@RequestParam("file") MultipartFile file, @PathVariable("name") String name) {
        if (!file.isEmpty()) {
            log.info("Parsing {} Users File", name);
            try {
                userService.createUsersFromCSV(file, name);
                return ResponseEntity.ok().build();
            } catch (EntityCreationException e) {
                log.info("{} Users not created", name);
                log.info(e.getMessage());
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    //This is a create script, to be used once per new environment, maybe in the future this could just POST to alfresco directly.
    @RequestMapping(value = "/legacy/users/{name}/export", method = RequestMethod.GET)
    public ResponseEntity<UserCreateRecord> getLegacyUsersByReference(@PathVariable("name") String name) {
        log.info("export \"Legacy Users\" requested");
        try {
            UserCreateRecord users = userService.getLegacyUsersByDepartment(name);
            return ResponseEntity.ok(users);
        } catch (ListNotFoundException e) {
            log.info("export \"Legacy Users\" failed");
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "s/homeoffice/cts/teamUsers", method = RequestMethod.GET)
    public ResponseEntity<UserRecord> getLegacyTeamsByGroupReference(@RequestParam String group) {
        log.info("List \"Legacy teamUsers\" requested");
        try {
            return ResponseEntity.ok(userService.getLegacyTeamsByGroupName(group));
        } catch (ListNotFoundException e) {
            log.info("List \"Legacy teamUsers\" not found");
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
