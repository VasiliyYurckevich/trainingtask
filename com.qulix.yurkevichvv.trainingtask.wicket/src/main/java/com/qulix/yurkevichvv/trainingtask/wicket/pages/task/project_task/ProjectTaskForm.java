package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPageFactory;

/**
 * Форма задачи связанной с определенным проектом.
 *
 * @author Q-YVV
 */
public class ProjectTaskForm extends AbstractEntityForm<Task> {

    /**
     * Сервис для выполнения бизнес-логики {@link ProjectTemporaryData}.
     */
    private final ProjectTemporaryService projectTemporaryDataService = new ProjectTemporaryService();

    /**
     * Модель {@link ProjectTemporaryData}.
     */
    private final CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel;

    /**
     * Индекс задачи в списке задач.
     */
    private final int index;

    /**
     * Конструктор.
     *  @param id идентификатор
     * @param entityModel модель сущности {@link Task}
     * @param projectTemporaryDataModel модель данных проекта задачи
     */
    public ProjectTaskForm(String id, CompoundPropertyModel<Task> entityModel,
        CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel) {

        super(id, entityModel);
        this.projectTemporaryDataModel = projectTemporaryDataModel;
        this.index = projectTemporaryDataModel.getObject().getTasksList().indexOf(getModelObject());
    }


    @Override
    public void onSubmitting() {
        if (index < 0) {
            projectTemporaryDataService.addTask(projectTemporaryDataModel.getObject(), getModelObject());
        }
        else {
            projectTemporaryDataService.updateTask(projectTemporaryDataModel.getObject(), index, getModelObject());
        }
    }

    @Override
    public void onChangesSubmitted() {
        setResponsePage(new ProjectPageFactory().createPageByWrappedData(projectTemporaryDataModel));
    }
}
