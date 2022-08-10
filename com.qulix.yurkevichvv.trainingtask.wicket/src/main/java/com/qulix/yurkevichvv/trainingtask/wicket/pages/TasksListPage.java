package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.tables.TasksTable;

import java.util.List;

public class TasksListPage extends BasePage {

    public TasksListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Задачи");
        List<Task> tasks = new TaskDao().getAll();
        add(new TasksTable("tasks", tasks));
    }
}
