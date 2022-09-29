package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;




/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends AbstractEntityPage> extends Link<T> {

    /*
     * Элемент ListView.
     */
    private final AbstractEntityPage page;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id, AbstractEntityPage page) {
        super(id);
        this.page = page;
    }

    @Override
    public void onClick() {
        setResponsePage(page);
    }
}
