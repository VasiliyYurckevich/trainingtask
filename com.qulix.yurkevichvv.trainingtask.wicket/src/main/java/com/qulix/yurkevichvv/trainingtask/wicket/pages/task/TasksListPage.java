package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;

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
     * Сервис для работы с Task.
     */
    private final TaskService service = new TaskService();

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
                this.setEnabled(!new ProjectService().findAll().isEmpty());
            }
        };
        add(addTask);
        LoadableDetachableModel<List<Task>> tasks = new LoadableDetachableModel<>() {
            @Override
            protected List<Task> load() {
                return service.findAll();
            }
        };

        CustomListView<Task> taskListView = new TaskCustomListView(tasks, service);
        add(taskListView);
    }

    private TaskPage getTaskPage(Task task) {

        return new TaskPage(new Model<>(task)) {
            @Override
            protected void onSubmitting() {
                taskService.save(getTaskModel().getObject());
            }

            @Override
            protected void onChangesSubmitted() {
                setResponsePage(TasksListPage.this);
            }
        };
    }

    private class TaskCustomListView extends CustomListView<Task> {

        public TaskCustomListView(LoadableDetachableModel<List<Task>> tasks, IService service) {
            super("tasks", tasks, service);
        }

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

        @Override
        public AbstractEntityPage getNewPage(ListItem<Task> item) {
            return TasksListPage.this.getTaskPage(item.getModelObject());
        }
    }
}
