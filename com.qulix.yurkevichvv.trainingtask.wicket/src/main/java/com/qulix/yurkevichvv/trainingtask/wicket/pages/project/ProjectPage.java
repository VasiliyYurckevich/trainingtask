package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;

import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.List;

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
