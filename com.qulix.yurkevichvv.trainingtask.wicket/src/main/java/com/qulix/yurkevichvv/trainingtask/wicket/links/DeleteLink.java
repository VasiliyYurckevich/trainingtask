package com.qulix.yurkevichvv.trainingtask.wicket.links;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

public class DeleteLink extends Link<Void> {
    private ListItem<?> item;
    private EmployeeDao employeeDao= new EmployeeDao();
    private ProjectDao projectDao= new ProjectDao();
    private TaskDao taskDao = new TaskDao();

    public DeleteLink(String id, ListItem<?> item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
        if (item.getModelObject() instanceof Employee) {
            employeeDao.delete(((Employee) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(EmployeesListPage.class);
        } else if (item.getModelObject() instanceof Project) {
            projectDao.delete(((Project) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(ProjectsListPage.class);
        } else if (item.getModelObject() instanceof Task) {
            taskDao.delete(((Task) item.getModelObject()).getId(), ConnectionController.getConnection());
            setResponsePage(TasksListPage.class);
        }
    }
}
