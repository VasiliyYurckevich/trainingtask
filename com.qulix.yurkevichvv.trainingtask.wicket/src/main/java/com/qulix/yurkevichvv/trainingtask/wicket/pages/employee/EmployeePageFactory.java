package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import java.io.Serializable;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;

/**
 * Генерирует страницу редактирования сотрудника.
 *
 * @author Q-YVV
 */
public class EmployeePageFactory implements AbstractEntityPageFactory<Employee>, Serializable {
    @Override
    public AbstractEntityPage createPage(CompoundPropertyModel<Employee> entityModel) {
        return new EmployeePage(entityModel);
    }
}
