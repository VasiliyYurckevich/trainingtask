package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.SQLException;
import java.util.List;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface IDao<T> {

    /**
     * Добавление новой сущности в БД.
     *
     * @param t Сущность для добавления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean add(T t) throws DaoException, PathNotValidException;

    /**
     * Обновление сущности в БД.
     *
     * @param t Сущность для обновления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean update(T t) throws DaoException, PathNotValidException;

    /**
     * Удаление сущности из БД.
     *
     * @param t Сущность для удаления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean delete(Integer t) throws DaoException, PathNotValidException;

    /**
     * Получение всех сущностей определеного класса из БД.
     *
     * @return Список сущностей.
     * @throws SQLException ошибка при выполнении запроса.
     */
    List<T> getAll() throws DaoException, PathNotValidException;

    /**
     * Нахождение сущности по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность.
     * @throws SQLException ошибка при выполнении запроса.
     */
    T getById(Integer id) throws DaoException, PathNotValidException;
}
