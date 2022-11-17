package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.EntityPageFactory;
import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity> extends Link<T> {

    /**
     * Страница для перехода.
     */
    private final T entity;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id, T entity) {
        super(id);
        this.entity = entity;
    }

    @Override
    public void onClick() {
        AbstractEntityPage page = new EntityPageFactory().getPage(entity);
        setResponsePage(page);
    }
}
