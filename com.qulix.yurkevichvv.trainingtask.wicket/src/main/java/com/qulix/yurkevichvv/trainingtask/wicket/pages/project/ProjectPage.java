package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProjectPage extends BasePage {

    public ProjectPage() {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        Form<Project> projectForm = new Form<>("projectForm", new CompoundPropertyModel<>(new Project())){
            @Override
            protected void onSubmit() {
                ProjectDao projectDao= new ProjectDao();
                projectDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        addContent(projectForm);
        add(projectForm);
    }

    protected static void addContent(Form<Project> projectForm) {
        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        };
        projectForm.add(cancelButton);
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

    }


}
