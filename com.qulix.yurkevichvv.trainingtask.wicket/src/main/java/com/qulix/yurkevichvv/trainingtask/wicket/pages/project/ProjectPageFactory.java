package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;


import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProjectPageFactory implements AbstractEntityPageFactory<Project> {

    @Override
    public AbstractEntityPage create(CompoundPropertyModel<Project> entityModel) {
        return new ProjectPage(entityModel);
    }
}
