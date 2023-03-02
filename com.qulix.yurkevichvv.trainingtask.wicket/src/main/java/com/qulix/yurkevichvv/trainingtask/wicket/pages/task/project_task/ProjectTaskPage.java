package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import java.util.List;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
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
     * Модель {@link ProjectTemporaryData}.
     */
    private final CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel;

    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     */
    public ProjectTaskPage(CompoundPropertyModel<Task> taskModel,
        CompoundPropertyModel<ProjectTemporaryData> projectTemporaryDataModel) {

        super(taskModel, new ProjectTaskForm("taskForm", taskModel, projectTemporaryDataModel));
        this.projectTemporaryDataModel = projectTemporaryDataModel;
    }

    @Override
    protected boolean changeProjectOption() {
        return false;
    }

    @Override
    protected LoadableDetachableModel<List<Project>> getProjectListLoadableDetachableModel() {
        return LoadableDetachableModel.of(
            () -> ProjectListService.getProjectListWithNew(projectTemporaryDataModel.getObject().getProject()));
    }

    @Override
    protected SubmitButton getSubmitButton() {
        return new SaveProjectTaskButton("submit");
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
