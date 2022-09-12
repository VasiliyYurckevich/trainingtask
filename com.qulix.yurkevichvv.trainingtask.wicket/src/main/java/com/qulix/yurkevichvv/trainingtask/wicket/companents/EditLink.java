package com.qulix.yurkevichvv.trainingtask.wicket.companents;

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
public class EditLink extends Link<Void> {

    /**
     * Элемент ListView.
     */
    private ListItem<?> item;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public EditLink(String id, ListItem<?> item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
        if (item.getModelObject() instanceof Employee) {
            setResponsePage(new EmployeePage((Employee) item.getModelObject()));
        }
        else if (item.getModelObject() instanceof Project) {
            setResponsePage(new ProjectPage((Project) item.getModelObject(),
                new TaskDao().getTasksInProject(((Project) item.getModelObject()).getId())));
        }
        else if (item.getModelObject() instanceof Task) {
            setResponsePage(new TaskPage((Task) item.getModelObject()));
        }
    }
}
