package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.ProjectForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.RequiredTextField;

public class AddProjectPage extends BasePage {

    private ProjectDao projectDao= new ProjectDao();

    public AddProjectPage() {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        ProjectForm projectForm = new ProjectForm("addProjectForm"){
            @Override
            protected void onSubmit() {
                projectDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        RequiredTextField<String> title = new RequiredTextField<>("title");
        title.add(new CustomStringValidator(50));
        projectForm.add(title);
        RequiredTextField<String> description = new RequiredTextField<>("description");
        description.add(new CustomStringValidator(250));
        projectForm.add(description);
        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel", new ComponentFeedbackMessageFilter(title));
        projectForm.add(titleFeedbackPanel);
        CustomFeedbackPanel descriptionFeedbackPanel = new CustomFeedbackPanel("descriptionFeedbackPanel", new ComponentFeedbackMessageFilter(description));
        projectForm.add(descriptionFeedbackPanel);
        add(projectForm);
    }
}
