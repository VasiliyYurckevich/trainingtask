package com.qulix.yurkevichvv.trainingtask.model.services;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

/**
 * Методы для работы с Project.
 *
 * @author Q-YVV
 */
public interface IService<T extends Entity> {

    /**
     * Сохраняет сущность в БД.
     *
     * @param entity сущность.
     */
    void save(T entity) throws ServiceException;

    /**
     * Удаляет сущность из БД по идентификатору.
     *
     * @param id идентификатор.
     */
    void delete(Integer id) throws ServiceException;

    /**
     * Возвращает все сущности определенного класса из БД.
     *
     * @return Список сущностей
     */
    List<T> findAll() throws ServiceException;

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность
     */
    T getById(Integer id) throws ServiceException;
}
