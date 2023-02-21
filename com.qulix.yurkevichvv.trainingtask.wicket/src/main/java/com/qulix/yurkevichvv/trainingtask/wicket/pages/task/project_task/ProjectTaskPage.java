package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AbstractTaskPage;

/**
 * Страница задачи, связанной с данными проекта.
 *
 * @author Q-YVV
 */
public class ProjectTaskPage extends AbstractTaskPage {

    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     */
    public ProjectTaskPage(CompoundPropertyModel<Task> taskModel,
        CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel) {
        super(taskModel, new ProjectTaskForm("taskForm", taskModel, projectTemporaryDataModel));
    }

    @Override
    protected boolean changeProjectOption() {
        return false;
    }
}
