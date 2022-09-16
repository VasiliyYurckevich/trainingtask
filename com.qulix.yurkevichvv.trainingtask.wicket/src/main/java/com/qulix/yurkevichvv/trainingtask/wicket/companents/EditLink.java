package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.api.entity.Entity;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.api.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;

/**
 * Ссылка для редактирования сущности.
 *
 * @author Q-YVV
 */
public class EditLink<T extends Entity> extends Link<T> {

    /**
     * Элемент ListView.
     */
    private T item;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public EditLink(String id, T item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
    }
}
