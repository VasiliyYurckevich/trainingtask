package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.employee.EmployeeFormPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EditEmployeePage extends BasePage {

    private EmployeeDao employeeDao= new EmployeeDao();

    public EditEmployeePage(final Employee employee) {
        get("pageTitle").setDefaultModelObject("Редактировать сотрудника");
        EmployeeFormPanel employeeFormPanel = new EmployeeFormPanel("employeeFormPanel", new CompoundPropertyModel<Employee>(employee));
        add(employeeFormPanel);
    }
}
