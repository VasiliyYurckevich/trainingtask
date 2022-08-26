package com.qulix.yurkevichvv.trainingtask.wicket.panels.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class EmployeeFormPanel extends Panel {

    private EmployeeDao employeeDao = new EmployeeDao();

    public EmployeeFormPanel(String id) {
        super(id);
        EmployeeForm employeeForm = new EmployeeForm("addEmployeeForm"){
            @Override
            protected void onSubmit() {
                employeeDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };
        add(employeeForm);
    }

    public EmployeeFormPanel(String id, IModel<?> model) {
        super(id, model);
        EmployeeForm employeeForm = new EmployeeForm("addEmployeeForm", (Employee) model.getObject()){
            @Override
            protected void onSubmit() {
                employeeDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(EmployeesListPage.class);
            }
        };
        add(employeeForm);
    }

    private class EmployeeForm extends Form<Employee> {

        public EmployeeForm(String id) {
            super(id, new CompoundPropertyModel<>(new Employee()));
            addFields();
        }

        public EmployeeForm(String id,  Employee employee) {
            super(id, new CompoundPropertyModel<>(employee));
            addFields();
        }

        private void addFields() {
            RequiredTextField<String> surname = new RequiredTextField<>("surname");
            surname.add(new CustomStringValidator(50));
            add(surname);
            RequiredTextField<String> firstName = new RequiredTextField<>("firstName");
            firstName.add(new CustomStringValidator(50));
            add(firstName);
            RequiredTextField<String> patronymic = new RequiredTextField<>("patronymic");
            patronymic.add(new CustomStringValidator(50));
            add(patronymic);
            RequiredTextField<String> post = new RequiredTextField<>("post");
            post.add(new CustomStringValidator(50));
            add(post);
            CustomFeedbackPanel surnameFeedbackPanel = new CustomFeedbackPanel("surnameFeedbackPanel", new ComponentFeedbackMessageFilter(surname));
            add(surnameFeedbackPanel);
            CustomFeedbackPanel firstNameFeedbackPanel = new CustomFeedbackPanel("firstNameFeedbackPanel", new ComponentFeedbackMessageFilter(firstName));
            add(firstNameFeedbackPanel);
            CustomFeedbackPanel patronymicFeedbackPanel = new CustomFeedbackPanel("patronymicFeedbackPanel", new ComponentFeedbackMessageFilter(patronymic));
            add(patronymicFeedbackPanel);
            CustomFeedbackPanel postFeedbackPanel = new CustomFeedbackPanel("postFeedbackPanel", new ComponentFeedbackMessageFilter(post));
            add(postFeedbackPanel);
        }

    }

}
