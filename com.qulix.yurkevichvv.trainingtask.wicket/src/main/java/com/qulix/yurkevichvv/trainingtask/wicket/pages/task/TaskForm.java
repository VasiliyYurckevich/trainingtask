package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Форма редактирования задачи.
 *
 * @author Q-YVV
 */
class TaskForm extends AbstractEntityForm<Task> {

    /**
     * Сервис для работы с {@link Task}.
     */
    private final TaskService service = new TaskService();

    /**
     * Конструктор.
     *
     * @param id          идентификатор
     * @param entityModel модель {@link Task}
     */
    public TaskForm(String id, CompoundPropertyModel<Task> entityModel) {
        super(id, entityModel);
    }

    @Override
    protected void onSubmitting() {
        service.save(getModelObject());
    }

    @Override
    protected void onChangesSubmitted() {
        setResponsePage(TasksListPage.class);
    }
}
