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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        employeeService.delete(Integer.parseInt(request.getParameter("employeeId")));
        response.sendRedirect("employees");
    }
}
