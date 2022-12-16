package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.models.TaskListLoadableDetachableModel;
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

        Form<Project> form = new Form<>("projectForm", entityModel) {
            @Override
            protected void onSubmit() {
                onSubmitting();
                onChangesSubmitted();
            }
        };
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

        return new NewTaskInProject(CompoundPropertyModel.of(task), entityModel.getObject());
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

        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button)
            .add(new PreventSubmitOnEnterBehavior());
        form.setDefaultButton(button);

        Link<Void> cancelButton = new Link<>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        };
        form.add(cancelButton);

        addStringField(form, "title", TITLE_MAXLENGTH);
        addStringField(form, "description", DESCRIPTION_MAXLENGTH);
    }

    /**
     * Добавляет список задач проекта.
     */
    private void addTaskList() {
        LoadableDetachableModel<List<Task>> tasks = new TaskListLoadableDetachableModel();

        ListView<Task> taskListView = new TasksInProjectListView(tasks, entityModel, service);
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

    private class NewTaskInProject extends TaskPage {

        private Project project;

        public NewTaskInProject(CompoundPropertyModel<Task> task, Project project) {
            super(task);
            this.project = project;
        }

        @Override
        protected void onSubmitting() {
            service.addTask(project, entityModel.getObject());
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
}
