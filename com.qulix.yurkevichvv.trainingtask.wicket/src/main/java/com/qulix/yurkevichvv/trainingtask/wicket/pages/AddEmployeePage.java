package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.wicket.forms.AddEmployeeForm;

public class AddEmployeePage extends BasePage {

    public AddEmployeePage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить сотрудника");
        add(new AddEmployeeForm("addEmployeeForm"));
    }
}
