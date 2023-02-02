package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
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
        super(entityModel, new AbstractEntityForm<Task>("", entityModel) {
            @Override
            protected void onSubmitting() {

            }

            @Override
            protected void onChangesSubmitted() {

            }
        });
    }

/*    @Override
    protected void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected void onChangesSubmitted() {
        setResponsePage(TasksListPage.class);
    }*/
}
