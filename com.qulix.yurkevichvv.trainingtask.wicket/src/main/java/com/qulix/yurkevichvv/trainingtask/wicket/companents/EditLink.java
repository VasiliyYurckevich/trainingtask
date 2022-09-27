package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

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
    private AbstractEntityPage<T> page;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id, AbstractEntityPage<T> page,T entity) {
        super(id);
        this.page = page;
        this.entity = entity;
    }

    @Override
    public void onClick() {
      page.setEntity(entity);
      setResponsePage(page);
    }
}
