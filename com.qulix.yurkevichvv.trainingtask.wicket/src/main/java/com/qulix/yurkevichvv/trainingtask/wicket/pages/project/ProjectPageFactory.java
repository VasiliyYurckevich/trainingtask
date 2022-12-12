package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.model.CompoundPropertyModel;


import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования проекта.
 *
 * @author Q-YVV
 */
public class ProjectPageFactory implements AbstractEntityPageFactory<Project> {

    @Override
    public AbstractEntityPage createPage(CompoundPropertyModel<Project> entityModel) {
        return new ProjectPage(entityModel);
    }
}
