package com.qulix.yurkevichvv.trainingtask.servlets.controllers.pages;

import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEmployeeCommand implements Command {


    /**
     * Обозначение ID сотрудника.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Обозначение списка сотрудников.
     */
    private static final String EMPLOYEES_LIST = "employees";

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        employeeService.delete(Integer.parseInt(req.getParameter(EMPLOYEE_ID)));

        resp.sendRedirect(EMPLOYEES_LIST);
    }
}
