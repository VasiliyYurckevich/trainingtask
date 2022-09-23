package com.qulix.yurkevichvv.trainingtask.model.entity;

import com.qulix.yurkevichvv.trainingtask.model.dao.IDao;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;

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
    IService getService();
}
