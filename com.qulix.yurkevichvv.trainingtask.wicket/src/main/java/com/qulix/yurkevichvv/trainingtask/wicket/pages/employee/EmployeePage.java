package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
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
     * Конструктор.
     *
     * @param employeeModel редактируемый сотрудник
     */
    public EmployeePage(CompoundPropertyModel<Employee> employeeModel) {
        super("Редактировать сотрудника", employeeModel, new EmployeeForm("employeeForm", employeeModel));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
    }

    @Override
    protected void addFormComponents() {
        addButtons();
        addStringField("surname", MAXLENGTH);
        addStringField("firstName", MAXLENGTH);
        addStringField("patronymic", MAXLENGTH);
        addStringField("post", MAXLENGTH);
        add(getForm());
    }

    /**
     * Форма сотрудника.
     */
    static class EmployeeForm extends AbstractEntityForm<Employee> {

        /**
         * Сервис для работы с Employee.
         */
        private final EmployeeService service = new EmployeeService();

        /**
         * Конструктор.
         *
         * @param id
         * @param entityModel
         */
        public EmployeeForm(String id, CompoundPropertyModel<Employee> entityModel) {
            super(id, entityModel);
        }

        @Override
        protected void onSubmit() {
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
}
