package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;

/**
 * Конвертер в элемент выпадающего списка для сотрудника.
 *
 * @author Q-YVV
 */
public class EmployeeDropDownItemConverter implements DropDownItemConverter<Employee> {

    @Override
    public DropDownListItem convert(Employee employee) {
        return new DropDownListItem(employee.getId(), employee.getFullName());
    }
}
