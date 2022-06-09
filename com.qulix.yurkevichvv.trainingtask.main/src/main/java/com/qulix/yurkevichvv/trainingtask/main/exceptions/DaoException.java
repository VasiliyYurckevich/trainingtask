package com.qulix.yurkevichvv.trainingtask.main.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString()
    {
        return this.getMessage();
    }

}


