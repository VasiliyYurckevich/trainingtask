package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.io.Serializable;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.EditTaskPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.ITaskTableColumns;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;

/**
 * Реализует CustomListView для задач проекта.
 *
 * @author Q-YVV
 */
class TasksInProjectListView extends ListView<Task> {

    /**
     * Модель проекта, связанного с задачами.
     */
    private final CompoundPropertyModel<ProjectTemporaryData> projectModel;

    /**
     * Сервис для работы с проектом.
     */
    private final ProjectTemporaryService service;

    /**
     * Конструктор.
     *
     * @param tasks        модель списка задач
     * @param service      сервис для работ с проектом
     */
    public TasksInProjectListView(LoadableDetachableModel<List<Task>> tasks, ProjectTemporaryService service) {

        super("tasks", tasks);
        this.service = service;
        this.projectModel = CompoundPropertyModel.of(new ProjectTemporaryData(new Project()));
        setReuseItems(true);
    }


    @Override
    protected void populateItem(ListItem<Task> item) {
        ITaskTableColumns.addColumns(item);

        item.add(new DeleteInProjectLink(item.getModel()))
                .add(new EditInProjectLink(CompoundPropertyModel.of(item.getModel()), projectModel));
    }

    /**
     * Реализует DeleteLink для задач проекта.
     *
     * @author Q-YVV
     */
    private class DeleteInProjectLink extends Button {

        /**
         * Модель задачи.
         */
        private final IModel<Task> taskModel;

        public DeleteInProjectLink(IModel<Task> taskModel) {
            super("deleteLink");
            this.taskModel = taskModel;
        }

        @Override
        public void onSubmit() {
            service.deleteTask(projectModel.getObject(), taskModel.getObject());
        }
    }

    /**
     * Страница редактирования задачи проекта.
     *
     * @author Q-YVV
     */
//    protected static class EditProjectTaskPage extends AbstractTaskPage {
//
//        /**
//         * Индекс в списке задач проекта.
//         */
//        private final Integer index;
//
//        /**
//         * Модель проекта.
//         */
//        private final CompoundPropertyModel<ProjectTemporaryData> projectModel;
//
//        /**
//         * Сервис для работы с проектом.
//         */
//        private final ProjectTemporaryService service = new ProjectTemporaryService();
//
//        /**
//         * Конструктор.
//         *
//         * @param taskModel    модель задачи
//         * @param projectModel модель проекта
//         */
//        public EditProjectTaskPage(CompoundPropertyModel<Task> taskModel,
//            CompoundPropertyModel<ProjectTemporaryData> projectModel) {
//
//            super(taskModel);
//            this.projectModel = projectModel;
//            this.index = projectModel.getObject().getTasksList().indexOf(taskModel.getObject());
//
//        }
//
//        @Override
//        protected void onSubmitting() {
//            service.updateTask(projectModel.getObject(), index, super.entityModel.getObject());
//        }
//
//        @Override
//        protected void onChangesSubmitted() {
//            setResponsePage(new ProjectPage());
//        }
//
//        @Override
//        protected boolean changeProjectOption() {
//            return false;
//        }
//    }

    /**
     * Ссылка для редактирования задачи в проекте.
     */
    private static class EditInProjectLink extends Link<Task> {

        /**
         * Модель задачи.
         */
        private final CompoundPropertyModel<Task> taskModel;

        /**
         * Модель проекта.
         */
        private final CompoundPropertyModel<ProjectTemporaryData> projectModel;

        /**
         * Конструктор.
         *
         * @param taskModel    модель задачи
         * @param projectModel модель проекта
         */
        public EditInProjectLink(CompoundPropertyModel<Task> taskModel,
            CompoundPropertyModel<ProjectTemporaryData> projectModel) {

            super("editLink");
            this.taskModel = taskModel;
            this.projectModel = projectModel;
        }

        @Override
        public void onClick() {
            setResponsePage(new ProjectTaskPageFactory().createPage(CompoundPropertyModel.of(taskModel), projectModel));
        }
    }

    private static class ProjectTaskPageFactory implements Serializable {

        public AbstractEntityPage createPage(CompoundPropertyModel<Task> taskModel,
            CompoundPropertyModel<ProjectTemporaryData> propertyModel) {

            return new EditTaskPage(taskModel);
        }
    }
}
