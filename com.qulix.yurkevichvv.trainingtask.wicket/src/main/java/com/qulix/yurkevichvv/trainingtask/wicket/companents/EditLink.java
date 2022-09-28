package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;




/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity & Serializable > extends Link<T> {

    private final T entity;
    /*
     * Элемент ListView.
     */
    private AbstractEntityPage page;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id, AbstractEntityPage page, T entity) {
        super(id);
        this.page = page;
        this.entity = entity;
    }

    @Override
    public void onClick() {
        setResponsePage(page);
    }
}
