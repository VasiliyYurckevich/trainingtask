package com.qulix.yurkevichvv.trainingtask.model.services;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

import java.util.List;

public interface IProjectService {

    List<Task> getProjectsTasks(Project project);

    void deleteTask(Project project, Task task);

    void addTask(Project project, Task task);

    void updateTask(Project project, Integer index, Task task);
}
