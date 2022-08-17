package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.EmployeeForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import org.apache.wicket.markup.html.form.RequiredTextField;

public class AddEmployeePage extends BasePage {

    private EmployeeDao employeeDao= new EmployeeDao();


    public AddEmployeePage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить сотрудника");
        EmployeeForm employeeForm = new EmployeeForm("addEmployeeForm"){
            @Override
            protected void onSubmit() {
                employeeDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };
        employeeForm.add(new RequiredTextField<String>("surname"));
        employeeForm.add(new RequiredTextField<String>("firstName"));
        employeeForm.add(new RequiredTextField<String>("patronymic"));
        employeeForm.add(new RequiredTextField<String>("post"));
        add(employeeForm);
    }
}
