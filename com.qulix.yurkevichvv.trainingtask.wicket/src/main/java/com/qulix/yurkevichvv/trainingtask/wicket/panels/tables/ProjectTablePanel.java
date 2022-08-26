package com.qulix.yurkevichvv.trainingtask.wicket.panels.tables;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

public class ProjectTablePanel extends Panel {
    public ProjectTablePanel(String id, List<Project> projectList) {
        super(id);
        ListView<Project> projects = new ListView<Project>("projects", projectList) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                final Project project = item.getModelObject();
                item.add(new Label("title", project.getTitle()));
                item.add(new Label("description", project.getDescription()));
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(projects);
    }
}
