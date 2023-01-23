package com.qulix.yurkevichvv.trainingtask.servlets.controllers.pages;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.EmployeePageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SaveEmployeeCommand implements Command {

    /**
     * Хранит название JSP редактирования сотрудника.
     */
    private static final String EDIT_EMPLOYEE_FORM_JSP = "/edit-employee-form.jsp";

    /**
     * Обозначение ID сотрудника.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Обозначение списка сотрудников.
     */
    private static final String EMPLOYEES_LIST = "employees";
    private final EmployeeService employeeService;

    private final PageDataService<Employee> pageDataService;

    public SaveEmployeeCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.pageDataService = new EmployeePageDataService();
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> paramsMap = pageDataService.getDataFromPage(req);
        Map<String, String> errorsMap = ValidationService.checkEmployeeData(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            Employee employee = pageDataService.getEntity(req.getParameter(EMPLOYEE_ID));
            pageDataService.setOutputDataToEntity(paramsMap, employee);
            employeeService.save(employee);
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
        }
    }
}
