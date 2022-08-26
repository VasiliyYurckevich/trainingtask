package com.qulix.yurkevichvv.trainingtask.wicket.pages.lists;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AddTaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.tables.TaskTablePanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import java.util.List;

public class TasksListPage extends BasePage {

    public TasksListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Задачи");
        List<Task> tasks = new TaskDao().getAll();
        add(new TaskTablePanel("tasks", tasks));
        add(new Link<WebPage>("addTask") {
            @Override
            public void onClick() {
                setResponsePage(new AddTaskPage());
            }
        });
    }
}
