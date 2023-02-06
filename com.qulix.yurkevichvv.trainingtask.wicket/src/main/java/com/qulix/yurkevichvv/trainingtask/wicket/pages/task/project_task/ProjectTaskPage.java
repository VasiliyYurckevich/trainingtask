package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AbstractTaskPage;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

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
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
    }

    @Override
    protected boolean changeProjectOption() {
        return false;
    }
}
