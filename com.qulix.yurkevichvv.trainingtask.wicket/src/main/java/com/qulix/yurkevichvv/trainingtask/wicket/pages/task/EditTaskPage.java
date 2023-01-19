package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;

/**
 * Страница редактирования задачи.
 *
 * @author Q-YVV
 */
public class EditTaskPage extends TaskPage {

    /**
     * Сервис для работы с задачами.
     */
    private final TaskService service = new TaskService();

    /**
     * Конструктор.
     *
     * @param entityModel модель задачи
     */
    public EditTaskPage(CompoundPropertyModel<Task> entityModel) {
        super(entityModel);
    }

    @Override
    protected void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected void onChangesSubmitted() {
        setResponsePage(TasksListPage.class);
    }
}
