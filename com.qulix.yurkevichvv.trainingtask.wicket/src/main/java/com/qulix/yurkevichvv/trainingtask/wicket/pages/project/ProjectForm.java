package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;

/**
 * Форма проекта.
 *
 * @author Q-YVV
 */
class ProjectForm extends AbstractEntityForm<ProjectTemporaryData> {

    /**
     * Сервис для работы с {@link ProjectTemporaryData}.
     */
    private final ProjectTemporaryService service = new ProjectTemporaryService();

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param entityModel модель {@link ProjectTemporaryData}
     */
    public ProjectForm(String id, CompoundPropertyModel<ProjectTemporaryData> entityModel) {
        super(id, entityModel);
    }

    @Override
    public final void onSubmitting() {
        service.save(getModelObject());
    }

    @Override
    public final void onChangesSubmitted() {
        setResponsePage(ProjectsListPage.class);
    }
}
