package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.ProjectForm;
import com.qulix.yurkevichvv.trainingtask.wicket.links.AddInProjectLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.tables.TaskTablePanel;
import org.apache.wicket.markup.html.form.RequiredTextField;

import java.util.List;

public class EditProjectPage extends BasePage {

    private ProjectDao projectDao= new ProjectDao();

    public EditProjectPage(Project project, List<Task> tasks) {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        ProjectForm projectForm = new ProjectForm("addProjectForm", project){
            @Override
            protected void onSubmit() {
                projectDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        final AddInProjectLink addTaskLink = new AddInProjectLink("addTaskInProject", project, tasks);
        projectForm.add(addTaskLink);
        RequiredTextField<String> title = new RequiredTextField<>("title");
        projectForm.add(title);
        RequiredTextField<String> description = new RequiredTextField<>("description");
        projectForm.add(description);
        TaskTablePanel taskTablePanel = new TaskTablePanel("tasks", tasks);
        projectForm.add(taskTablePanel);
        add(projectForm);
    }
}
