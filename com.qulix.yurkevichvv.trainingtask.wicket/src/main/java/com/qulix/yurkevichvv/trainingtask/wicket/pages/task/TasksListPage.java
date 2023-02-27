package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EntityEditPageLink;
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

        EntityEditPageLink<Task> addTask = new TaskEntityEditPageLink("addTask",
            TasksListPage.this.getPageFactory(), new Model<>(new Task()));
        add(addTask);

        CustomListView<Task> taskListView =
            new TaskCustomListView(LoadableDetachableModel.of(() -> new TaskService().findAll()), getService());
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
        public TaskCustomListView(LoadableDetachableModel<List<Task>> tasks, IService<Task> service) {
            super("tasks", tasks, service);
        }

        @Override
        protected AbstractEntityPageFactory<Task> generatePageFactory() {
            return new TaskPageFactory();
        }

        @Override
        protected void populateItem(ListItem<Task> item) {
            super.populateItem(item);
            ITaskTableColumns.addColumns(item);
            item.add(new Label("projectTitle", item.getModelObject().getProject().getTitle()));
        }
    }

    /**
     * Ссылка для перехода на страницу редактирования новой задачи.
     *
     * @author Q-YVV
     */
    private static class TaskEntityEditPageLink extends EntityEditPageLink<Task> {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         * @param pageFactory фабрика для генерации страницы
         * @param taskModel модель {@link Task}
         */
        public TaskEntityEditPageLink(String id, AbstractEntityPageFactory<Task> pageFactory, Model<Task> taskModel) {
            super(id, pageFactory, taskModel);
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            setEnabled(!new ProjectService().findAll().isEmpty());
        }
    }

}
