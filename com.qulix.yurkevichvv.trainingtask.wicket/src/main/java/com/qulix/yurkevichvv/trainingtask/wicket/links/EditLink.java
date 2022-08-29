package com.qulix.yurkevichvv.trainingtask.wicket.links;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.EditProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

public class EditLink extends Link<Void> {
    private ListItem<?> item;

    public EditLink(String id, ListItem<?> item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
        if (item.getModelObject() instanceof Employee) {
                setResponsePage(new EmployeePage((Employee) item.getModelObject()));
        } else if (item.getModelObject() instanceof Project) {
            setResponsePage(new EditProjectPage((Project) item.getModelObject(),
                new TaskDao().getTasksInProject(((Project) item.getModelObject()).getId())));
        } else if (item.getModelObject() instanceof Task) {
            setResponsePage(new TaskPage((Task) item.getModelObject()));
        }
    }
}
