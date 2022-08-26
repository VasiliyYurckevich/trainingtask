package com.qulix.yurkevichvv.trainingtask.wicket.links;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AddTaskPage;
import org.apache.wicket.markup.html.link.Link;

import java.util.List;

public class AddInProjectLink extends Link<Void> {
    private Project project;
    private List<Task> list;


    public AddInProjectLink (String id, Project project, List<Task> list) {
        super(id);
        this.project = project;
        this.list = list;
    }

    @Override
    public void onClick() {
        setResponsePage(new AddTaskPage(project, list));
    }
}
