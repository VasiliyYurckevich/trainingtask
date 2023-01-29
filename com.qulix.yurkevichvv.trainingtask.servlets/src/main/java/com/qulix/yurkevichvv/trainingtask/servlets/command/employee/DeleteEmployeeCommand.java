package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEmployeeCommand implements Command {

    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        employeeService.delete(Integer.parseInt(req.getParameter("employeeId")));

        resp.sendRedirect("employees");
    }
}
