package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends AbstractEntityPage<Project> {

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
    private final ProjectService service = new ProjectService();

    /**
     * Конструктор.
     *
     * @param projectModel редактируемый проект
     */
    public ProjectPage(CompoundPropertyModel<Project> projectModel) {
        super(projectModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        get("pageTitle").setDefaultModelObject("Редактировать проект");

        Form<Project> form = new ProjectForm();
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
    protected void addFormComponents(Form<Project> form) {
        Link<Void> addTaskLink = new Link<>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(getNewTaskPage(new Task()));
            }
        };
        add(addTaskLink);

        addButtons(form);

        addStringField(form, "title", TITLE_MAXLENGTH);
        addStringField(form, "description", DESCRIPTION_MAXLENGTH);
    }

    /**
     * Добавляет список задач проекта.
     */
    private void addTaskList() {
        ListView<Task> taskListView = new TasksInProjectListView(LoadableDetachableModel.of(() ->
            this.entityModel.getObject().getTasksList()), entityModel, service);
        add(taskListView);
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
        private CompoundPropertyModel<Project>  projectModel;

        /**
         * Конструктор.
         *
         * @param taskModel модель задачи
         * @param projectModel модель проекта
         */
        private NewProjectTask(CompoundPropertyModel<Task> taskModel, CompoundPropertyModel<Project> projectModel) {
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
    private class ProjectForm extends Form<Project> {

        /**
         * Конструктор.
         */
        public ProjectForm() {
            super("projectForm", ProjectPage.this.entityModel);
        }

        @Override
        protected void onSubmit() {
            onSubmitting();
            onChangesSubmitted();
        }
    }
}
