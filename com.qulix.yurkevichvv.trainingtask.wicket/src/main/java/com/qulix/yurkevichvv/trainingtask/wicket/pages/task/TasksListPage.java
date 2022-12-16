package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.ITaskTableColumns;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.models.TaskListLoadableDetachableModel;
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
     * Реализует CustomListView для задач.
     *
     * @author Q-YVV
     */
    private static class TaskCustomListView extends CustomListView<Task> {

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
            ITaskTableColumns.addColumns(item);
        }

    }

    private class AddTaskLink extends Link<WebPage> {
        public AddTaskLink() {
            super("addTask");
        }

        @Override
        public void onClick() {
            setResponsePage(new NewTaskPage(CompoundPropertyModel.of(new Task())));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            this.setEnabled(!new ProjectService().findAll().isEmpty());
        }
    }

    private class NewTaskPage extends TaskPage {
        public NewTaskPage(CompoundPropertyModel<Task> taskModel) {
            super(taskModel);
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
