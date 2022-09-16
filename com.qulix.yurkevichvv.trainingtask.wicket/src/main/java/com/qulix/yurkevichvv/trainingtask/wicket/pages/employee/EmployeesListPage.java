package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import org.apache.wicket.model.LoadableDetachableModel;

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
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject("Сотрудники");

        LoadableDetachableModel <List<Employee>> employees = new LoadableDetachableModel<>() {
            @Override
            protected List<Employee> load() {
                return new EmployeeDao().getAll();
            }
        };

        ListView<Employee> employeeListView = new EmployeeListView(employees);
        add(employeeListView);

        add(new Link<WebPage>("addEmployee") {
            @Override
            public void onClick() {
                setResponsePage(new EmployeePage(this.getPage(),new Employee()));
            }
        });
    }

    private static class EmployeeListView extends ListView<Employee> {
        public EmployeeListView(LoadableDetachableModel<List<Employee>> employees) {
            super("employees", employees);
            this.setReuseItems(true);
        }

        @Override
        protected void populateItem(ListItem<Employee> item) {
            final Employee employee = item.getModelObject();
            item.add(new Label("surname", employee.getSurname()));
            item.add(new Label("firstName", employee.getFirstName()));
            item.add(new Label("patronymic", employee.getPatronymic()));
            item.add(new Label("post", employee.getPost()));
            item.add(new DeleteLink("deleteLink", item.getModelObject()));
            item.add(new EditLink("editLink", item.getModelObject()));
        }
    }
}
