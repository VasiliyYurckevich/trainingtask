package com.qulix.yurkevichvv.trainingtask.servlets.controllers.pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.Command;



public class EmployeeListPageCommand implements Command {

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("EMPLOYEE_LIST", employeeService.findAll());

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
