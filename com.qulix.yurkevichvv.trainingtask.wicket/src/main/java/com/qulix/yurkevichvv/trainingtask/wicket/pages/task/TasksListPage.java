package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractListPage;

/**
 * Страница списка задач.
 *
 * @author Q-YVV
 */
public class TasksListPage extends AbstractListPage<Task> {

    /**
     * Конструктор.
     */
    public TasksListPage() {
        super(new TaskPageFactory(), new TaskService());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject("Задачи");

        Link<WebPage> addTask = new AddTaskLink();
        add(addTask);

        LoadableDetachableModel<List<Task>> tasks = new TaskListLoadableDetachableModel();
        CustomListView<Task> taskListView = new TaskCustomListView(tasks, service);
        add(taskListView);
    }

    /**
     * Возвращает страницу редактирования задачи.
     *
     * @param task задача
     * @return экземпляр TaskPage дял данной задачи
     */
    private TaskPage getTaskPage(CompoundPropertyModel<Task> task) {
        return new NewTaskPage(task);
    }

    /**
     * Реализует CustomListView для задач.
     *
     * @author Q-YVV
     */
    private class TaskCustomListView extends CustomListView<Task> {

        /**
         * Конструктор.
         *
         * @param tasks модель списка задач
         * @param service сервис для работы с сущностями
         */
        public TaskCustomListView(LoadableDetachableModel<List<Task>> tasks, IService service) {
            super("tasks", tasks, service);
        }

        @Override
        protected AbstractEntityPageFactory<Task> getPageFactory() {
            return new TaskPageFactory();
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
                .add(new Label("projectTitle", new ProjectService().getById(task.getProjectId()).getTitle()))
                .add(new Label("employeeName",
                task.getEmployeeId() != null ? new EmployeeService().getById(task.getEmployeeId()).getFullName() : ""));
        }

    }

    private class TaskListLoadableDetachableModel extends LoadableDetachableModel<List<Task>> {
        @Override
        protected List<Task> load() {
            return service.findAll();
        }
    }

    private class AddTaskLink extends Link<WebPage> {
        public AddTaskLink() {
            super("addTask");
        }

        @Override
        public void onClick() {
            setResponsePage(getTaskPage(CompoundPropertyModel.of(new Task())));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            this.setEnabled(!new ProjectService().findAll().isEmpty());
        }
    }

    private class NewTaskPage extends TaskPage {
        public NewTaskPage(CompoundPropertyModel<Task> entityModel) {
            super(entityModel);
        }

        @Override
        protected void onSubmitting() {
            taskService.save(entityModel.getObject());
        }

        @Override
        protected void onChangesSubmitted() {
            setResponsePage(TasksListPage.class);
        }
    }
}
