package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.model.IDetachable;

import java.io.Serializable;

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
