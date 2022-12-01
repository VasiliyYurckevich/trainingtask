package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования задачи.
 *
 * @author Q-YVV
 */
public class TaskPageFactory implements AbstractEntityPageFactory<Task> {
    @Override
    public AbstractEntityPage<Task> createPage(CompoundPropertyModel<Task> entityModel) {
        return new TaskPage(entityModel);
    }
}
