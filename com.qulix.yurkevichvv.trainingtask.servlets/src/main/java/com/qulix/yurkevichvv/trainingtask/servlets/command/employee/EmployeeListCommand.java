package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

/**
 * Открывает список существующих в базе данных {@link Employee}.
 *
 * @author Q-YVV
 */
public class EmployeeListCommand implements Command {

    /**
     * Сервис для управления {@link Employee}.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("EMPLOYEE_LIST", employeeService.findAll());
        request.getRequestDispatcher("/employees.jsp").forward(request, response);
    }
}
