package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends BasePage {

    /**
     * Максимальная длинна ввода поля наименования.
     */
    public static final int TITLE_MAXLENGTH = 50;
    /**
     * Максимальная длинна ввода поля описания.
     */
    public static final int DESCRIPTION_MAXLENGTH = 250;

    /**
     * Конструктор.
     */
    public ProjectPage() {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        Form<Project> projectForm = new Form<>("projectForm", new CompoundPropertyModel<>(new Project())) {
            @Override
            protected void onSubmit() {
                ProjectDao projectDao = new ProjectDao();
                projectDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        addFormComponents(projectForm);
        add(projectForm);
    }

    /**
     * Добавляет компаненты в форму проекта.
     *
     * @param form форма для добавления
     */
    protected static void addFormComponents(Form<Project> form) {
        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        };
        form.add(cancelButton);
        RequiredTextField<String> title = new RequiredTextField<>("title");
        title.add(new CustomStringValidator(TITLE_MAXLENGTH));
        form.add(title);
        RequiredTextField<String> description = new RequiredTextField<>("description");
        description.add(new CustomStringValidator(DESCRIPTION_MAXLENGTH));
        form.add(description);
        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel",
            new ComponentFeedbackMessageFilter(title));
        form.add(titleFeedbackPanel);
        CustomFeedbackPanel descriptionFeedbackPanel = new CustomFeedbackPanel("descriptionFeedbackPanel",
            new ComponentFeedbackMessageFilter(description));
        form.add(descriptionFeedbackPanel);

    }


}
