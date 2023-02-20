package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.EmployeeValidation;

/**
 * Сохраняет изменения {@link Employee} в базе данных.
 *
 * @author Q-YVV
 */
public class SaveEmployeeCommand extends CommandWithValidation<Employee> {

    /**
     * Сервис для управления {@link Employee}.
     */
    private final EmployeeService employeeService = new EmployeeService();

    /**
     * Конструктор.
     *
     */
    public SaveEmployeeCommand() {
        super(new EmployeeValidation(), new EmployeePageDataService());
    }

    @Override
    protected void successesAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employee employee = pageDataService.getEntity(request);
        pageDataService.setOutputDataToEntity(paramsMap, employee);
        employeeService.save(employee);
    }

    @Override
    public void redirectAfterSuccessesAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("employees");
    }

    @Override
    protected void failedAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(request, paramsMap, errorsMap);
        request.getRequestDispatcher("/edit-employee-form.jsp").forward(request, response);
    }
}
