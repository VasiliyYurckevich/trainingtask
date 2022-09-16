package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
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
import org.apache.wicket.model.LoadableDetachableModel;

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
     * Конструктор.
     */
    public TasksListPage() {
        super();
    }

    @Override
    protected void onInitialize() {
            super.onInitialize();
        get("pageTitle").setDefaultModelObject("Задачи");

        Link<WebPage> addTask = new Link<>("addTask") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage());
            }

            @Override
            protected void onConfigure(){
                super.onConfigure();
                this.setEnabled(!new ProjectDao().getAll().isEmpty());
            }
        };
        add(addTask);

        LoadableDetachableModel<List<Task>> tasks = new LoadableDetachableModel<>() {
            @Override
            protected List<Task> load() {
                return new TaskDao().getAll();
            }
        };
        
        ListView<Task> taskListView = new ListView<>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                final Task task = item.getModelObject();

                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", new ProjectDao().getById(task.getProjectId()).getTitle()));
                item.add(new Label(EMPLOYEE_NAME,
                    task.getEmployeeId() != 0 ? new EmployeeDao().getById(task.getEmployeeId()).getFullName():""));
                item.add(new DeleteLink("deleteLink", item.getModelObject()));
                item.add(new EditLink("editLink", item.getModelObject()));
            }
        };
        add(taskListView);
    }
}
