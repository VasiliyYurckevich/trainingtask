package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;

/**
 * Страница добавления/редактирования сотрудников.
 *
 * @author Q-YVV
 */
public class EmployeePage extends AbstractEntityPage {

    /**
     * Идентификатор элемента названия страницы.
     */
    private static final String PAGE_TITLE = "pageTitle";

    /**
     * Идентификатор элемента формы.
     */
    private static final String EMPLOYEE_FORM = "form";

    /**
     * Максимальная длинна ввода полей.
     */
    private static final int MAXLENGTH = 50;

    /**
     * Модель Employee.
     */
    private IModel<Employee> employeeModel;

    /**
     * Сервис для работы с Employee.
     */
    private EmployeeService service = new EmployeeService();

    /**
     * Конструктор.
     *
     * @param employeeModel редактируемый сотрудник
     */
    public EmployeePage(IModel<Employee> employeeModel) {
        super();
        this.employeeModel = employeeModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get(PAGE_TITLE).setDefaultModelObject("Редактировать сотрудника");
        Form employeeForm = new Form<>(EMPLOYEE_FORM, new CompoundPropertyModel<>(employeeModel)) {
            @Override
                public void onSubmit() {
                onSubmitting();
                onChangesSubmitted();
                }
        };
        addFormComponents(employeeForm);
        add(employeeForm);
    }

    @Override
    protected final void onSubmitting() {
        service.save(employeeModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(EmployeesListPage.class);
    }

    @Override
    protected void addFormComponents(Form form) {
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button)
            .add(new PreventSubmitOnEnterBehavior());
        form.setDefaultButton(button);

        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                onChangesSubmitted();
            }
        };
        form.add(cancelButton);

        addStringField(form, "surname", MAXLENGTH);
        addStringField(form, "firstName", MAXLENGTH);
        addStringField(form, "patronymic", MAXLENGTH);
        addStringField(form, "post", MAXLENGTH);
    }
}
