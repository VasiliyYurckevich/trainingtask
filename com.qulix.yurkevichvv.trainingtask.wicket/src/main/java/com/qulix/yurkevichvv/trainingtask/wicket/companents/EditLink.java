package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Page,V extends Entity & Serializable> extends Link<T> {

    private final V entity;
    /*
     * Элемент ListView.
     */
    private Page page ;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param page элемент ListView
     */
    public EditLink(String id, T page,V entity) {
        super(id, entity);
        this.page = page;
        this.entity = entity;
    }

    @Override
    public void onClick() {
       setResponsePage(page);
    }
}
