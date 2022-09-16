package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import org.apache.wicket.Page;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;

/**
 * Страница добавления/редактирования сотрудников.
 *
 * @author Q-YVV
 */
public class EmployeePage<T extends Page> extends BasePage {


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
    private T page;
    private Employee employee;

    /**
     * Конструктор.
     *
     * @param page страница
     * @param employee редактируемый сотрудник
     */
    public EmployeePage(Employee employee) {
        super();
        this.page = page;
        this.employee = employee;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get(PAGE_TITLE).setDefaultModelObject("Редактировать сотрудника");
        Form employeeForm = new Form<>(EMPLOYEE_FORM, new CompoundPropertyModel<>(employee)) {
            @Override
            public void onSubmit() {
                EmployeeDao employeeDao = new EmployeeDao();
                employeeDao.update(getModelObject(), ConnectionController.getConnection());
                onAfterSubmit();
                }
        };
        addFormComponents(employeeForm);
        add(employeeForm);
    }

    protected void onAfterSubmit() {
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
                setResponsePage(EmployeesListPage.class);
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
