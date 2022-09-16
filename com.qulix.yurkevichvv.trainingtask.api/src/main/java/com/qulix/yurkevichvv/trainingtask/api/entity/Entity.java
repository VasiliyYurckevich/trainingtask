package com.qulix.yurkevichvv.trainingtask.api.entity;

import com.qulix.yurkevichvv.trainingtask.api.dao.IDao;

/**
 * Интерфейс сущностей.
 *
 * @author Q-YVV
 */
public interface Entity {

    /**
     * Возвращает идентификатор сущности.
     *
     * @return идентификатор
     */
    Integer getId();

    /**
     * Возвращает объект DAO-класса, связанный с сущностью.
     *
     * @return  объект DAO-класса
     */
    IDao getDao();
}
