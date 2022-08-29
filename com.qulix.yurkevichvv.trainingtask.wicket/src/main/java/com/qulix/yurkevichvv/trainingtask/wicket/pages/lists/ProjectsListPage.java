package com.qulix.yurkevichvv.trainingtask.wicket.pages.lists;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;

public class ProjectsListPage extends BasePage {

    public ProjectsListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Проекты");
        List<Project> projects = new ProjectDao().getAll();
        ListView<Project> projectListView = new ListView<>("projects", projects) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                final Project project = item.getModelObject();
                item.add(new Label("title", project.getTitle()));
                item.add(new Label("description", project.getDescription()));
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(projectListView);
        add(new Link<WebPage>("addProject") {
            @Override
            public void onClick() {
                setResponsePage(new ProjectPage());
            }
        });
    }

}
