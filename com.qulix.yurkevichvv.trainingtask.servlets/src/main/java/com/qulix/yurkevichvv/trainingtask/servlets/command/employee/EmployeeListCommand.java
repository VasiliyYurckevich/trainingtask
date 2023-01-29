package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmployeeListCommand implements Command {

    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("EMPLOYEE_LIST", employeeService.findAll());

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
