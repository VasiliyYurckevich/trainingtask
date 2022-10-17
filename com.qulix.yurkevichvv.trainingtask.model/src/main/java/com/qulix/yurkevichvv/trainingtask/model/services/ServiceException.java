package com.qulix.yurkevichvv.trainingtask.model.services;

/**
 * Описывает исключение, возникающее при работе сущностями.
 *
 * @author Q-YVV
 */
public class ServiceException extends RuntimeException {

    /**
     * Конструктор класса с исключением.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

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
