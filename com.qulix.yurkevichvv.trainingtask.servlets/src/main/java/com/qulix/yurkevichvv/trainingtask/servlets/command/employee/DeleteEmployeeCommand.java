package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

/**
 * Удаление {@link Employee}.
 *
 * @author Q-YVV
 */
public class DeleteEmployeeCommand implements Command {

    /**
     * Сервис для управления {@link Employee}.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        employeeService.delete(Integer.parseInt(req.getParameter("employeeId")));

        resp.sendRedirect("employees");
    }
}
