package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.tables.ProjectTable;

import java.util.List;

public class ProjectsListPage extends BasePage {

    public ProjectsListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Проекты");
        List<Project> projects = new ProjectDao().getAll();
        add(new ProjectTable("projects", projects));
    }

}
