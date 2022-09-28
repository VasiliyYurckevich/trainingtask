package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;


/**
 * Ссылка для удаления сущности.
 *
 * @author Q-YVV
 */
public class DeleteLink<T extends Entity> extends Link<T> {

    private final IService service;
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
    public DeleteLink(String id, IService service, T entity) {
        super(id);
        this.entity = entity;
        this.service = service;
    }

    @Override
    public void onClick() {
        service.delete(entity.getId());
    }
}
