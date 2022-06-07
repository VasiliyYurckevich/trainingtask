package com.qulix.yurkevichvv.trainingtask.main.exceptions;

public class EntityFailedException extends Exception {

    public EntityFailedException(String message) {
        super(message);
    }

    public EntityFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityFailedException(Throwable cause) {
        super(cause);
    }
}

