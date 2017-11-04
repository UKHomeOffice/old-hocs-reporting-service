package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserCreateRecord;
import uk.gov.digital.ho.hocs.dto.legacy.users.UserRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.legacy.users.CSVUserLine;
import uk.gov.digital.ho.hocs.legacy.users.UserFileParser;
import uk.gov.digital.ho.hocs.model.BusinessGroup;
import uk.gov.digital.ho.hocs.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BusinessGroupRepository groupRepository;

    @Autowired
    public UserService(UserRepository userRepository, BusinessGroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }


    public UserCreateRecord getLegacyUsersByDepartment(String name) throws ListNotFoundException {
        try {
            List<User> list = userRepository.findAllByDepartment(name);
            return UserCreateRecord.create(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    public UserRecord getLegacyTeamsByGroupName(String group) throws ListNotFoundException {
        try {
            List<User> list = userRepository.findAllByReferenceName(group);
            return UserRecord.create(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    @CacheEvict(value = "users", allEntries = true, beforeInvocation = true)
    public void createUsersFromCSV(MultipartFile file, String department) {
        List<CSVUserLine> lines = new UserFileParser(file).getLines();

        Set<User> users = new HashSet<>();
        for (CSVUserLine line : lines) {
            User user = new User(line.getFirst(), line.getLast(), line.getEmail(), line.getEmail(), department);

            Set<BusinessGroup> groups = new HashSet<>();
            for(String group : line.getGroups()) {
                groups.add(groupRepository.findByReferenceName(group));
            }
            user.setGroups(groups);
            users.add(user);
        }

        createUsers(users);
    }

    private void createUsers(Set<User> users) {
        try {
            userRepository.save(users);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("user_name_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }
}