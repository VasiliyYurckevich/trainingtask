package com.qulix.yurkevichvv.trainingtask.wicket.companents.models;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

public class TaskListLoadableDetachableModel extends LoadableDetachableModel<List<Task>> {
    @Override
    protected List<Task> load() {
        return new TaskService().findAll();
    }
}