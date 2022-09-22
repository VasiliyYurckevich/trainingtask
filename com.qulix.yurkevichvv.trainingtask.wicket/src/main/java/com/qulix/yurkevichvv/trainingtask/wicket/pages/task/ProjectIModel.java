package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

class ProjectIModel implements IModel<Project> {
    private final List<Project> list;
    private final Task task;

    public ProjectIModel(List<Project> list, Task task) {
        this.list = list;
        this.task = task;
    }

    @Override
    public Project getObject() {
        for (Project project : list) {
            if (project.getId().equals(task.getProjectId())) {
                return project;
            }
        }
        return null;
    }

    @Override
    public void setObject(final Project project) {
        task.setProjectId(project.getId());
    }
}
