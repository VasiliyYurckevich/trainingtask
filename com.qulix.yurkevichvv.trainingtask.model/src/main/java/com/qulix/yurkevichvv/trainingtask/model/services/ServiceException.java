package com.qulix.yurkevichvv.trainingtask.model.services;

import java.io.Serializable;

/**
 * Описывает исключение, возникающее при работе сущностями.
 *
 * @author Q-YVV
 */
public class ServiceException extends RuntimeException implements Serializable {

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
