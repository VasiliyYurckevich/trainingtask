package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TasksListPage;

/**
 * Верхняя панель для переключения между страницами.
 *
 * @author Q-YVV
 */
public class Header extends Panel {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public Header(String id) {
        super(id);

        add(new Link<WebPage>("projectsList") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        });
        add(new Link<WebPage>("employeesList") {
            @Override
            public void onClick() {
                setResponsePage(EmployeesListPage.class);
            }
        });
        add(new Link<WebPage>("tasksList") {
            @Override
            public void onClick() {
                setResponsePage(TasksListPage.class);
            }
        });
    }
}
