package com.qulix.yurkevichvv.trainingtask.main.exceptions;

public class InputDataException extends Exception {

    public InputDataException(String message) {
        super(message);
    }

    public InputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputDataException(Throwable cause) {
        super(cause);
    }
}
