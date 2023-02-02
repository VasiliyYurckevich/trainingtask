package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования задачи.
 *
 * @author Q-YVV
 */
public class TaskPageFactory implements AbstractEntityPageFactory<Task> {

    @Override
    public AbstractEntityPage<Task> createPage(CompoundPropertyModel<Task> entityModel) {
        return new EditTaskPage(entityModel);
    }
}
