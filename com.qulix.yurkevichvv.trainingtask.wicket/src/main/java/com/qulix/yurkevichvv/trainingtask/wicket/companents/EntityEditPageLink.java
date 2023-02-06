package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

public class EntityEditPageLink<T extends Entity> extends Link<Void> {

    private final AbstractEntityPageFactory<T> pageFactory;
    private final IModel<T> entityModel;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param pageFactory фабрика страниц
     * @param entityModel модель {@link Entity}
     */
    public EntityEditPageLink(String id, AbstractEntityPageFactory<T> pageFactory, IModel<T> entityModel) {
        super(id);
        this.pageFactory = pageFactory;
        this.entityModel = entityModel;
    }

    @Override
    public void onClick() {
        setResponsePage(pageFactory.createPage(CompoundPropertyModel.of(entityModel)));
    }
}
