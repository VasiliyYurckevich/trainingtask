package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import static com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage.addFormComponents;

/**
 * Страница редактирования проекта.
 *
 * @author Q-YVV
 */
public class EditProjectPage extends BasePage {

    /**
     * Идентификатор поля сотрудника.
     */
    public static final String EMPLOYEE_NAME = "employeeName";
    /**
     * Проект.
     */
    private final Project project;

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
    private static final Logger LOGGER = Logger.getLogger(EditProjectPage.class.getName());

    /**
     * Конструктор.
     *
     * @param project редактируемый проект
     * @param tasks список задач проекта
     */
    public EditProjectPage(Project project, List<Task> tasks) {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        this.project = project;
        this.tasks = tasks;
        Form<Project> projectForm = new Form<Project>("projectForm", new CompoundPropertyModel<>(project)) {
            @Override
            protected void onSubmit() {
                updateProject();
                setResponsePage(ProjectsListPage.class);
            }
        };
        Link<Void> addTaskLink = new Link<Void>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage(project, tasks));
            }
        };
        add(addTaskLink);
        addFormComponents(projectForm);
        addTaskList(tasks);
        add(projectForm);
    }

    /**
     * Добавляет список задач проекта.
     *
     * @param tasks список задач
     */
    private void addTaskList(List<Task> tasks) {
        ListView<Task> taskListView = new ListView<>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                final Task task = item.getModelObject();
                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", projectDao.getById(task.getProjectId()).getTitle()));
                if (task.getEmployeeId() != 0) {
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
                item.add(new Link<Void>("editLink") {
                    @Override
                    public void onClick() {
                        setResponsePage(new TaskPage(project, tasks, item.getIndex()));
                    }
                });
            }
        };
        add(taskListView);
    }

    private void updateProject() {
        Connection connection = ConnectionController.getConnection();
        try {
            connection.setAutoCommit(false);
            List<Integer> oldTasksId =
                taskDao.getTasksInProject(project.getId()).stream().map(Task::getId).collect(Collectors.toList());
            List<Integer> newTasksId = tasks.stream().map(Task::getId).collect(Collectors.toList());
            tasks.stream().filter(task -> task.getId() != null).forEach(task -> taskDao.update(task, connection));
            tasks.stream().filter(task -> task.getId() == null).forEach(task -> taskDao.add(task, connection));
            oldTasksId.stream().filter(id -> !newTasksId.contains(id)).forEach(id -> taskDao.delete(id, connection));
            projectDao.update(project, connection);
            ConnectionController.commitConnection(connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            LOGGER.log(Level.SEVERE, "Exception trying create transaction", e);
            throw new DaoException(e);
        }
    }
}

