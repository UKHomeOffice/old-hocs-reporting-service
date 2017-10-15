package com.sls.listService;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends CrudRepository<DataList, Long> {

    DataList findOneByReference(String reference);

}


