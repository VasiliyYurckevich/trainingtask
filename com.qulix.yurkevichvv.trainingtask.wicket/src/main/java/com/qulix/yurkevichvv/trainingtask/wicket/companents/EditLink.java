package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity & Serializable> extends Link<T> {

    /**
     * Страница для перехода.
     */
    private final IModel<T> model;

    private final AbstractEntityPageFactory<T> pageFactory;
    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public EditLink(String id,  AbstractEntityPageFactory<T> pageFactory, IModel<T> model) {
        super(id);
        this.model = model;
        this.pageFactory = pageFactory;
    }

    @Override
    public void onClick() {
        setResponsePage(pageFactory.createPage(CompoundPropertyModel.of(model)));
    }
}
