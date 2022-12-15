package com.qulix.yurkevichvv.trainingtask.wicket.companents.models;

import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;


/**
 * Определяет модель для списка задач.
 *
 * @author Q-YVV
 */
public class TaskListLoadableDetachableModel extends LoadableDetachableModel<List<Task>> {
    @Override
    protected List<Task> load() {
        return new TaskService().findAll();
    }
}
