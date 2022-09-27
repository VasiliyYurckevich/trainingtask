package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;


import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends AbstractEntityPage<Project>{

    /**
     * Максимальная длинна ввода поля наименования.
     */
    public static final int TITLE_MAXLENGTH = 50;
    /**
     * Максимальная длинна ввода поля описания.
     */
    public static final int DESCRIPTION_MAXLENGTH = 250;

    /**
     * Идентификатор элемента названия страницы.
     */
    private static final String PAGE_TITLE = "pageTitle";


    /**
     * Идентификатор поля сотрудника.
     */
    private static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Идентификатор формы проекта.
     */
    private static final String PROJECT_FORM = "projectForm";

    private Project project;

    private ProjectService service = new ProjectService();

    /**
     * Список задач проекта.
     */

    /**
     * Переменные доступа к методам классов DAO.
     */
    private ProjectDao projectDao = new ProjectDao();

    private TaskDao taskDao  = new TaskDao();

    private EmployeeDao employeeDao = new EmployeeDao();

    /**
     * Конструктор.
     *
     * @param project редактируемый проект
     */
    public ProjectPage(Project project) {
        super();
        this.project = project;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get(PAGE_TITLE).setDefaultModelObject("Редактировать проект");
        Form<Project> form = new Form<Project>(PROJECT_FORM, new CompoundPropertyModel<>(project)) {
            @Override
            protected void onSubmit() {

            }
        };
        addButtons(form);
        addFormComponents(form);
        addTaskList(project);
        add(form);
    }



    private void addButtons(Form<Project> form) {

        TaskPage taskPage = getNewTaskPage();
        Link<Void> addTaskLink = new Link<Void>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(taskPage);
            }
        };
        add(addTaskLink);
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button);
        form.setDefaultButton(button);
        form.add(new PreventSubmitOnEnterBehavior());
    }

    private TaskPage getNewTaskPage() {
        TaskPage taskPage = new TaskPage(new Task()){
            @Override
            protected void onSubmitting() {
                service.addTask(project,getTask());
            }

            @Override
            protected void onChangesSubmitted() {
                setResponsePage(this);
            }
        };
        return taskPage;
    }

    /**
     * Добавляет компоненты в форму проекта.
     *
     * @param form форма для добавления
     */
    protected static void addFormComponents(Form<Project> form) {
        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        };
        form.add(cancelButton);

        RequiredTextField<String> title = new RequiredTextField<>("title");
        title.add(new CustomStringValidator(TITLE_MAXLENGTH));
        form.add(title);

        RequiredTextField<String> description = new RequiredTextField<>("description");
        description.add(new CustomStringValidator(DESCRIPTION_MAXLENGTH));
        form.add(description);

        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel",
            new ComponentFeedbackMessageFilter(title));
        form.add(titleFeedbackPanel);

        CustomFeedbackPanel descriptionFeedbackPanel = new CustomFeedbackPanel("descriptionFeedbackPanel",
            new ComponentFeedbackMessageFilter(description));
        form.add(descriptionFeedbackPanel);

    }

    /**
     * Добавляет список задач проекта.
     *
     * */
    private void addTaskList(Project project) {
        LoadableDetachableModel<List<Task>> tasks = new LoadableDetachableModel<List<Task>>() {
            @Override
            protected List<Task> load() {
                return project.getTasksList();
            }
        };

        Task task = new Task();

        ListView<Task> taskListView = new TaskListView(tasks, project);
        add(taskListView);
    }

    @Override
    public void setEntity(Project project) {
        this.project = project;
    }

    @Override
    protected void onSubmitting() {
        service.save(project);
    }

    @Override
    protected void onChangesSubmitted() {
        setResponsePage(ProjectsListPage.class);
    }

    private class TaskListView extends ListView<Task> {
        private final Project project;

        public TaskListView(LoadableDetachableModel<List<Task>> tasks, Project project) {
            super("tasks", tasks);
            this.project = project;
        }

        @Override
        protected void populateItem(ListItem<Task> item) {
            final Task task = item.getModelObject();

            TaskPage taskPage = new TaskPage(task){
                @Override
                protected void onSubmitting() {
                    service.updateTask(project,item.getIndex(),getTask());
                }

                @Override
                protected void onChangesSubmitted() {
                    setResponsePage(this);
                }
            };

            item.add(new Label("status", task.getStatus().getStatusTitle()))
                .add(new Label("task_title", task.getTitle()))
                .add(new Label("workTime", task.getWorkTime()))
                .add(new Label("beginDate", task.getBeginDate().toString()))
                .add(new Label("endDate", task.getEndDate().toString()))
                .add(new Label("projectTitle", project.getTitle()))
                .add(new Label(EMPLOYEE_NAME,
                    task.getEmployeeId() != 0 ? new EmployeeDao().getById(task.getEmployeeId()).getFullName() : ""))
                .add(new Link<Void>("deleteLink") {
                    @Override
                    public void onClick() {
                        service.deleteTask(project,task);
                    }
                })
                .add(new EditLink<Task>("edit", taskPage,task));
        }
    }
}
