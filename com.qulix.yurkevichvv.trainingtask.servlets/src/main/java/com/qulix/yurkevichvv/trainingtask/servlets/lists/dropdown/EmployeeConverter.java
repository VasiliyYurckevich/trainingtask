package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;

public class EmployeeConverter implements DropDownItemConverter<Employee> {

    private EmployeeService employeeService = new EmployeeService();

    @Override
    public DropDownListItem convert(Employee employee) {
        return new DropDownListItem(employee.getId(), employee.getFullName());
    }

    @Override
    public Employee convert(DropDownListItem dropDownListItem) {
        return employeeService.getById(dropDownListItem.getId());
    }
}
