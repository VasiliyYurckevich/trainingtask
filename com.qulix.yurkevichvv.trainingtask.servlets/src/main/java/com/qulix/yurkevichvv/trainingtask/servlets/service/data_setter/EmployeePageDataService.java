package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;

/**
 * Отвечает за взаимодействие данных {@link Employee} и визуализации на странице.
 *
 * @author Q-YVV
 */
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

    /**
     * Переменная доступа к методам работы с сущностями {@link Employee}.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, Employee entity) {
        entity.setSurname(paramsMap.get(SURNAME));
        entity.setFirstName(paramsMap.get(FIRST_NAME));
        entity.setPatronymic(paramsMap.get(PATRONYMIC));
        entity.setPost(paramsMap.get(POST));
    }

    @Override
    public void setFailedDataToPage(HttpServletRequest request, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        request.setAttribute("ERRORS", errorsMap);
        request.setAttribute(EMPLOYEE_ID, paramsMap.get(EMPLOYEE_ID));
        request.setAttribute(SURNAME, paramsMap.get(SURNAME).trim());
        request.setAttribute(FIRST_NAME, paramsMap.get(FIRST_NAME).trim());
        request.setAttribute(PATRONYMIC, paramsMap.get(PATRONYMIC).trim());
        request.setAttribute(POST, paramsMap.get(POST).trim());
    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put(EMPLOYEE_ID, request.getParameter(EMPLOYEE_ID));
        paramsMap.put(SURNAME, request.getParameter(SURNAME));
        paramsMap.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        paramsMap.put(PATRONYMIC, request.getParameter(PATRONYMIC));
        paramsMap.put(POST, request.getParameter(POST));
        return paramsMap;
    }

    @Override
    public void setDataToPage(HttpServletRequest request, Employee entity) {
        request.setAttribute(EMPLOYEE_ID, entity.getId());
        request.setAttribute(SURNAME, entity.getSurname());
        request.setAttribute(FIRST_NAME, entity.getFirstName());
        request.setAttribute(PATRONYMIC, entity.getPatronymic());
        request.setAttribute(POST, entity.getPost());
    }

    @Override
    public Employee getEntity(HttpServletRequest request) {
        String employeeId = request.getParameter(EMPLOYEE_ID);

        if (!employeeId.isBlank()) {
            return employeeService.getById(Integer.valueOf(employeeId));
        }
        return new Employee();
    }
}
