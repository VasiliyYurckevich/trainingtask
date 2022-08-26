package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.employee.EmployeeFormPanel;

public class AddEmployeePage extends BasePage {

    private EmployeeDao employeeDao= new EmployeeDao();


    public AddEmployeePage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить сотрудника");
        EmployeeFormPanel employeeFormPanel = new EmployeeFormPanel("employeeFormPanel");
        add(employeeFormPanel);
    }

}
