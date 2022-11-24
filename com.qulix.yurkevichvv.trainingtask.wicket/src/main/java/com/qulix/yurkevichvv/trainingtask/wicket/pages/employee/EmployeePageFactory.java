package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.model.CompoundPropertyModel;

public class EmployeePageFactory implements AbstractEntityPageFactory<Employee> {
    @Override
    public AbstractEntityPage createPage(CompoundPropertyModel<Employee> entityModel) {
        return new EmployeePage(entityModel);
    }
}
