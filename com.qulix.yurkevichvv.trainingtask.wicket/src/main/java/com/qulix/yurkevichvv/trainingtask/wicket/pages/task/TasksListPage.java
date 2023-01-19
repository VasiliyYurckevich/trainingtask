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
        super("Задачи", new TaskPageFactory(), new TaskService());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Link<WebPage> addTask = new AddTaskLink("addTask");
        add(addTask);

        CustomListView<Task> taskListView =
            new TaskCustomListView(LoadableDetachableModel.of(() -> new TaskService().findAll()), service);
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

    /**
     * Ссылка добавления задачи.
     *
     * @author Q-YVV
     */
    private class AddTaskLink extends Link<WebPage> {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         */
        public AddTaskLink(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            setResponsePage(new EditTaskPage(CompoundPropertyModel.of(new Task())));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            this.setEnabled(!new ProjectService().findAll().isEmpty());
        }
    }

}
