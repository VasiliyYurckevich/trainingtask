/**
 * Info about this package doing something for package-info.java file.
 */
package com.qulix.yurkevichvv.trainingtask.main.controllers;

import com.qulix.yurkevichvv.trainingtask.main.dao.EmployeeDAO;
import com.qulix.yurkevichvv.trainingtask.main.dao.IDao;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;
import com.qulix.yurkevichvv.trainingtask.main.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.main.validation.ValidationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Содержит сервлеты для выполнения действий объектов класса "Сотрудник".
 *
 * @author Q-YVV
 */
public class EmployeeController extends HttpServlet {

    private static final String ADD_EMPLOYEE_FORM_JSP = "/add-employee-form.jsp";

    private static final String EDIT_EMPLOYEE_FORM_JSP = "/edit-employee-form.jsp";

    private static final String LIST = "/list";

    private static final String ACTION = "action";

    private static final String EMPLOYEE_ID = "employeeId";

    private static final String SURNAME = "surname";

    private static final String FIRST_NAME = "firstName";

    private static final String PATRONYMIC = "patronymic";

    private static final String POST = "post";

    private static final String EMPLOYEES_LIST = "employees";

    /**
     * Интерфейс для взаимодействия с базой данных.
     */
    private IDao<Employee> employeeInterface;

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        employeeInterface = new EmployeeDAO();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
        } catch (IOException | DaoException | ServletException e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
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
        } catch (IOException | DaoException | ServletException e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }


    /**
     * Отображает форму для редактирования сотрудника.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения Бд.
     * @throws ServletException исключение сервлета.
     * @throws IOException исключение ввода-вывода.
     */
    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws  ServletException, IOException, DaoException {

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
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException исключение сервлета.
     * @throws IOException исключение ввода-вывода.
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет сотрудника из БД.
     *
     * @param req  запрос.
     * @param resp ответ.
     * @throws ServletException исключение сервлета.
     * @throws IOException      исключение ввода-вывода.
     * @throws SQLException     исключение БД.
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, IOException {
        Integer employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        employeeInterface.delete(employeeId);
        resp.sendRedirect(EMPLOYEES_LIST);
        LOGGER.log(Level.INFO, "Employee with id {0} deleted", employeeId);
    }

    /**
     * Запись отредактированного сотрудника в БД.
     *
     * @param req  запрос.
     * @param resp ответ.
     * @throws ServletException исключение сервлета.
     * @throws IOException      исключение ввода-вывода.
     * @throws SQLException     исключение БД.
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, DaoException, IOException {
        int employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        List<String> paramsList = getDataFromJsp(req);
        List<String> errorsList = ValidationService.employeeValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
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
     * Создание сотрудника с полученными параметрами.
     *
     * @param paramsList список параметров.
     * @return сотрудник.
     */
    private static Employee getEmployee(List<String> paramsList) {
        Employee theEmployee = new Employee();
        theEmployee.setSurname(paramsList.get(Nums.ZERO.getValue()));
        theEmployee.setFirstName(paramsList.get(Nums.ONE.getValue()));
        theEmployee.setPatronymic(paramsList.get(Nums.TWO.getValue()));
        theEmployee.setPost(paramsList.get(Nums.THREE.getValue()));
        return theEmployee;
    }

    /**
     * Заполнение формы данными о сотруднике.
     *
     * @param req запрос.
     * @param paramsList список параметров.
     * @param errorsList список ошибок.
     */
    private void setDataToJsp(HttpServletRequest req, List<String> paramsList, List<String> errorsList) {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(SURNAME, paramsList.get(Nums.ZERO.getValue()).trim());
        req.setAttribute(FIRST_NAME, paramsList.get(Nums.ONE.getValue()).trim());
        req.setAttribute(PATRONYMIC, paramsList.get(Nums.TWO.getValue()).trim());
        req.setAttribute(POST, paramsList.get(Nums.THREE.getValue()).trim());
    }

    /**
     * Получение данных о сотруднике из формы.
     *
     * @param req запрос.
     * @return список параметров.
     */
    private List<String> getDataFromJsp(HttpServletRequest req) {
        List<String> params = new ArrayList<>(Nums.FOUR.getValue());
        params.add(req.getParameter(SURNAME));
        params.add(req.getParameter(FIRST_NAME));
        params.add(req.getParameter(PATRONYMIC));
        params.add(req.getParameter(POST));
        return params;
    }

    /**
     * Валидация и добавление сотрудника в БД.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключение БД.
     * @throws ServletException исключение сервлета.
     * @throws IOException исключение ввода-вывода.
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        List<String> paramsList = getDataFromJsp(req);
        List<String> errorsList = ValidationService.employeeValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
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
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключение БД.
     * @throws ServletException исключение сервлета.
     * @throws IOException исключение ввода-вывода.
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
        throws  DaoException, ServletException, IOException {
        Utils.setDataOfDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);
    }
}
