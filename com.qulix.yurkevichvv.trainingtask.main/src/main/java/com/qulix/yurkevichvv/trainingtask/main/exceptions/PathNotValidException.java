package com.qulix.yurkevichvv.trainingtask.main.exceptions;

public class PathNotValidException extends Exception {

    public PathNotValidException(String message) {
        super(message);
    }

    public PathNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString()
    {
        return this.getMessage();
    }
}

