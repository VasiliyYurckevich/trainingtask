package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.security.KeyStore;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;

import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;

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
                setResponsePage(getTaskPage(new Task()));
            }

            @Override
            protected void onConfigure() {
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

        TaskPage taskPage = getTaskPage(new Task());
        CustomListView<Task> taskListView = new CustomListView<Task>("tasks", tasks,taskPage) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                super.populateItem(item);
                final Task task = item.getModelObject();
                item.add(new Label("status", task.getStatus().getStatusTitle()))
                    .add(new Label("title", task.getTitle()))
                    .add(new Label("workTime", task.getWorkTime()))
                    .add(new Label("beginDate", task.getBeginDate().toString()))
                    .add(new Label("endDate", task.getEndDate().toString()))
                    .add(new Label("projectTitle", new ProjectDao().getById(task.getProjectId()).getTitle()))
                    .add(new Label(EMPLOYEE_NAME,
                        task.getEmployeeId() != 0 ? new EmployeeDao().getById(task.getEmployeeId()).getFullName() : ""));
            }
        };
        add(taskListView);
    }

    private TaskPage getTaskPage(Task task) {
        TaskPage taskPage = new TaskPage(task){
            @Override
            protected void onSubmitting() {
                TaskService taskService = new TaskService();
                taskService.save(getTask());
                System.out.println("hio");
            }

            @Override
            protected void onChangesSubmitted() {
                setResponsePage(this);
            }
        };
        return taskPage;
    }

}
