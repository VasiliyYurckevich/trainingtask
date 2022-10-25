package com.qulix.yurkevichvv.trainingtask.model.dao;

/**
 * Описывает исключение, возникающее при ошибке выполнения запроса к базе данных.
 *
 * @author Q-YVV
 */
public class DaoException extends RuntimeException {

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

    /**
     * Конструктор класса с исключением.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
