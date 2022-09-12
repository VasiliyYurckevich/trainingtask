package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.api.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditInProject;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends BasePage {

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
    public static final String PAGE_TITLE = "pageTitle";


    /**
     * Идентификатор поля сотрудника.
     */
    public static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Идентификатор формы проекта.
     */
    public static final String PROJECT_FORM = "projectForm";

    /**
     * Сообщение ошибки транзакции.
     */
    public static final String TRANSACTION_ERROR_MESSAGE = "Exception trying create transaction";

    /**
     * Список задач проекта.
     */
    private List<Task> tasks;

    /**
     * Переменные доступа к методам классов DAO.
     */
    private ProjectDao projectDao = new ProjectDao();
    private TaskDao taskDao  = new TaskDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectPage.class.getName());

    /**
     * Конструктор.
     */
    public ProjectPage(List<Task> tasks) {
        get(PAGE_TITLE).setDefaultModelObject("Добавить проект");
        Project project = new Project();
        this.tasks = tasks;
        Form<Project> form = new Form<>(PROJECT_FORM, new CompoundPropertyModel<>(project)) {
            @Override
            protected void onSubmit() {
                addProject(getModelObject());
                setResponsePage(ProjectsListPage.class);
            }
        };
        addButtons(project, tasks, form);
        addFormComponents(form);
        addTaskList(project, tasks);
        add(form);
    }

    /**
     * Конструктор.
     *
     * @param project редактируемый проект
     * @param tasks список задач проекта
     */
    public ProjectPage(Project project, List<Task> tasks) {
        get(PAGE_TITLE).setDefaultModelObject("Редактировать проект");
        this.tasks = tasks;
        Form<Project> form = new Form<Project>(PROJECT_FORM, new CompoundPropertyModel<>(project)) {
            @Override
            protected void onSubmit() {
                updateProject(project);
                setResponsePage(ProjectsListPage.class);
            }
        };
        addButtons(project, tasks, form);
        addFormComponents(form);
        addTaskList(project, tasks);
        add(form);
    }

    private void addButtons(Project project, List<Task> tasks, Form<Project> form) {
        Link<Void> addTaskLink = new Link<Void>("addTaskInProject") {
            @Override
            public void onClick() {
                if (project.getId() != null) {
                    setResponsePage(new TaskPage(project, tasks));
                }
                else {
                    setResponsePage(new TaskPage(tasks));

                }
            }
        };
        add(addTaskLink);
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button);
        form.setDefaultButton(button);
        form.add(new PreventSubmitOnEnterBehavior());
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
        title.add(new RangeValidator<Integer>(0,50));
        form.add(title);
        RequiredTextField<String> description = new RequiredTextField<>("description");
        description.add(new StringValidator(0,50));
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
     * @param tasks список задач
     */
    private void addTaskList(Project project, List<Task> tasks) {
        ListView<Task> taskListView = new ListView<>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                final Task task = item.getModelObject();
                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("task_title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", project.getTitle()));
                if (task.getEmployeeId() != null && task.getEmployeeId() != 0) {
                    item.add(new Label(EMPLOYEE_NAME, employeeDao.getById(task.getEmployeeId()).getFullName()));
                }
                else {
                    item.add(new Label(EMPLOYEE_NAME, " "));
                }
                item.add(new Link<Void>("deleteLink") {
                    @Override
                    public void onClick() {
                        tasks.remove(item.getIndex());
                    }
                });
                item.add(new EditInProject("editLink", item, project, tasks));
            }
        };
        add(taskListView);
    }

    private void updateProject(Project project) {
        Connection connection = ConnectionController.getConnection();
        try {
            connection.setAutoCommit(false);
            List<Integer> oldTasksId =
                taskDao.getTasksInProject(project.getId()).stream().map(Task::getId).collect(Collectors.toList());
            List<Integer> newTasksId = tasks.stream().map(Task::getId).collect(Collectors.toList());
            projectDao.update(project, connection);
            tasks.stream().filter(task -> task.getId() == null).forEach(task -> taskDao.add(task, connection));
            tasks.stream().filter(task -> task.getId() != null).forEach(task -> taskDao.update(task, connection));
            projectDao.cellIdentity(connection);
            oldTasksId.stream().filter(id -> !newTasksId.contains(id)).forEach(id -> taskDao.delete(id, connection));
            ConnectionController.commitConnection(connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            LOGGER.log(Level.SEVERE, TRANSACTION_ERROR_MESSAGE, e);
            throw new DaoException(e);
        }
    }

    private void addProject(Project project) {
        Connection connection = ConnectionController.getConnection();
        try {
            connection.setAutoCommit(false);
            projectDao.add(project, connection);
            int newProjectId = projectDao.cellIdentity(connection);
            tasks.stream().forEach(task -> {
                task.setProjectId(newProjectId);
                taskDao.add(task, connection);
            }
            );
            ConnectionController.commitConnection(connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            LOGGER.log(Level.SEVERE, TRANSACTION_ERROR_MESSAGE, e);
            throw new DaoException(e);
        }
    }
}
