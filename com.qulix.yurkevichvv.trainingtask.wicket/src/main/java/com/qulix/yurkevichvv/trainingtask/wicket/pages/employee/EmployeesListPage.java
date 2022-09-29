package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;


/**
 * Страница списка сотрудников.
 *
 * @author Q-YVV
*/
public class EmployeesListPage extends BasePage {

    /**
     * Сервис для работы с Employee.
     */
    private EmployeeService service = new EmployeeService();

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
                return service.findAll();
            }
        };

        ListView<Employee> employeeListView = new EmployeeListView(employees, new EmployeeService());
        add(employeeListView);

        EmployeePage employeePage = getMewEmployeePage();

        add(new Link<WebPage>("addEmployee", new Model<>(employeePage)) {
            @Override
            public void onClick() {
                setResponsePage(getModelObject());
            }
        });
    }

    private EmployeePage getMewEmployeePage() {
        EmployeePage employeePage = new EmployeePage(new Model<>(new Employee())) {
            @Override
            protected void onChangesSubmitted() {
                setResponsePage(EmployeesListPage.this);
            }
        };
        return employeePage;
    }

    private static class EmployeeListView extends CustomListView<Employee> {
        public EmployeeListView(IModel<List<Employee>> employees, IService service) {
            super("employees", employees, service);
        }

        @Override
        protected void populateItem(ListItem<Employee> item) {
            super.populateItem(item);
            final Employee employee = item.getModelObject();
            item.add(new Label("surname", employee.getSurname()));
            item.add(new Label("firstName", employee.getFirstName()));
            item.add(new Label("patronymic", employee.getPatronymic()));
            item.add(new Label("post", employee.getPost()));
        }

        @Override
        public AbstractEntityPage getNewPage(ListItem<Employee> item) {
            EmployeePage projectPage = new EmployeePage(item.getModel()) {

                @Override
                protected void onChangesSubmitted() {
                    setResponsePage(EmployeesListPage.class);
                }
            };
            return  projectPage;
        }
    }
}
