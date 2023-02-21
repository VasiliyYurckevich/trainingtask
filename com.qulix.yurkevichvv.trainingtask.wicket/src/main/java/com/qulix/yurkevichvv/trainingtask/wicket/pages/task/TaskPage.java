package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Страница редактирования задачи.
 *
 * @author Q-YVV
 */
public class TaskPage extends AbstractTaskPage {

    /**
     * Конструктор.
     *
     * @param entityModel модель задачи
     */
    public TaskPage(CompoundPropertyModel<Task> entityModel) {
        super(entityModel, new TaskForm("taskForm", entityModel));
    }
}
