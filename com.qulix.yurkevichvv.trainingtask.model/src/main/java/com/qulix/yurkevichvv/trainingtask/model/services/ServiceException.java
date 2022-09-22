package com.qulix.yurkevichvv.trainingtask.model.services;

public class ServiceException extends RuntimeException{

    /**
     * Конструктор класса с сообщением.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Конструктор класса с сообщением и исключением.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Конструктор класса с исключением.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
