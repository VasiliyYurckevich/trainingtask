package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.wicket.tables.EmployeeTable;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import java.util.List;

public class EmployeesListPage extends BasePage {

    public EmployeesListPage() {
        super();
        get("pageTitle").setDefaultModelObject("Сотруднки");
        List<Employee> employees = new EmployeeDao().getAll();
        add(new EmployeeTable("employees", employees));
        add(new Link<WebPage>("addEmployee") {
            @Override
            public void onClick() {
                setResponsePage(new AddEmployeePage());
            }
        });
    }
}
