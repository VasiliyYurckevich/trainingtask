package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.ProjectForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import org.apache.wicket.markup.html.form.RequiredTextField;

public class EditProjectPage extends BasePage {

    private ProjectDao projectDao= new ProjectDao();

    public EditProjectPage(final Project project) {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        ProjectForm projectForm = new ProjectForm("addProjectForm", project){
            @Override
            protected void onSubmit() {
                projectDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        projectForm.add(new RequiredTextField<String>("title"));
        projectForm.add(new RequiredTextField<String>("description"));
        add(projectForm);
    }
}
