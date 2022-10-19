/*
 * Copyright 2007 Qulix Systems, Inc. All rights reserved.
 * QULIX SYSTEMS PROPRIETARY/CONFIDENTIAL. Use is subject to license
 * terms.
 * Copyright (c) 2003-2007 Qulix Systems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Qulix Systems. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * QULIX MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.servlets.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.servlets.validation.ValidationService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Сотрудник".
 *
 * @author Q-YVV
 */
public class EmployeeController extends HttpServlet {

    /**
     * Хранит название JSP добавления сотрудника.
     */
    private static final String ADD_EMPLOYEE_FORM_JSP = "/add-employee-form.jsp";

    /**
     * Хранит название JSP редактирования сотрудника.
     */
    private static final String EDIT_EMPLOYEE_FORM_JSP = "/edit-employee-form.jsp";

    /**
     * Хранит константу для обозначения действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Хранит константу для обозначения ID сотрудника.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Хранит константу для обозначения фамилии сотрудника.
     */
    private static final String SURNAME = "surname";

    /**
     * Хранит константу для обозначения имени сотрудника.
     */
    private static final String FIRST_NAME = "firstName";

    /**
     * Хранит константу для обозначения отчества сотрудника.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Хранит константу для обозначения должности сотрудника.
     */
    private static final String POST = "post";

    /**
     * Хранит константу для обозначения списка сотрудников.
     */
    private static final String EMPLOYEES_LIST = "employees";

    /**
     * Хранит название кейса для выбора списка сотрудников.
     */
    private static final String LIST = "/list";

    /**
     * Хранит текст для исключения при выборе неизвестной команды.
     */
    private static final String UNKNOWN_COMMAND_OF_EMPLOYEE_CONTROLLER = "Unknown command of Employee Controller:";

    /**
     * Хранит название для хранения сотрудника.
     */
    private static final String EMPLOYEE = "employee";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    /**
     * Сервис для работы с Employee.
     */
    private EmployeeService employeeService = new EmployeeService();

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/update":
                    updateEmployee(req, resp);
                    break;
                case "/add":
                    addEmployee(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_EMPLOYEE_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doPost method in Employee Controller", e);
            throw e;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = LIST;
            }

            switch (action) {
                case "/delete":
                    deleteEmployee(req, resp);
                    break;
                case "/new":
                    addEmployeeForm(req, resp);
                    break;
                case "/edit":
                    updateEmployeeForm(req, resp);
                    break;
                case LIST:
                    listEmployees(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_EMPLOYEE_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doGet method in Employee Controller", e);
            throw e;
        }
    }

    /**
     * Отображает форму для редактирования сотрудника.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Integer employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        Employee existingEmployee = employeeService.getById(employeeId);
        existingEmployee.setId(employeeId);

        req.getSession().setAttribute(EMPLOYEE, existingEmployee);
        req.setAttribute(SURNAME, existingEmployee.getSurname());
        req.setAttribute(FIRST_NAME, existingEmployee.getFirstName());
        req.setAttribute(PATRONYMIC, existingEmployee.getPatronymic());
        req.setAttribute(POST, existingEmployee.getPost());

        req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
    }

    /**
     * Отображает форму для добавления сотрудника.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, ServiceException, IOException {

        req.getSession().setAttribute(EMPLOYEE, new Employee());

        req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP).forward(req, resp);
    }

    /**
     * Удаляет сотрудника из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        Integer employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        employeeService.delete(employeeId);

        resp.sendRedirect(EMPLOYEES_LIST);
    }

    /**
     * Записывает отредактированного сотрудника в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Map<String, String> paramsMap = getDataFromJsp(req);
        Map<String, String> errorsMap = ValidationService.checkEmployeeData(paramsMap);

        if (errorsMap.isEmpty()) {
            Employee employee = (Employee) req.getSession().getAttribute(EMPLOYEE);
            employee = getEmployee(paramsMap, employee);
            employeeService.save(employee);
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            setDataToJsp(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
        }
    }

    /**
     * Создает сотрудника с полученными параметрами.
     *
     * @param paramsMap список параметров
     * @return возвращаемый сотрудник
     */
    private static Employee getEmployee(Map<String, String> paramsMap, Employee employee) {
        employee.setSurname(paramsMap.get(SURNAME));
        employee.setFirstName(paramsMap.get(FIRST_NAME));
        employee.setPatronymic(paramsMap.get(PATRONYMIC));
        employee.setPost(paramsMap.get(POST));
        return employee;
    }

    /**
     * Заполняет форму данными о сотруднике.
     *
     * @param req запрос
     * @param paramsMap список параметров
     * @param errorsMap список ошибок
     */
    private void setDataToJsp(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(SURNAME, paramsMap.get(SURNAME).trim());
        req.setAttribute(FIRST_NAME, paramsMap.get(FIRST_NAME).trim());
        req.setAttribute(PATRONYMIC, paramsMap.get(PATRONYMIC).trim());
        req.setAttribute(POST, paramsMap.get(POST).trim());
    }

    /**
     * Получение данных о сотруднике из формы.
     *
     * @param req запрос
     * @return список параметров
     */
    private Map<String, String> getDataFromJsp(HttpServletRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put(SURNAME, req.getParameter(SURNAME));
        params.put(FIRST_NAME, req.getParameter(FIRST_NAME));
        params.put(PATRONYMIC, req.getParameter(PATRONYMIC));
        params.put(POST, req.getParameter(POST));
        return params;
    }

    /**
     * Валидация и добавление сотрудника в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Map<String, String> paramsMap = getDataFromJsp(req);
        Map<String, String> errorsMap = ValidationService.checkEmployeeData(paramsMap);

        if (errorsMap.isEmpty()) {
            Employee employee = (Employee) req.getSession().getAttribute(EMPLOYEE);
            employeeService.save(getEmployee(paramsMap, employee));
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            setDataToJsp(req, paramsMap, errorsMap);
            req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP).forward(req, resp);
        }
    }

    /**
     * Отображение списка сотрудников.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        req.getSession().invalidate();
        Utils.setDataToList(req);

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
