package uk.gov.digital.ho.hocs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.digital.ho.hocs.model.BusinessGroup;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<BusinessGroup, Long> {
    BusinessGroup findOneByReferenceName(String referenceName);

    List<BusinessGroup> findAllBy();



}
