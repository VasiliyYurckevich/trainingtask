package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TasksListPage;


/**
 * Ссылка для удаления сущности.
 *
 * @author Q-YVV
 */
public class DeleteLink extends Link<Void> {

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
    public DeleteLink(String id, ListItem<?> item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
        if (item.getModelObject() instanceof Employee) {
            EmployeeDao employeeDao = new EmployeeDao();
            employeeDao.delete(((Employee) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(EmployeesListPage.class);
        }
        else if (item.getModelObject() instanceof Project) {
            ProjectDao projectDao = new ProjectDao();
            projectDao.delete(((Project) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(ProjectsListPage.class);
        }
        else if (item.getModelObject() instanceof Task) {
            TaskDao taskDao = new TaskDao();
            taskDao.delete(((Task) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(TasksListPage.class);
        }
    }
}
