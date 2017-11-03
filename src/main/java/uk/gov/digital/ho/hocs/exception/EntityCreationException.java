package uk.gov.digital.ho.hocs.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EntityCreationException extends DataIntegrityViolationException {

    public EntityCreationException(String msg) {
        super(msg);
    }
}