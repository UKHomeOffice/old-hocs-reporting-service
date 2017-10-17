package com.sls.listService;

import org.springframework.dao.DataIntegrityViolationException;

public class EntityCreationException extends DataIntegrityViolationException {

    public EntityCreationException(String msg) {
        super(msg);
    }
}

