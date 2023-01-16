package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.beans.JavaBean;
import java.io.Serializable;

/**
 * Интерфейс сущностей.
 *
 * @author Q-YVV
 */
@JavaBean
public interface Entity extends Serializable {

    /**
     * Возвращает идентификатор сущности.
     *
     * @return идентификатор
     */
    Integer getId();
}
