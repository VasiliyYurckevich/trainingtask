package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.EmployeeForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class EditEmployeePage extends BasePage {

    private EmployeeDao employeeDao= new EmployeeDao();

    public EditEmployeePage(PageParameters parameters) {
        super(parameters);
        Employee employee = employeeDao.getById(parameters.get("id").toInteger());
        EmployeeForm employeeForm = new EmployeeForm("editEmployeeForm"){
            @Override
            protected void onSubmit() {
                employeeDao.update(this.getEmployee(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };

    }
}
