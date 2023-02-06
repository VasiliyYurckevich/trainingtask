package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
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
}
