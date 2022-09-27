package com.qulix.yurkevichvv.trainingtask.model.services;

public class ServiceException extends RuntimeException{

    /**
     * Конструктор класса с сообщением и исключением.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
