package com.qulix.yurkevichvv.trainingtask.wicket.links;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

/**
 * Ссылка для редактирования задачи проекта.
 *
 * @author Q-YVV
 */
public class EditInProject extends Link<Void> {

    /**
     * Элемент ListView.
     */
    private ListItem<?> item;

    /**
     * Связанный проект.
     */
    private Project project;

    /**
     * Список задач.
     */
    private List<Task> tasks;
    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public EditInProject(String id, ListItem<?> item, Project project, List<Task> tasks) {
        super(id);
        this.item = item;
        this.project = project;
        this.tasks = tasks;
    }

    @Override
    public void onClick() {
        if (project.getId() != null) {
            setResponsePage(new TaskPage(project, tasks, item.getIndex()));
        }
        else {
            setResponsePage(new TaskPage(tasks, item.getIndex()));
        }
    }
}
