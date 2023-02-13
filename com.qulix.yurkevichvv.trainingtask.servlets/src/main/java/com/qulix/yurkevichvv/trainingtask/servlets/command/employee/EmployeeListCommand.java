package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        //session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("EMPLOYEE_LIST", employeeService.findAll());

        generateToken(req);

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
