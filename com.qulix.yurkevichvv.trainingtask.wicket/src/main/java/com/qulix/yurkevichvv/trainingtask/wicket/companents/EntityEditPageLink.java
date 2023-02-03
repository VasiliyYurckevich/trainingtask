package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class EntityEditPageLink<T extends Entity> extends Link<Void> {

    private final AbstractEntityPageFactory<T> pageFactory;
    private final IModel<T> entityModel;

    public EntityEditPageLink(String id, AbstractEntityPageFactory<T> pageFactory, IModel<T> entityModel) {
        super(id);
        this.pageFactory = pageFactory;
        this.entityModel = entityModel;
    }

    @Override
    public void onClick() {
        System.out.println("ЫЫЫЫЫ СОздал");
        setResponsePage(pageFactory.createPage(CompoundPropertyModel.of(entityModel)));
    }
}
