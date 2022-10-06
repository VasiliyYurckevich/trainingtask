package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

class EmployeeIModel implements IModel<Employee> {

    /**
     * Список сотрудников.
     */
    private final List<Employee> list;

    /**
     * Редактируемая задача.
     */
    private final Task task;

    /**
     * Конструктор.
     *
     * @param list список сотрудников
     * @param task редактируемая задача
     */
    public EmployeeIModel(List<Employee> list, Task task) {
        this.list = list;
        this.task = task;
    }

    @Override
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
        task.setEmployeeId(employee != null ? employee.getId() : null);
    }
}
