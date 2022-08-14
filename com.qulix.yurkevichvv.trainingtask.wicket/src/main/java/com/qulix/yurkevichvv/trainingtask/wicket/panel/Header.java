package com.qulix.yurkevichvv.trainingtask.wicket.panel;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class Header extends Panel {

    public Header(String id) {
        super(id);
        WebMarkupContainer css = new WebMarkupContainer( "style" );
        add(css);
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
