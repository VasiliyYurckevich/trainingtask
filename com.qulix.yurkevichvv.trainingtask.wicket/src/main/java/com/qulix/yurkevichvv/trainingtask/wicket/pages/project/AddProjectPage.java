package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.ProjectForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import org.apache.wicket.markup.html.form.RequiredTextField;

public class AddProjectPage extends BasePage {

    private ProjectDao projectDao= new ProjectDao();

    public AddProjectPage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить проект");
        ProjectForm projectForm = new ProjectForm("addProjectForm"){
            @Override
            protected void onSubmit() {
                projectDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        projectForm.add(new RequiredTextField<String>("title"));
        projectForm.add(new RequiredTextField<String>("description"));
        add(projectForm);
    }
}
