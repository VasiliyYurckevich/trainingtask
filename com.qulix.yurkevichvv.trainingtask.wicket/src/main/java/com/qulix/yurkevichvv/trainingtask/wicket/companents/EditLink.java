package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
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
    private Class<Page> pageClass ;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id, Class<Page> pageClass,T entity) {
        super(id, new Model<T>(entity));
        this.pageClass = pageClass;
        this.entity = entity;
    }

    @Override
    public void onClick() {
        try {
            Page page = pageClass.getConstructor(entity.getClass()).newInstance(entity);
            setResponsePage(page);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
