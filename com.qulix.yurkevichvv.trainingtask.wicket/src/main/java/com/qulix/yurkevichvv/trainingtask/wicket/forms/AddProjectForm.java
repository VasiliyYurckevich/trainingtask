package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.ProjectsListPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class AddProjectForm extends Form<AddProjectForm> {

    private String title;
    private String description;

    private ProjectDao projectDao = new ProjectDao();

    public AddProjectForm(String id) {
        super(id);
        setModel(new CompoundPropertyModel<>(this));
        add(new RequiredTextField<String>("title"));
        add(new RequiredTextField<String>("description"));
    }

    @Override
    protected void onSubmit() {
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        projectDao.add(project, ConnectionController.getConnection());
        setResponsePage(ProjectsListPage.class);
    }

}
