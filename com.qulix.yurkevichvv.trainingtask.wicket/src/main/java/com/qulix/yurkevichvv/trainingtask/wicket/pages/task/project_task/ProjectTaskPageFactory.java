package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Генерирует страницу редактирования {@link Task}, связанной с {@link ProjectTemporaryData}.
 *
 * @author Q-YVV
 */
public class ProjectTaskPageFactory implements AbstractEntityPageFactory<Task> {

    /**
     *  Модель {@link ProjectTemporaryData}
     */
    private final CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel;

    /**
     * Конструктор.
     *
     * @param projectTemporaryDataModel модель данных проекта
     */
    public ProjectTaskPageFactory(CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel) {
        this.projectTemporaryDataModel = projectTemporaryDataModel;
    }

    @Override
    public AbstractEntityPage<?> createPage(CompoundPropertyModel<Task> entityModel) {
        return new ProjectTaskPage(entityModel,projectTemporaryDataModel);
    }
}
