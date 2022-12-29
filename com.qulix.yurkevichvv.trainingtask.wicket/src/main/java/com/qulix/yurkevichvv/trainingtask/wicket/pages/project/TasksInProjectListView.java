package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.io.Serializable;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.temporary.ProjectTemporaryService;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.ITaskTableColumns;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

/**
 * Реализует CustomListView для задач проекта.
 *
 * @author Q-YVV
 */
class TasksInProjectListView extends ListView<Task> {

    /**
     * Модель проекта, связанного с задачами.
     */
    private final CompoundPropertyModel<Project> projectModel;

    /**
     * Сервис для работы с проектом.
     */
    private final ProjectTemporaryService service;

    /**
     * Конструктор.
     *
     * @param tasks        модель списка задач
     * @param projectModel модель проекта
     * @param service      сервис для работ с проектом
     */
    public TasksInProjectListView(LoadableDetachableModel<List<Task>> tasks, CompoundPropertyModel<Project> projectModel,
                                  ProjectTemporaryService service) {

        super("tasks", tasks);
        this.projectModel = projectModel;
        this.service = service;
    }

    @Override
    protected void populateItem(ListItem<Task> item) {
        ITaskTableColumns.addColumns(item);

        item.add(new DeleteInProjectLink(item.getModel()))
            .add(new EditInProjectLink("editLink", CompoundPropertyModel.of(item.getModel()), projectModel));
    }

    /**
     * Реализует DeleteLink для задач проекта.
     *
     * @author Q-YVV
     */
    private class DeleteInProjectLink extends Link<Void> {
        /**
         * Модель задачи.
         */
        private final IModel<Task> taskModel;

        public DeleteInProjectLink(IModel<Task> taskModel) {
            super("deleteLink");
            this.taskModel = taskModel;
        }

        @Override
        public void onClick() {
            //service.deleteTask(projectModel.getObject(), taskModel.getObject());
        }
    }

    /**
     * Страница редактирования задачи проекта.
     *
     * @author Q-YVV
     */
    protected class EditProjectTaskPage extends TaskPage {

        /**
         * Индекс в списке задач проекта.
         */
        private final Integer index;

        /**
         * Модель проекта.
         */
        private CompoundPropertyModel<Project> projectModel;

        /**
         * Сервис для работы с проектом.
         */
        private ProjectTemporaryService service = new ProjectTemporaryService();

        /**
         * Конструктор.
         *
         * @param taskModel модель задачи
         * @param projectModel модель проекта
         */
        public EditProjectTaskPage(CompoundPropertyModel<Task> taskModel, CompoundPropertyModel<Project> projectModel) {
            super(taskModel);
            this.projectModel = projectModel;
            this.index = projectModel.getObject().getTasksList().indexOf(taskModel.getObject());
        }

        @Override
        protected void onSubmitting() {
            this.modelChanged();
            service.updateTask(projectModel.getObject(), index, super.entityModel.getObject());
        }

        @Override
        protected void onChangesSubmitted() {
            setResponsePage(new ProjectPage(projectModel));
        }

        @Override
        protected boolean changeProjectOption() {
            return false;
        }
    }

    /**
     * Ссылка для редактирования задачи в проекте.
     */
    private class EditInProjectLink extends Link<Task> {

        /**
         * Модель задачи.
         */
        private CompoundPropertyModel<Task> taskModel;


        /**
         * Модель проекта.
         */
        private CompoundPropertyModel<Project> projectModel;

        /**
         * Конструктор.
         *
         * @param taskModel модель задачи
         * @param projectModel модель проекта
         */
        public EditInProjectLink(String id, CompoundPropertyModel<Task> taskModel, CompoundPropertyModel<Project> projectModel) {
            super(id);
            this.taskModel = taskModel;
            this.projectModel = projectModel;
        }

        @Override
        public void onClick() {
            setResponsePage(new ProjectTaskPageFactory().createPage(CompoundPropertyModel.of(taskModel), projectModel));
        }
    }

    private class ProjectTaskPageFactory implements Serializable {

        public AbstractEntityPage createPage(CompoundPropertyModel<Task> taskModel,
            CompoundPropertyModel<Project> propertyModel) {

            return new EditProjectTaskPage(taskModel, propertyModel);
        }
    }
}
