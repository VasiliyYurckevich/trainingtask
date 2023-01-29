package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditEmployeeCommand implements Command {

    private final EmployeePageDataService pageDataService = new EmployeePageDataService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Employee employee = pageDataService.getEntity(req);

        pageDataService.setDataToPage(req, employee);

        req.getRequestDispatcher("/edit-employee-form.jsp").forward(req, resp);
    }
}
