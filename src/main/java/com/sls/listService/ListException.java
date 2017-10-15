package com.sls.listService;

public class ListException extends RuntimeException {

    public ListException(String message) {
        super(message);
    }

    public ListException(String message, Throwable cause) {
        super(message, cause);
    }
}
