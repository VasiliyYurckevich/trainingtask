package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Форма сотрудника.
 */
class EmployeeForm extends AbstractEntityForm<Employee> {

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService service = new EmployeeService();

    public EmployeeForm(String id, CompoundPropertyModel<Employee> entityModel) {
        super(id, entityModel);
    }

    @Override
    public void onSubmit() {
        onSubmitting();
        onChangesSubmitted();
    }

    @Override
    protected final void onSubmitting() {
        service.save(getModelObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(EmployeesListPage.class);
    }
}
