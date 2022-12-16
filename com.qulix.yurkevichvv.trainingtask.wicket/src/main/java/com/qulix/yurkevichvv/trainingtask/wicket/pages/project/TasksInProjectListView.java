package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.ITaskTableColumns;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPageFactory;

/**
 * Реализует CustomListView для задач проекта.
 *
 * @author Q-YVV
 */
class TasksInProjectListView extends ListView<Task> {

    /**
     * Модель проекта, связанного с задачами.
     */
    private final IModel<Project> projectModel;

    /**
     * Сервис для работы с проектом.
     */
    private final ProjectService service;

    /**
     * Фабрика для работы с задачами проекта.
     */
    private final AbstractEntityPageFactory<Task> pageFactory;

    /**
     * Конструктор.
     *
     * @param tasks        модель списка задач
     * @param projectModel модель проекта
     * @param service      сервис для работ с проектом
     */
    public TasksInProjectListView(LoadableDetachableModel<List<Task>> tasks, IModel<Project> projectModel,
        ProjectService service) {

        super("tasks", tasks);
        this.projectModel = projectModel;
        this.service = service;
        this.pageFactory = new TaskPageFactory();
    }

    @Override
    protected void populateItem(ListItem<Task> item) {
        ITaskTableColumns.addColumns(item);

        item.add(new DeleteInProjectLink(item.getModel()))
            .add(new EditLink<>("editLink", pageFactory, item.getModel()));
    }

    /**
     * Реализует DeleteLink для задач проекта.
     *
     * @author Q-YVV
     */
    private class DeleteInProjectLink extends Link<Void> {
        private final IModel<Task> taskModel;

        public DeleteInProjectLink(IModel<Task> taskModel) {
            super("deleteLink");
            this.taskModel = taskModel;
        }

        @Override
        public void onClick() {
            service.deleteTask(projectModel.getObject(), taskModel.getObject());
            TasksInProjectListView.this.getModel().detach();
        }
    }
}
