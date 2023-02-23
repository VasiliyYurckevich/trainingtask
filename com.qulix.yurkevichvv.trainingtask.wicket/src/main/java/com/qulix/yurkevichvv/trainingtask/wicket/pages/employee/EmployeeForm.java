package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;

/**
 * Форма сотрудника.
 */
class EmployeeForm extends AbstractEntityForm<Employee> {

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService service = new EmployeeService();

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param entityModel модель сущности {@link Employee}
     */
    public EmployeeForm(String id, CompoundPropertyModel<Employee> entityModel) {
        super(id, entityModel);
    }

    @Override
    public final void onSubmitting() {
        service.save(getModelObject());
    }

    @Override
    public final void onChangesSubmitted() {
        setResponsePage(EmployeesListPage.class);
    }
}
