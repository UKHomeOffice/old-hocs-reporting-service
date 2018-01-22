package uk.gov.digital.ho.hocs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.digital.ho.hocs.model.CaseCurrentProperties;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface CaseCurrentPropertiesRepository extends CrudRepository<CaseCurrentProperties, Long> {

    CaseCurrentProperties findByCaseRef(String caseReference);

    Set<CaseCurrentProperties> getAllByTimestampBetweenAndCorrespondenceTypeIn(LocalDateTime start, LocalDateTime end, String[] correspondenceTypes);
}
