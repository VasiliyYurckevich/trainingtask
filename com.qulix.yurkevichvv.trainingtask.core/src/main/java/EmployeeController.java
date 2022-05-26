import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Employees.
 *
 *<p> {@link EmployeeController} using to interact with Employees in application.</p>
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
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


    private DAOInterface<Employee> employeeInterface;

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());


    /**
     * Initialize the Employee servlet.
     */
    public void init() throws ServletException, NullPointerException {
        super.init();
        employeeInterface = new DAOEmployee();

    }

    /**
     * Processes requests for HTTP  POST methods.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }
    }

    /**
     * Processes requests for HTTP  GET methods.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));

        }

    }

    /**
     * Method for open update employee form.
     */
    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Integer employeeId = Integer.valueOf(req.getParameter(EMPLOYEE_ID));
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
     * Method for open add employee form.
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete employee.
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        Integer theEmployeeId = Integer.valueOf(req.getParameter(EMPLOYEE_ID));
        employeeInterface.delete(theEmployeeId);
        listEmployees(req, resp);
        LOGGER.info("Employee with id " + theEmployeeId + " deleted");
    }

    /**
     * Method for update employee.
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        int employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        List<String> paramsList  = getDataFromJSP(req);
        List<String> errorsList = ValidationService.employeeValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            req.setAttribute(EMPLOYEE_ID, employeeId);
            Employee theEmployee = new Employee( paramsList.get(0), paramsList.get(1), paramsList.get(2), paramsList.get(Nums.THREE.getValue()));
            theEmployee.setId(employeeId);
            employeeInterface.update(theEmployee);
            listEmployees(req, resp);
            LOGGER.info("Updated employee with id " + employeeId);
        }
        else {
            req.setAttribute(EMPLOYEE_ID, employeeId);
            setDataToJSP(req,  paramsList, errorsList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_EMPLOYEE_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    private void setDataToJSP(HttpServletRequest req, List<String> paramsList, List<String> errorsList)
        throws ServletException, IOException {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(SURNAME, paramsList.get(Nums.ZERO.getValue()).trim());
        req.setAttribute(FIRST_NAME, paramsList.get(Nums.ONE.getValue()).trim());
        req.setAttribute(PATRONYMIC, paramsList.get(Nums.TWO.getValue()).trim());
        req.setAttribute(POST, paramsList.get(Nums.THREE.getValue()).trim());
    }
    private List<String> getDataFromJSP(HttpServletRequest req) throws ServletException, IOException {
        List<String> params = new ArrayList<>(Nums.FOUR.getValue());
        params.add(req.getParameter(SURNAME));
        params.add(req.getParameter(FIRST_NAME));
        params.add(req.getParameter(PATRONYMIC));
        params.add(req.getParameter(POST));
        return params;
    }


    /**
     * Method for add employee.
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        List<String> paramsList  = getDataFromJSP(req);
        List<String> errorsList = ValidationService.employeeValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            Employee employee = new Employee(paramsList.get(Nums.ZERO.getValue()),
                paramsList.get(Nums.ONE.getValue()), paramsList.get(Nums.TWO.getValue()), paramsList.get(Nums.THREE.getValue()));
            employeeInterface.add(employee);
            LOGGER.info("Employee created");
            listEmployees(req, resp);
        }
        else {
            setDataToJSP(req, paramsList, errorsList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_EMPLOYEE_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Method for get list employees.
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Utils.setDataOfDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);
    }
}
