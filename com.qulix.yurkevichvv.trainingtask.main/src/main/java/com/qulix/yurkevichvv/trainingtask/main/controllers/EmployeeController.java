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
package com.qulix.yurkevichvv.trainingtask.main.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.main.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.main.dao.IDao;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.main.validation.ValidationService;





/**
 * Содержит сервлеты для выполнения действий объектов класса "Сотрудник".
 *
 * @author Q-YVV
 */
public class EmployeeController extends HttpServlet {

    /**
     * Хранит название JSP добавления сотрудника.
     */
    private static final String ADD_EMPLOYEE_FORM_JSP = "/add1-employee-form.jsp";

    /**
     * Хранит название JSP редактирования сотрудника.
     */
    private static final String EDIT_EMPLOYEE_FORM_JSP = "/edit-employee-form.jsp";

    /**
     * Хранит название кейса для выбора списка сотрудников.
     */
    private static final String LIST = "/list";

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
     * Хранит константу для сообщения ошибки в сервлете.
     */
    public static final String ERROR_IN_SERVLET = "Ошибка в сервлете ";

    /**
     * Переменная доступа к методам классов DAO.
     */
    private IDao<Employee> employeeInterface;

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        employeeInterface = new EmployeeDao();
    }


    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);
            switch (action) {
                case "/add":
                    addEmployee(req, resp);
                    break;
                case "/update":
                    updateEmployee(req, resp);
                    break;
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServletException(e);
        }
        catch (DaoException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        }
    }


    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = LIST;
            }

            switch (action) {
                case LIST:
                    listEmployees(req, resp);
                    break;
                case "/delete":
                    deleteEmployee(req, resp);
                    break;
                case "/new":
                    addEmployeeForm(req, resp);
                    break;
                case "/edit":
                    updateEmployeeForm(req, resp);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServletException(e);
        }
        catch (DaoException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        }
    }


    /**
     * Отображает форму для редактирования сотрудника.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {

        Integer employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        Employee existingEmployee = employeeInterface.getById(employeeId);
        req.setAttribute(EMPLOYEE_ID, employeeId);
        req.setAttribute(SURNAME, existingEmployee.getSurname());
        req.setAttribute(FIRST_NAME, existingEmployee.getFirstName());
        req.setAttribute(PATRONYMIC, existingEmployee.getPatronymic());
        req.setAttribute(POST, existingEmployee.getPost());

        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP);
        existingEmployee.setId(employeeId);
        req.setAttribute("employee", existingEmployee);
        dispatcher.forward(req, resp);
    }

    /**
     * Отображает форму для добавления сотрудника.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет сотрудника из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, IOException {

        Integer employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        employeeInterface.delete(employeeId);
        resp.sendRedirect(EMPLOYEES_LIST);
        LOGGER.log(Level.INFO, "Employee with id {0} deleted", employeeId);
    }

    /**
     * Записывает отредактированного сотрудника в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, DaoException, IOException {

        int employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        Map<String, String> paramsList = getDataFromJsp(req);
        Map<String, String> errorsList = ValidationService.inspectEmployeeData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            req.setAttribute(EMPLOYEE_ID, employeeId);
            Employee theEmployee = getEmployee(paramsList);
            theEmployee.setId(employeeId);
            LOGGER.log(Level.INFO, "Employee with id {0} updated", employeeId);
            employeeInterface.update(theEmployee);
            resp.sendRedirect(EMPLOYEES_LIST);

        }
        else {
            req.setAttribute(EMPLOYEE_ID, employeeId);
            setDataToJsp(req, paramsList, errorsList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Создает сотрудника с полученными параметрами.
     *
     * @param paramsList список параметров
     * @return возвращаемый сотрудник
     */
    private static Employee getEmployee(Map<String, String> paramsList) {
        Employee theEmployee = new Employee();
        theEmployee.setSurname(paramsList.get(SURNAME));
        theEmployee.setFirstName(paramsList.get(FIRST_NAME));
        theEmployee.setPatronymic(paramsList.get(PATRONYMIC));
        theEmployee.setPost(paramsList.get(POST));
        return theEmployee;
    }

    /**
     * Заполняет форму данными о сотруднике.
     *
     * @param req запрос
     * @param paramsList список параметров
     * @param errorsList список ошибок
     */
    private void setDataToJsp(HttpServletRequest req, Map<String, String> paramsList, Map<String, String> errorsList) {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(SURNAME, paramsList.get(SURNAME).trim());
        req.setAttribute(FIRST_NAME, paramsList.get(FIRST_NAME).trim());
        req.setAttribute(PATRONYMIC, paramsList.get(PATRONYMIC).trim());
        req.setAttribute(POST, paramsList.get(POST).trim());
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
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        Map<String, String> paramsList = getDataFromJsp(req);
        Map<String, String> errorsList = ValidationService.inspectEmployeeData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Employee employee = getEmployee(paramsList);
            employeeInterface.add(employee);
            LOGGER.log(Level.INFO, "Created employee");
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            setDataToJsp(req, paramsList, errorsList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Отображение списка сотрудников.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        Utils.setDataToDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);
    }
}
