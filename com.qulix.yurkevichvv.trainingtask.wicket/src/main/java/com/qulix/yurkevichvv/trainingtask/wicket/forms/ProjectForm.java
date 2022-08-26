package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProjectForm extends Form<Project> {

    public ProjectForm(String id) {
        super(id, new CompoundPropertyModel<>(new Project()));
    }

    public ProjectForm(String id, Project project) {
        super(id, new CompoundPropertyModel<>(project));
    }

    @Override
    protected void onSubmit() {
    }

}
