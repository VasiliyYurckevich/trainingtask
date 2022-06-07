package com.qulix.yurkevichvv.trainingtask.main.exceptions;

public class DaoErrorException extends Exception {

    public DaoErrorException(String message) {
        super(message);
    }

    public DaoErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoErrorException(Throwable cause) {
        super(cause);
    }
}


