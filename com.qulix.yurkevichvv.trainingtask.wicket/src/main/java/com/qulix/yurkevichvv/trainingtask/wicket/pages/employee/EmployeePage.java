package com.qulix.yurkevichvv.trainingtask.wicket.pages.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Страница добавления/редактирования сотрудников.
 *
 * @author Q-YVV
 */
public class EmployeePage extends BasePage {

    /**
     * Конструктор.
     */
    public EmployeePage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить сотрудника");
        Form employeeForm =  new Form<Employee>("employeeForm", new CompoundPropertyModel<>(new Employee()) ){
            @Override
            protected void onSubmit() {
                EmployeeDao employeeDao = new EmployeeDao();
                employeeDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };
        addFormComponents(employeeForm);
        add(employeeForm);
    }

    /**
     * Конструктор.
     *
     * @param employee редактируемый сотрудник
     */
    public EmployeePage(final Employee employee) {
        get("pageTitle").setDefaultModelObject("Редактировать сотрудника");
        Form employeeForm =  new Form<>("employeeForm", new CompoundPropertyModel<>(employee)){
            @Override
            protected void onSubmit() {
                EmployeeDao employeeDao = new EmployeeDao();
                employeeDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };
        addFormComponents(employeeForm);
        add(employeeForm);
    }

    /**
     * Добавляет компаненты в форму задачи.
     *
     * @param form форма для добавления
     */
    private void addFormComponents(Form form) {
        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(EmployeesListPage.class);
            }
        };
        form.add(cancelButton);

        RequiredTextField<String> surname = new RequiredTextField<>("surname");
        surname.add(new CustomStringValidator(50));
        form.add(surname);

        RequiredTextField<String> firstName = new RequiredTextField<>("firstName");
        firstName.add(new CustomStringValidator(50));
        form.add(firstName);

        RequiredTextField<String> patronymic = new RequiredTextField<>("patronymic");
        patronymic.add(new CustomStringValidator(50));
        form.add(patronymic);

        RequiredTextField<String> post = new RequiredTextField<>("post");
        post.add(new CustomStringValidator(50));
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