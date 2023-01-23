package com.qulix.yurkevichvv.trainingtask.servlets.controllers.pages;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditEmployeePageCommand implements Command {

    /**
     * Обозначение ID сотрудника.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Обозначение фамилии сотрудника.
     */
    private static final String SURNAME = "surname";

    /**
     * Обозначение имени сотрудника.
     */
    private static final String FIRST_NAME = "firstName";

    /**
     * Обозначение отчества сотрудника.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Обозначение должности сотрудника.
     */
    private static final String POST = "post";

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService employeeService = new EmployeeService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Employee employee = getEmployee(req.getParameter(EMPLOYEE_ID));

        req.setAttribute(EMPLOYEE_ID, employee.getId());
        req.setAttribute(SURNAME, employee.getSurname());
        req.setAttribute(FIRST_NAME, employee.getFirstName());
        req.setAttribute(PATRONYMIC, employee.getPatronymic());
        req.setAttribute(POST, employee.getPost());

        req.getRequestDispatcher("/edit-employee-form.jsp").forward(req, resp);
    }

    private Employee getEmployee(String employeeId) {
        if (!employeeId.isBlank()) {
            return employeeService.getById(Integer.valueOf(employeeId));
        }
        return new Employee();
    }
}
