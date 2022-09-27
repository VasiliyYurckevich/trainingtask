package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.sql.Connection;

import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;



/**
 * Ссылка для удаления сущности.
 *
 * @author Q-YVV
 */
public class DeleteLink<T extends Entity> extends Link<T> {

    /**
     * Элемент ListView.
     */
    private T entity;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param entity элемент ListView
     */
    public DeleteLink(String id, T entity) {
        super(id);
        this.entity = entity;
    }

    @Override
    public void onClick() {
        entity.getService().delete(entity.getId());
    }
}
