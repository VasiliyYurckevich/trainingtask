package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProjectTaskPage extends AbstractTaskPage {
    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     * @param form
     */
    public ProjectTaskPage(CompoundPropertyModel<Task> taskModel, AbstractEntityForm<Task> form) {
        super(taskModel, form);
    }
}
