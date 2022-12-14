package com.qulix.yurkevichvv.trainingtask.wicket.companents.models;

import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;

public class EmployeeListLoadableDetachableModel extends LoadableDetachableModel<List<Employee>> {
    @Override
    protected List<Employee> load() {
        return new EmployeeService().findAll();
    }
}
