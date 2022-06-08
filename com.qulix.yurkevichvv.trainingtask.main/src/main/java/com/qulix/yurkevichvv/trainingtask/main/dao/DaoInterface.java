package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.SQLException;
import java.util.List;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface DaoInterface<T> {

    /**
     * Добавление новой сущности в БД.
     *
     * @param t Сущность для добавления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean add(T t) throws DaoException;

    /**
     * Обновление сущности в БД.
     *
     * @param t Сущность для обновления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean update(T t) throws DaoException;

    /**
     * Удаление сущности из БД.
     *
     * @param t Сущность для удаления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean delete(Integer t) throws DaoException;

    /**
     * Получение всех сущностей определеного класса из БД.
     *
     * @return Список сущностей.
     * @throws SQLException ошибка при выполнении запроса.
     */
    List<T> getAll() throws DaoException;

    /**
     * Нахождение сущности по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность.
     * @throws SQLException ошибка при выполнении запроса.
     */
    T getById(Integer id) throws DaoException;
}
