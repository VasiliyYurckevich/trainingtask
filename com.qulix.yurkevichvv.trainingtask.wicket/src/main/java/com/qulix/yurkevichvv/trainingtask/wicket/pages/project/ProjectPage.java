package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
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
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task.ProjectTaskPageFactory;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends AbstractEntityPage<ProjectTemporaryData> {

    /**
     * Максимальная длинна ввода поля наименования.
     */
    private static final int TITLE_MAXLENGTH = 50;

    /**
     * Максимальная длинна ввода поля описания.
     */
    private static final int DESCRIPTION_MAXLENGTH = 250;

    private final ProjectTaskPageFactory pageFactory = new ProjectTaskPageFactory(getEntityModel());

    /**
     * Конструктор.
     */
    public ProjectPage(CompoundPropertyModel<ProjectTemporaryData> projectModel) {
        super("Редактировать проект", projectModel, new ProjectForm("projectForm", projectModel));
    }

    @Override
    protected void addFormComponents() {
        addButtons();

        addStringField("title", TITLE_MAXLENGTH);
        addStringField("description", DESCRIPTION_MAXLENGTH);

        Task task = new Task();
        task.setProject(getForm().getModelObject().getProject());
        getForm().add(new EditProjectTaskButton("addTaskLink",
            CompoundPropertyModel.of(task), pageFactory));
        addTaskList();

        super.addFormComponents();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
    }

    /**
     * Добавляет список задач проекта.
     */
    private void addTaskList() {
        getForm().add(new ProjectTasksListView(LoadableDetachableModel.of(() ->
            getForm().getModelObject().getTasksList()), new ProjectTemporaryService(), pageFactory, getEntityModel()));
    }

    /**
     * Реализует CustomListView для задач проекта.
     *
     * @author Q-YVV
     */
    static class ProjectTasksListView extends ListView<Task> {

        /**
         * Модель проекта, связанного с задачами.
         */
        private final CompoundPropertyModel<ProjectTemporaryData> projectModel;

        /**
         * Сервис для работы с проектом.
         */
        private final ProjectTemporaryService service;

        /**
         * Фабрика дял генерации страницы сущности.
         */
        private final AbstractEntityPageFactory<Task> pageFactory;

        /**
         * Конструктор.
         *
         * @param tasks   модель списка задач
         * @param service сервис для работ с проектом
         */
        public ProjectTasksListView(LoadableDetachableModel<List<Task>> tasks, ProjectTemporaryService service,
            AbstractEntityPageFactory<Task> pageFactory, CompoundPropertyModel<ProjectTemporaryData> projectModel) {

            super("tasks", tasks);
            this.service = service;
            this.pageFactory = pageFactory;
            this.projectModel = projectModel;
        }

        @Override
        protected void populateItem(ListItem<Task> item) {
            ITaskTableColumns.addColumns(item);

            item.add(new DeleteProjectTaskButton("deleteLink", item.getModel()))
                .add(new EditProjectTaskButton("editLink", CompoundPropertyModel.of(item.getModel()), pageFactory));
        }

        /**
         * Реализует DeleteLink для задач проекта.
         *
         * @author Q-YVV
         */
        private class DeleteProjectTaskButton extends Button {

            /**
             * Модель {@link Task}.
             */
            private final IModel<Task> taskModel;

            /**
             * Конструктор.
             *
             * @param id        идентификатор
             * @param taskModel модель {@link Task}
             */
            public DeleteProjectTaskButton(String id, IModel<Task> taskModel) {
                super(id);
                this.taskModel = taskModel;
            }

            @Override
            public void onSubmit() {
                service.deleteTask(projectModel.getObject(), taskModel.getObject());
                getForm().detachModels();
            }
        }
    }

    /**
     * Ссылка для редактирования задачи в проекте.
     */
    private static class EditProjectTaskButton extends Button {

        /**
         * Модель задачи.
         */
        private final CompoundPropertyModel<Task> taskModel;

        /**
         * Фабрика для генерации страницы редактирования задачи проекта.
         */
        private final AbstractEntityPageFactory<Task> pageFactory;

        /**
         * Конструктор.
         *
         * @param taskModel модель задачи
         * @param pageFactory фабрика для генерации страницы редактирования задачи проекта
         */
        public EditProjectTaskButton(String id, CompoundPropertyModel<Task> taskModel,
            AbstractEntityPageFactory<Task> pageFactory) {

            super(id);
            this.taskModel = taskModel;
            this.pageFactory = pageFactory;
        }

        @Override
        public void onSubmit() {
            setResponsePage(pageFactory.createPage(taskModel));
        }
    }
}
