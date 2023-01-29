package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveEmployeeCommand extends CommandWithValidation<Employee> {

    private EmployeeService employeeService = new EmployeeService();

    /**
     * Конструктор.
     *
     * @param validationService сервис валидации
     * @param pageDataService   сервис взаимодействия сущностей модели и данных со страниц
     */
    public SaveEmployeeCommand(ValidationService validationService, PageDataService<Employee> pageDataService) {
        super(validationService, pageDataService);
    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Employee employee = pageDataService.getEntity(req);
        pageDataService.setOutputDataToEntity(paramsMap, employee);
        employeeService.save(employee);
        resp.sendRedirect("employees");
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
        req.getRequestDispatcher("/edit-employee-form.jsp").forward(req, resp);
    }
}
