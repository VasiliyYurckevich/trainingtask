package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class Header extends Panel {

    public Header(String id) {
        super(id);
        add(new Link<WebPage>("projectsList") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsList.class);
            }
        });
        add(new Link<WebPage>("employeesList"){
            @Override
            public void onClick() {
                setResponsePage(EmployeesList.class);
            }
        });
        add(new Link<WebPage>("taskList"){
            @Override
            public void onClick() {
                setResponsePage(TasksList.class);
            }
        });
    }
}
