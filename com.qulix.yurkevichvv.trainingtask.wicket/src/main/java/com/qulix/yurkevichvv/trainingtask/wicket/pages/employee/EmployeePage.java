package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;

/**
 * Страница добавления/редактирования сотрудников.
 *
 * @author Q-YVV
 */
public class EmployeePage extends AbstractEntityPage<Employee> {

    /**
     * Максимальная длинна ввода полей.
     */
    private static final int MAXLENGTH = 50;

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService service = new EmployeeService();

    /**
     * Конструктор.
     *
     * @param employeeModel редактируемый сотрудник
     */
    public EmployeePage(CompoundPropertyModel<Employee> employeeModel) {
        super("Редактировать сотрудника", employeeModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<Employee> employeeForm = new EmployeeForm("form", entityModel);

        addFormComponents(employeeForm);
        add(employeeForm);
    }

    @Override
    protected final void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(EmployeesListPage.class);
    }

    @Override
    protected void addFormComponents(Form<Employee> form) {
        addButtons(form);
        addStringField(form, "surname", MAXLENGTH);
        addStringField(form, "firstName", MAXLENGTH);
        addStringField(form, "patronymic", MAXLENGTH);
        addStringField(form, "post", MAXLENGTH);
    }

    /**
     * Форма сотрудника.
     */
    private class EmployeeForm extends Form<Employee> {

        public EmployeeForm(String id, CompoundPropertyModel<Employee> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            onSubmitting();
            onChangesSubmitted();
        }
    }
}
