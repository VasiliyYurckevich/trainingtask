package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPageFactory;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProjectTaskForm extends AbstractEntityForm<Task> {

    private final ProjectTemporaryService projectTemporaryDataService = new ProjectTemporaryService();

    private final CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel;

    private final int index;


    /**
     * Конструктор.
     *  @param id идентификатор
     * @param entityModel модель сущности {@link Task}
     * @param projectTemporaryDataModel
     * @param index
     */
    public ProjectTaskForm(String id, CompoundPropertyModel<Task> entityModel,
                           CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel, int index) {

        super(id, entityModel);
        this.projectTemporaryDataModel = projectTemporaryDataModel;
        this.index = projectTemporaryDataModel.getObject().getTasksList().indexOf(getModelObject());

    }

    @Override
    protected void onSubmitting() {
        if (index < 0){
            projectTemporaryDataService.addTask(projectTemporaryDataModel.getObject(), getModelObject());
        }
        else {
            projectTemporaryDataService.updateTask(projectTemporaryDataModel.getObject(), index, getModelObject());
        }
    }

    @Override
    protected void onChangesSubmitted() {
        setResponsePage(new ProjectPageFactory().createPage(projectTemporaryDataModel));
    }
}
