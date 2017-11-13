package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicGroupRecord;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.legacy.CSVList;
import uk.gov.digital.ho.hocs.legacy.topics.CSVTopicLine;
import uk.gov.digital.ho.hocs.model.Topic;
import uk.gov.digital.ho.hocs.model.TopicGroup;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicsService {

    private final TopicsRepository repo;

    @Autowired
    public TopicsService(TopicsRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "topics", key = "#caseType")
    public List<TopicGroupRecord> getTopicByCaseType(String caseType) throws ListNotFoundException {
        try {
            List<TopicGroup> list = repo.findAllByCaseType(caseType);
            return list.stream().map(TopicGroupRecord::create).collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new ListNotFoundException();
        }
    }

    @CacheEvict(value = "topics", key = "#caseType")
    public void createTopicsListFromCSV(CSVList list, String caseType) {
        Map<String, Set<Topic>> topics = new HashMap<>();

        List<CSVTopicLine> lines = list.getLines();
        for (CSVTopicLine line : lines) {
            topics.putIfAbsent(line.getParentTopicName(), new HashSet<>());

            Topic topic = new Topic(line.getTopicName(), line.getTopicUnit(), line.getTopicTeam() );
            topics.get(line.getParentTopicName()).add(topic);
        }

        Set<TopicGroup> topicGroups = new HashSet<>();
        for (Map.Entry<String, Set<Topic>> entity : topics.entrySet()) {
            String parentTopicName = entity.getKey();

            TopicGroup topicGroup = new TopicGroup(parentTopicName, caseType);
            topicGroup.setTopicListItems(entity.getValue());

            topicGroups.add(topicGroup);
        }

        createTopics(topicGroups);
    }

    private void createTopics(Set<TopicGroup> topicGroups) {
        try {
            repo.save(topicGroups);
        } catch (DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException &&
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("topic_group_name_idempotent") ||
                    ((ConstraintViolationException) e.getCause()).getConstraintName().toLowerCase().contains("topic_name_ref_idempotent")) {
                throw new EntityCreationException("Identified an attempt to recreate existing entity, rolling back");
            }

            throw e;
        }
    }

}
