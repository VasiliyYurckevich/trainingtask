package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;

/**
 * Страница редактирования задачи.
 *
 * @author Q-YVV
 */
public class EditTaskPage extends AbstractTaskPage {

    /**
     * Конструктор.
     *
     * @param entityModel модель задачи
     */
    public EditTaskPage(CompoundPropertyModel<Task> entityModel) {
        super(entityModel, new TaskForm("taskForm", entityModel));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
    }
}
