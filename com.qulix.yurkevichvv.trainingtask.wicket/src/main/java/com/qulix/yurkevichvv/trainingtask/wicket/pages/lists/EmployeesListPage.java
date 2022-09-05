package com.qulix.yurkevichvv.trainingtask.wicket.pages.lists;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeePage;

/**
 * Страница списка сотрудников.
 *
 * @author Q-YVV
*/
public class EmployeesListPage extends BasePage {

    /**
     * Конструктор.
     */
    public EmployeesListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Сотрудники");
        List<Employee> employees = new EmployeeDao().getAll();
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
        add(new Link<WebPage>("addEmployee") {
            @Override
            public void onClick() {
                setResponsePage(new EmployeePage());
            }
        });
    }
}
