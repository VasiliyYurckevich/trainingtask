package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования задачи.
 *
 * @author Q-YVV
 */
public class TaskPageFactory implements AbstractEntityPageFactory<Task> {
    @Override
    public AbstractEntityPage<Task> createPage(CompoundPropertyModel<Task> entityModel) {
        return new EditTaskPage(entityModel);
    }


    private static class EditTaskPage extends TaskPage {

        /**
         * Сервис для работы с Task.
         */
        protected TaskService taskService = new TaskService();

        public EditTaskPage(CompoundPropertyModel<Task> entityModel) {
            super(entityModel);
        }

        @Override
        protected void onSubmitting() {
            this.modelChanged();
            taskService.save(entityModel.getObject());
        }

        @Override
        protected void onChangesSubmitted() {
            setResponsePage(TasksListPage.class);
        }

        @Override
        protected boolean changeProjectOption() {
            return true;
        }
    }

}
