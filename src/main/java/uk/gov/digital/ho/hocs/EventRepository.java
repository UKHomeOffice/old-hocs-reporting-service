package uk.gov.digital.ho.hocs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.digital.ho.hocs.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
