package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

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
    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public EditInProject(String id, ListItem<?> item, Project project) {
        super(id);
        this.item = item;
        this.project = project;

    }

    @Override
        public void onClick() {
            if (project.getId() != null) {
                //setResponsePage(new TaskPage());
            }
            else {
               // setResponsePage(new TaskPage(tasks));
        }
    }
}
