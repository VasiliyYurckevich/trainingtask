package com.qulix.yurkevichvv.trainingtask.wicket.panels.tables;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

public class EmployeeTablePanel extends Panel {

    public EmployeeTablePanel(String id, List<Employee> employees) {
        super(id);
        ListView<Employee> employeeListView = new ListView<>("employees", employees) {
            @Override
            protected void populateItem(ListItem<Employee> item) {
                final Employee employee = item.getModelObject();
                item.add(new Label("surname", employee.getSurname()));
                item.add(new Label("firstName", employee.getFirstName()));
                item.add(new Label("patronymic", employee.getPatronymic()));
                item.add(new Label("post", employee.getPost()));
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(employeeListView);
    }
}
