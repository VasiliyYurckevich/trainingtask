package com.qulix.yurkevichvv.trainingtask.wicket.pages.lists;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.AddProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.tables.ProjectTable;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import java.util.List;

public class ProjectsListPage extends BasePage {

    public ProjectsListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Проекты");
        List<Project> projects = new ProjectDao().getAll();
        add(new ProjectTable("projects", projects));
        add(new Link<WebPage>("addProject") {
            @Override
            public void onClick() {
                setResponsePage(new AddProjectPage());
            }
        });
    }

}
