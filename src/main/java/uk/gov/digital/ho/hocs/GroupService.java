package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.digital.ho.hocs.dto.legacy.units.UnitRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.legacy.units.CSVGroupLine;
import uk.gov.digital.ho.hocs.legacy.units.UnitFileParser;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.*;

@Service
@Slf4j
public class GroupService {
    private final GroupRepository repo;

    @Autowired
    public GroupService(GroupRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "group")
    public UnitRecord getAllGroups() throws ListNotFoundException {
        try {
            List<BusinessGroup> list = repo.findAllBy();
            return UnitRecord.create(list);
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    @CacheEvict(value = "group", key = "#businessGroup.referenceName()", beforeInvocation = true)
    public void createGroup(BusinessGroup businessGroup) throws EntityCreationException {
        Set<BusinessGroup> groups = new HashSet<>();
        groups.add(businessGroup);
        createGroup(groups);
    }

    @CacheEvict(value = "group", allEntries = true, beforeInvocation = true)
    public void createGroupsFromCSV(MultipartFile file) {
        List<CSVGroupLine> lines = new UnitFileParser(file).getLines();

        Map<String, Set<BusinessGroup>> groupMap = new HashMap<>();
        for (CSVGroupLine line : lines) {
            groupMap.putIfAbsent(line.getUnit(), new HashSet<>());
            groupMap.get(line.getUnit()).add(new BusinessGroup(line.getTeam(), line.getTeamValue()));
        }

        Set<BusinessGroup> groups = new HashSet<>();
        for (Map.Entry<String, Set<BusinessGroup>> entity : groupMap.entrySet()) {
            BusinessGroup group = new BusinessGroup(entity.getKey());
            group.setSubGroups(entity.getValue());
            groups.add(group);
        }

        createGroup(groups);
    }

//    public UnitCreateRecord getLegacyUnitCreateListByName(String name) throws ListNotFoundException {
//        try {
//            DataList list = repo.findOneByName(name);
//            return UnitCreateRecord.create(list);
//        } catch (NullPointerException e) {
//            throw new ListNotFoundException();
//        }
//    }

    private void createGroup(Set<BusinessGroup> groups) {
        try {
            repo.save(groups);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("user_name_idempotent") ||
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("group_name_ref_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }
}