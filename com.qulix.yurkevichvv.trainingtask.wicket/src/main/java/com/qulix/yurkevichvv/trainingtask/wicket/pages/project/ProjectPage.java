package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends AbstractEntityPage {

    /**
     * Максимальная длинна ввода поля наименования.
     */
    private static final int TITLE_MAXLENGTH = 50;

    /**
     * Максимальная длинна ввода поля описания.
     */
    private static final int DESCRIPTION_MAXLENGTH = 250;

    /**
     * Идентификатор поля сотрудника.
     */
    private static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Идентификатор формы проекта.
     */
    private static final String PROJECT_FORM = "projectForm";

    /**
     * Сервис для работы с Project.
     */
    private ProjectService service = new ProjectService();

    /**
     * Модель проекта.
     */
    private IModel<Project> projectModel;


    /**
     * Конструктор.
     *
     * @param project редактируемый проект
     */
    public ProjectPage(IModel<Project> project) {
        super();
        this.projectModel = project;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        get("pageTitle").setDefaultModelObject("Редактировать проект");

        Form<Project> form = new Form<>(PROJECT_FORM, new CompoundPropertyModel<>(projectModel)) {
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
    protected TaskPage getTaskPage(Task task) {

        task.setProjectId(projectModel.getObject().getId());

        return new TaskPage(new Model<>(task)) {
            @Override
            protected void onSubmitting() {
                service.addTask(projectModel.getObject(), getTaskModel().getObject());
            }

            @Override
            protected void onChangesSubmitted() {
                setResponsePage(ProjectPage.this);
            }

            @Override
            protected boolean changeProjectOption() {
                return false;
            }
        };
    }

    @Override
    protected void addFormComponents(Form form) {
        Link<Void> addTaskLink = new Link<>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(getTaskPage(new Task()));
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
        LoadableDetachableModel<List<Task>> tasks = new LoadableDetachableModel<>() {
            @Override
            protected List<Task> load() {
                return service.getProjectsTasks(projectModel.getObject());
            }
        };

        ListView<Task> taskListView = new TaskListView(tasks, projectModel, service);
        add(taskListView);
    }

    @Override
    protected final void onSubmitting() {
        service.save(projectModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(ProjectsListPage.class);
    }

    /**
     * Реализует CustomListView для задач проекта.
     */
    private class TaskListView extends ListView<Task> {

        /**
         * Модель проекта, связанного с задачами.
         */
        private final IModel<Project> projectModel;

        /**
         * Сервис для работы с проектом.
         */
        private final ProjectService service;

        /**
         * Конструктор.
         *
         * @param tasks модель списка задач
         * @param projectModel модель проекта
         * @param service сервис для работ с проектом
         */
        public TaskListView(LoadableDetachableModel<List<Task>> tasks, IModel<Project> projectModel,
            ProjectService service) {

            super("tasks", tasks);
            this.projectModel = projectModel;
            this.service = service;
        }

        @Override
        protected void populateItem(ListItem<Task> item) {
            final Task task = item.getModelObject();

            item.add(new Label("status", task.getStatus().getStatusTitle()))
                .add(new Label("task_title", task.getTitle()))
                .add(new Label("workTime", task.getWorkTime()))
                .add(new Label("beginDate", task.getBeginDate().toString()))
                .add(new Label("endDate", task.getEndDate().toString()))
                .add(new Label("projectTitle", projectModel.getObject().getTitle()))
                .add(new Label(EMPLOYEE_NAME, task.getEmployeeId() != null ?
                    new EmployeeService().getById(task.getEmployeeId()).getFullName() : ""))
                .add(new Link<Void>("deleteLink") {
                    @Override
                    public void onClick() {
                        service.deleteTask(projectModel.getObject(), task);
                    }
                })
                .add(new EditLink("editLink", getTaskPage(item)));
        }

        /**
         * Генерирует страницу редактирования задачи.
         *
         * @param item задача, элемент списка
         * @return страницу редактирования задачи
         */
        private TaskPage getTaskPage(ListItem<Task> item) {
            return new TaskPage(item.getModel()) {

                @Override
                protected void onSubmitting() {
                    service.updateTask(projectModel.getObject(), item.getIndex(), getTaskModel().getObject());
                }

                @Override
                protected void onChangesSubmitted() {
                    setResponsePage(ProjectPage.this);
                }

                @Override
                protected boolean changeProjectOption() {
                    return false;
                }
            };
        }
    }
}
