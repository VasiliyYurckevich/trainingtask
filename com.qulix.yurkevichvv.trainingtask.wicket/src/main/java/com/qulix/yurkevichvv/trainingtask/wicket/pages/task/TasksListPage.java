package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;

/**
 * Страница списка задач.
 *
 * @author Q-YVV
 */
public class TasksListPage extends BasePage {

    /**
     * Идентификатор метки имени сотрудника.
     */
    public static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Переменные доступа к методам классов DAO.
     */
    private final ProjectDao projectDao = new ProjectDao();
    private final EmployeeDao employeeDao = new EmployeeDao();

    /**
     * Конструктор.
     */
    public TasksListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Задачи");
        List<Task> tasks = new TaskDao().getAll();
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
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(taskListView);
        Link<WebPage> addTask = new Link<>("addTask") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage());
            }
        };
        if (projectDao.getAll().isEmpty()) {
            addTask.setEnabled(false);
        }
        add(addTask);
    }
}
