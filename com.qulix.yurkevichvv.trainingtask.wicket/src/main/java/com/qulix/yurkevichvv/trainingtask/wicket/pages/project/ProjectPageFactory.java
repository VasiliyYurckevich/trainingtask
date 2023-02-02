package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.Session;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования проекта.
 *
 * @author Q-YVV
 */
public class ProjectPageFactory implements AbstractEntityPageFactory<ProjectTemporaryData> {

    @Override
    public AbstractEntityPage<ProjectTemporaryData> createPage(CompoundPropertyModel<ProjectTemporaryData> entityModel) {
        return new ProjectPage(CompoundPropertyModel.of(entityModel.getObject()));
    }
}
