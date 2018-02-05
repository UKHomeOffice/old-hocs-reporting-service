package uk.gov.digital.ho.hocs;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.digital.ho.hocs.model.CaseProperties;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface CasePropertiesRepository extends CrudRepository<CaseProperties, Long> {

    @Query(value = "select pr.* from properties pr, (select max(id) as id, max(msg_timestamp), p.case_reference from properties p where p.correspondence_type in ?3 and p.msg_timestamp between ?1 and ?2 GROUP BY p.case_reference) spr where pr.id = spr.id", nativeQuery = true)
    Set<CaseProperties> getAllByTimestampBetweenAndCorrespondenceTypeIn(LocalDateTime start, LocalDateTime end, String[] correspondenceTypes);


}
