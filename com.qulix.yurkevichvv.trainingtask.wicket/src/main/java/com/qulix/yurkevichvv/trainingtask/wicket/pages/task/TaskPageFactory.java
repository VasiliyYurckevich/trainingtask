package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.model.CompoundPropertyModel;

public class TaskPageFactory implements AbstractEntityPageFactory<Task> {
    @Override
    public AbstractEntityPage<Task> createPage(CompoundPropertyModel<Task> entityModel) {
        return new TaskPage(entityModel);
    }
}
