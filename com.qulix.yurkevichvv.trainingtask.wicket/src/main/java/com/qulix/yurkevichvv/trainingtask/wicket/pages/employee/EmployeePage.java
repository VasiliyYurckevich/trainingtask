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
     * Конструктор.
     *
     * @param employeeModel редактируемый сотрудник
     */
    public EmployeePage(CompoundPropertyModel<Employee> employeeModel) {
        super("Редактировать сотрудника", employeeModel,  new EmployeeForm("form", employeeModel));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
    }
/*
    @Override
    protected final void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(EmployeesListPage.class);
    }*/

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
