package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import org.apache.wicket.markup.html.link.Link;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity> extends Link<T> {

    /**
     * Элемент ListView.
     */
    private T item;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public EditLink(String id, T item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
    }
}
