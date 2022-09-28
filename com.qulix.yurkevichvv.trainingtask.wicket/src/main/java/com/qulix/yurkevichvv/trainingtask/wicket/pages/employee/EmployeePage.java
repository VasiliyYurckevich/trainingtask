package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;


import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;


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
    private IModel<Employee> employeeModel;

    private IService service;
    /**
     * Конструктор.
     *
     * @param employeeModel редактируемый сотрудник
     */
    public EmployeePage(IModel<Employee> employeeModel, IService service) {
        super();
        this.employeeModel = employeeModel;
        this.service = service;
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
    protected void onSubmitting() {
        service.save(employeeModel.getObject());
    }

    @Override
    protected void onChangesSubmitted() {
    }

    /**
     * Добавляет компоненты в форму задачи.
     *
     * @param form форма для добавления
     */
    private void addFormComponents(Form form) {
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button);
        form.setDefaultButton(button);
        form.add(new PreventSubmitOnEnterBehavior());

        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                onChangesSubmitted();
            }
        };
        form.add(cancelButton);

        RequiredTextField<String> surname = new RequiredTextField<>("surname");
        surname.add(new CustomStringValidator(MAXLENGTH));
        form.add(surname);

        RequiredTextField<String> firstName = new RequiredTextField<>("firstName");
        firstName.add(new CustomStringValidator(MAXLENGTH));
        form.add(firstName);

        RequiredTextField<String> patronymic = new RequiredTextField<>("patronymic");
        patronymic.add(new CustomStringValidator(MAXLENGTH));
        form.add(patronymic);

        RequiredTextField<String> post = new RequiredTextField<>("post");
        post.add(new CustomStringValidator(MAXLENGTH));
        form.add(post);

        CustomFeedbackPanel surnameFeedbackPanel = new CustomFeedbackPanel("surnameFeedbackPanel",
            new ComponentFeedbackMessageFilter(surname));
        form.add(surnameFeedbackPanel);

        CustomFeedbackPanel firstNameFeedbackPanel = new CustomFeedbackPanel("firstNameFeedbackPanel",
            new ComponentFeedbackMessageFilter(firstName));
        form.add(firstNameFeedbackPanel);

        CustomFeedbackPanel patronymicFeedbackPanel = new CustomFeedbackPanel("patronymicFeedbackPanel",
            new ComponentFeedbackMessageFilter(patronymic));
        form.add(patronymicFeedbackPanel);

        CustomFeedbackPanel postFeedbackPanel = new CustomFeedbackPanel("postFeedbackPanel",
            new ComponentFeedbackMessageFilter(post));
        form.add(postFeedbackPanel);
    }


}
