package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.io.Serializable;

/**
 * Описывает исключение, возникающее при ошибке выполнения запроса к базе данных.
 *
 * @author Q-YVV
 */
public class DaoException extends RuntimeException implements Serializable {

    /**
     * Конструктор класса с сообщением.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Конструктор класса с сообщением и исключением.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
