package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

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
     * Обозначение действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Обозначение ID сотрудника.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Обозначение списка сотрудников.
     */
    private static final String EMPLOYEES_LIST = "employees";
    
    /**
     * Хранит текст для исключения при выборе неизвестной команды.
     */
    private static final String UNKNOWN_COMMAND_OF_EMPLOYEE_CONTROLLER = "Unknown command of Employee Controller:";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    /**
     * Сервис для работы с Employee.
     */
    private final EmployeeService employeeService = new EmployeeService();

    private final PageDataService<Employee> pageDataService = new EmployeePageDataService();

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

        Employee employee = pageDataService.getEntity(req);

        pageDataService.setDataToPage(req, employee);

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

        employeeService.delete(Integer.parseInt(req.getParameter(EMPLOYEE_ID)));

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

        Map<String, String> paramsMap = pageDataService.getDataFromPage(req);
        Map<String, String> errorsMap = ValidationService.checkEmployeeData(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            Employee employee = pageDataService.getEntity(req);
            pageDataService.setOutputDataToEntity(paramsMap, employee);
            employeeService.save(employee);
            resp.sendRedirect(EMPLOYEES_LIST);
        }
        else {
            pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP).forward(req, resp);
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

        HttpSession session = req.getSession();
        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("EMPLOYEE_LIST", employeeService.findAll());

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }
}
