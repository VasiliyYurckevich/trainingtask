package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

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

    /**
     * Сервис для работы с Project.
     */
    private final ProjectTemporaryService service = new ProjectTemporaryService();

    /**
     * Конструктор.
     *
     * @param projectModel редактируемый проект
     */
    public ProjectPage(CompoundPropertyModel<ProjectTemporaryData> projectModel) {
        super("Редактировать проект", projectModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<ProjectTemporaryData> form = new ProjectForm(entityModel);
        addFormComponents(form);
        addTaskList();
        add(form);
    }

    /**
     * Генерирует страницу редактирования задачи.
     *
     * @param task задача
     * @return страницу редактирования задачи
     */
    protected TaskPage getNewTaskPage(Task task) {

        task.setProjectId(entityModel.getObject().getId());

        return new NewProjectTask(CompoundPropertyModel.of(task), entityModel);
    }

    @Override
    protected void addFormComponents(Form<ProjectTemporaryData> form) {
        Link<Void> addTaskLink = new Link<>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(getNewTaskPage(new Task()));
            }
        };
        add(addTaskLink);

        addButtons(form);

        addStringField(form, "project.title", TITLE_MAXLENGTH);
        addStringField(form, "project.description", DESCRIPTION_MAXLENGTH);
    }

    /**
     * Добавляет список задач проекта.
     */
    private void addTaskList() {
        add(new TasksInProjectListView(LoadableDetachableModel.of(() ->
            this.entityModel.getObject().getTasksList()), entityModel, service));
    }

    @Override
    protected final void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(ProjectsListPage.class);
    }

    /**
     * Страница создания задачи проекта.
     */
    private class NewProjectTask extends TaskPage {

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
        private NewProjectTask(CompoundPropertyModel<Task> taskModel, CompoundPropertyModel<ProjectTemporaryData> projectModel) {
            super(taskModel);
            this.projectModel = projectModel;
        }

        @Override
        protected void onSubmitting() {
            service.addTask(projectModel.getObject(), entityModel.getObject());
        }

        @Override
        protected void onChangesSubmitted() {
            setResponsePage(ProjectPage.this);
        }

        @Override
        protected boolean changeProjectOption() {
            return false;
        }
    }

    /**
     * Форма проекта.
     */
    private class ProjectForm extends Form<ProjectTemporaryData> {

        /**
         * Конструктор.
         */
        public ProjectForm(CompoundPropertyModel<ProjectTemporaryData> entityModel) {
            super("projectForm", entityModel);
        }

        @Override
        protected void onSubmit() {
            onSubmitting();
            onChangesSubmitted();
        }
    }
}
