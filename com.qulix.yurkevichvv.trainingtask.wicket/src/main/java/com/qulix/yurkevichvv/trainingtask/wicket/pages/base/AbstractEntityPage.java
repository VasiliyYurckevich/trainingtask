package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

public abstract class AbstractEntityPage<T extends Entity> extends BasePage {


    public AbstractEntityPage() {
        super();

    }

    public abstract void setEntity(T entity);

    protected abstract void onSubmitting();

    protected abstract void onChangesSubmitted();
}
