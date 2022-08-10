package com.qulix.yurkevichvv.trainingtask.wicket.tables;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;

public class ProjectTable extends ListView<Project> {
    public ProjectTable(String id, List<Project> projects) {
        super(id, projects);
    }

    @Override
    protected void populateItem(ListItem<Project> item) {
        final Project project = item.getModelObject();
        item.add(new Label("title", project.getTitle()));
        item.add(new Label("description", project.getDescription()));
        item.add(new DeleteLink("deleteLink", item));
        item.add(new EditLink("editLink", item));
    }
}
