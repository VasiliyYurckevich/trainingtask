package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Ссылка для перехода на страницу редактирования сущности.
 *
 * @param <T> сущность
 * @author Q-YVV
 */
public class EntityEditPageLink<T extends Entity> extends Link<Void> {

    /**
     * Фабрика страниц.
     */
    private final AbstractEntityPageFactory<T> pageFactory;

    /**
     * Модель {@link Entity}.
     */
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
