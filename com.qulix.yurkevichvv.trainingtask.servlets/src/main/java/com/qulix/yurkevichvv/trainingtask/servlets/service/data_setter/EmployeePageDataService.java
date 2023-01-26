package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class EmployeePageDataService implements PageDataService<Employee> {

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

    private final EmployeeService employeeService;

    public EmployeePageDataService() {
        this.employeeService = new EmployeeService();
    }

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, Employee entity) {
        entity.setSurname(paramsMap.get(SURNAME));
        entity.setFirstName(paramsMap.get(FIRST_NAME));
        entity.setPatronymic(paramsMap.get(PATRONYMIC));
        entity.setPost(paramsMap.get(POST));
    }

    @Override
    public void setValidatedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(EMPLOYEE_ID, paramsMap.get(EMPLOYEE_ID));
        req.setAttribute(SURNAME, paramsMap.get(SURNAME).trim());
        req.setAttribute(FIRST_NAME, paramsMap.get(FIRST_NAME).trim());
        req.setAttribute(PATRONYMIC, paramsMap.get(PATRONYMIC).trim());
        req.setAttribute(POST, paramsMap.get(POST).trim());
    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest req) {

        Map<String, String> params = new HashMap<>();

        params.put(EMPLOYEE_ID, req.getParameter(EMPLOYEE_ID));
        params.put(SURNAME, req.getParameter(SURNAME));
        params.put(FIRST_NAME, req.getParameter(FIRST_NAME));
        params.put(PATRONYMIC, req.getParameter(PATRONYMIC));
        params.put(POST, req.getParameter(POST));
        return params;
    }

    @Override
    public void setDataToPage(HttpServletRequest req, Employee entity) {
        req.setAttribute(EMPLOYEE_ID, entity.getId());
        req.setAttribute(SURNAME, entity.getSurname());
        req.setAttribute(FIRST_NAME, entity.getFirstName());
        req.setAttribute(PATRONYMIC, entity.getPatronymic());
        req.setAttribute(POST, entity.getPost());
    }

    @Override
    public Employee getEntity(HttpServletRequest req) {
        String employeeId = req.getParameter("employeeId");

        if (!employeeId.isBlank()) {
            return employeeService.getById(Integer.valueOf(employeeId));
        }
        return new Employee();
    }
}
