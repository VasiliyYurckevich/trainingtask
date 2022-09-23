package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import org.apache.wicket.markup.html.link.Link;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends BasePage> extends Link<T> {

    /**
     * Элемент ListView.
     */
    private T page  ;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param page элемент ListView
     */
    public EditLink(String id, T page) {
        super(id);
        this.page = page;
    }

    @Override
    public void onClick() {
        setResponsePage(page);
    }
}
