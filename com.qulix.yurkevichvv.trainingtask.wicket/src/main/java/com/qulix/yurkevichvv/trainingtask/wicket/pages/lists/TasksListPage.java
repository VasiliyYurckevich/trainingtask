package com.qulix.yurkevichvv.trainingtask.wicket.pages.lists;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;

/**
 * Страница списка задач.
 *
 * @author Q-YVV
 */
public class TasksListPage extends BasePage {

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
                final ProjectDao projectDao = new ProjectDao();
                final EmployeeDao employeeDao = new EmployeeDao();
                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", projectDao.getById(task.getProjectId()).getTitle()));
                if (task.getEmployeeId() != 0) {
                    item.add(new Label("employeeName", employeeDao.getById(task.getEmployeeId()).getFullName()));
                } else {
                    item.add(new Label("employeeName", " "));

                }
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(taskListView);
        add(new Link<WebPage>("addTask") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage());
            }
        });
    }
}
