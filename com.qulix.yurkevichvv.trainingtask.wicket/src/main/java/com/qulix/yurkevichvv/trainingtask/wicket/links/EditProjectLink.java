package com.qulix.yurkevichvv.trainingtask.wicket.links;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AddTaskPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import java.util.List;

public class EditProjectLink extends Link<Project> {
    private ListItem<Project> item;
    private List<Task> list;


    public EditProjectLink(String id, ListItem<Project> item, List<Task> list) {
        super(id);
        this.item = item;
        this.list = list;
    }

    @Override
    public void onClick() {
        setResponsePage(new AddTaskPage(item.getModelObject(), list));
    }
}

