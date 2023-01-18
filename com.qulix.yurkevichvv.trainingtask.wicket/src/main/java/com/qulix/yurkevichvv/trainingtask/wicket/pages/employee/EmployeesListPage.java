package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
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

        CustomListView<Employee> employeeListView =
            new EmployeeCustomListView(LoadableDetachableModel.of(() -> new EmployeeService().findAll()), service);
        add(employeeListView);

        add(new WebPageLink("addEmployee",
            new Model<>(EmployeesListPage.this.pageFactory.createPage(CompoundPropertyModel.of(new Employee())))));
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
         * @param service сервис для работы с сущностями
         */
        public EmployeeCustomListView(LoadableDetachableModel<List<Employee>> employees, IService service) {
            super("employees", employees, service);
        }

        @Override
        protected AbstractEntityPageFactory<Employee> getPageFactory() {
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

    /**
     * Ссылка для перехода на страницу.
     *
     * @author Q-YVV
     */
    private class WebPageLink extends Link<WebPage> {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         * @param webPageModel модель страницы
         */
        public WebPageLink(String id, IModel<WebPage> webPageModel) {
            super(id, webPageModel);
        }

        @Override
        public void onClick() {
            setResponsePage(getModelObject());
        }
    }
}
