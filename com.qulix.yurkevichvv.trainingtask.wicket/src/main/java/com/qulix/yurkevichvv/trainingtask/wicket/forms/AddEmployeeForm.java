package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.EmployeesListPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class AddEmployeeForm extends Form<AddEmployeeForm> {

    private String surname;
    private String firstName;
    private String patronymic;
    private String post;

    private EmployeeDao employeeDao = new EmployeeDao();

    public AddEmployeeForm(String id) {
        super(id);
        setModel(new CompoundPropertyModel<>(this));
        add(new RequiredTextField<String>("surname"));
        add(new RequiredTextField<String>("firstName"));
        add(new RequiredTextField<String>("patronymic"));
        add(new RequiredTextField<String>("post"));
    }

    @Override
    protected void onSubmit() {
        Employee employee = new Employee();
        employee.setSurname(surname);
        employee.setFirstName(firstName);
        employee.setPatronymic(patronymic);
        employee.setPost(post);
        employeeDao.add(employee, ConnectionController.getConnection());
        setResponsePage(EmployeesListPage.class);
    }

}
