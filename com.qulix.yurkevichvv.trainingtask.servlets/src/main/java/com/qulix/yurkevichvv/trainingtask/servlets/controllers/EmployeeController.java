package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

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
     * Хранит название кейса для выбора списка сотрудников.
     */
    private static final String LIST = "/list";
    
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
    static {
        Handler handler = new StreamHandler(System.out, new SimpleFormatter());
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }
    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);
            if (action.equals("/save")) {
                saveEmployee(req, resp);
            }
            else {
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
                case "/edit":
                    editForm(req, resp);
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
    private void editForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Employee employee = getEmployee(req.getParameter(EMPLOYEE_ID));

        req.setAttribute(EMPLOYEE_ID, employee.getId());
        req.setAttribute(SURNAME, employee.getSurname());
        req.setAttribute(FIRST_NAME, employee.getFirstName());
        req.setAttribute(PATRONYMIC, employee.getPatronymic());
        req.setAttribute(POST, employee.getPost());

        req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
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
    private void saveEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Map<String, String> paramsMap = getDataFromJsp(req);
        Map<String, String> errorsMap = ValidationService.checkEmployeeData(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            Employee employee = getEmployee(req.getParameter(EMPLOYEE_ID));
            setEmployeeData(paramsMap, employee);
            employeeService.save(employee);
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            setDataToJsp(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
        }
    }

    private Employee getEmployee(String employeeId) {
        System.out.println("employeeId" + employeeId);
        if (employeeId.isBlank()) {
            return new Employee();
        }
        else {
           return employeeService.getById(Integer.valueOf(employeeId));
        }
    }

    /**
     * Устанавливает обновленные данные сотрудника.
     *
     * @param paramsMap список параметров
     */
    private static void setEmployeeData(Map<String, String> paramsMap, Employee employee) {
        employee.setSurname(paramsMap.get(SURNAME));
        employee.setFirstName(paramsMap.get(FIRST_NAME));
        employee.setPatronymic(paramsMap.get(PATRONYMIC));
        employee.setPost(paramsMap.get(POST));
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
        params.put(EMPLOYEE_ID, req.getParameter(EMPLOYEE_ID));
        params.put(SURNAME, req.getParameter(SURNAME));
        params.put(FIRST_NAME, req.getParameter(FIRST_NAME));
        params.put(PATRONYMIC, req.getParameter(PATRONYMIC));
        params.put(POST, req.getParameter(POST));
        return params;
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
        req.getSession().setAttribute("EMPLOYEE_LIST", employeeService.findAll());

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
