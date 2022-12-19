package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity & Serializable> extends Link<T> {

    /**
     * Модель сущности.
     */
    private final IModel<T> model;

    /**
     * Фабрика для генерации страниц сущностей.
     */
    private final AbstractEntityPageFactory<T> pageFactory;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param  pageFactory фабрика страниц
     * @param model модель
     */
    public EditLink(String id, AbstractEntityPageFactory<T> pageFactory, IModel<T> model) {
        super(id);
        this.model = model;
        this.pageFactory = pageFactory;
    }

    @Override
    public void onClick() {
        setResponsePage(pageFactory.createPage(CompoundPropertyModel.of(model)));
    }
}
