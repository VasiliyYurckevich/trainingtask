package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EntityEditPageLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractListPage;

/**
 * Страница списка сотрудников.
 *
 * @author Q-YVV
 */
public class EmployeesListPage extends AbstractListPage<Employee> {

    /**
     * Конструктор.
     */
    public EmployeesListPage() {
        super("Сотрудники", new EmployeePageFactory(), new EmployeeService());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new EntityEditPageLink<>("addEmployee", getPageFactory(), new Model<>(new Employee())));

        CustomListView<Employee> employeeListView =
            new EmployeeCustomListView(LoadableDetachableModel.of(() -> new EmployeeService().findAll()), getService());
        add(employeeListView);
    }

    /**
     * Реализует CustomListView для сотрудников.
     *
     * @author Q-YVV
     */
    private static class EmployeeCustomListView extends CustomListView<Employee> {

        /**
         * Конструктор.
         *
         * @param employees модель списка сотрудников
         * @param service   сервис для работы с сущностями
         */
        public EmployeeCustomListView(LoadableDetachableModel<List<Employee>> employees, IService<Employee> service) {
            super("employees", employees, service);
        }

        @Override
        protected AbstractEntityPageFactory<Employee> generatePageFactory() {
            return new EmployeePageFactory();
        }

        @Override
        protected void populateItem(ListItem<Employee> item) {
            super.populateItem(item);
            final Employee employee = item.getModelObject();
            item.add(new Label("surname", employee.getSurname()))
                    .add(new Label("firstName", employee.getFirstName()))
                    .add(new Label("patronymic", employee.getPatronymic()))
                    .add(new Label("post", employee.getPost()));
        }
    }
}
