package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AbstractTaskPage;

/**
 * Страница задачи, связанной с данными проекта.
 *
 * @author Q-YVV
 */
public class ProjectTaskPage extends AbstractTaskPage {

    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     */
    public ProjectTaskPage(CompoundPropertyModel<Task> taskModel,
        CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel) {
        super(taskModel, new ProjectTaskForm("taskForm", taskModel, projectTemporaryDataModel));
    }

    @Override
    protected boolean changeProjectOption() {
        return false;
    }

    @Override
    protected void addButtons() {
        SubmitButton button = new SaveProjectTaskButton("submit");
        getForm().add(button);
        getForm().setDefaultButton(button);

        Button cancelButton = new CancelLink("cancel");
        getForm().add(cancelButton);
    }

    /**
     * Кнопка отправки формы при сохранении задачи проекта.
     *
     * @author Q-YVV
     */
    private static class SaveProjectTaskButton extends AbstractTaskPage.SubmitButton {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         */
        public SaveProjectTaskButton(String id) {
            super(id);
        }

        @Override
        public void onSubmit() {
            AbstractEntityForm<?> form = (AbstractEntityForm<?>) getForm();
            form.onSubmitting();
            form.onChangesSubmitted();
        }
    }
}
