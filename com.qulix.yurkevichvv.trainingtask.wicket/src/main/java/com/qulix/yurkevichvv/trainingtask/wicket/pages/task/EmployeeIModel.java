package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

class EmployeeIModel implements IModel<Employee> {
    private final List<Employee> list;
    private final Task task;

    public EmployeeIModel(List<Employee> list, Task task) {
        this.list = list;
        this.task = task;
    }

    public Employee getObject() {
        for (Employee employee : list) {
            if (employee.getId().equals(task.getEmployeeId())) {
                return employee;
            }
        }
        return null;
    }

    @Override

    public void setObject(final Employee employee) {
        task.setEmployeeId(employee.getId());
    }
}
