package uk.gov.digital.ho.hocs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.digital.ho.hocs.model.CaseProperties;

import java.util.Set;

@Repository
public interface CasePropertiesRepository extends CrudRepository<CaseProperties, Long> {

    Set<CaseProperties> findAllBy();
}
