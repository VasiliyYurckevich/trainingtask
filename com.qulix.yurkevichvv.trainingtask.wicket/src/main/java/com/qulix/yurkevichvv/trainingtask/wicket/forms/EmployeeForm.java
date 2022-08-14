package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class EmployeeForm extends Form<Employee> {

    public EmployeeForm(String id) {
        super(id, new CompoundPropertyModel<>(new Employee()));
    }
    public EmployeeForm(String id,  Employee employee) {
        super(id, new CompoundPropertyModel<>(employee));
    }

    @Override
    protected void onSubmit() {

    }

    protected Employee getEmployee(){
        Employee employee = getModelObject();
        return employee;
    }
}
