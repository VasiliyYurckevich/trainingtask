import java.io.*;
import java.sql.SQLException;
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

    private DAOInterface<Employee> employeeInterface;

    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

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
            String action = req.getParameter("action");
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
            String action = req.getParameter("action");

            if (action == null) {
                action = "/list";
            }

            switch (action) {
                case "/list":
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
        Integer employeeId = Integer.valueOf(req.getParameter("employeeId"));
        Employee existingEmployee = employeeInterface.getById(employeeId);
        req.setAttribute("employeeId", employeeId);
        req.setAttribute("surname", existingEmployee.getSurname());
        req.setAttribute("firstName", existingEmployee.getFirstName());
        req.setAttribute("patronymic", existingEmployee.getPatronymic());
        req.setAttribute("post", existingEmployee.getPost());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-employee-form.jsp");
        existingEmployee.setId(employeeId);
        req.setAttribute("employee", existingEmployee);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add employee form.
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-employee-form.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete employee.
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        Integer theEmployeeId = Integer.valueOf(req.getParameter("employeeId"));
        employeeInterface.delete(theEmployeeId);
        listEmployees(req, resp);
        LOGGER.info("Employee with id " + theEmployeeId + " deleted");
    }

    /**
     * Method for update employee.
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        int employeeId = Integer.parseInt(req.getParameter("employeeId"));
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String patronymic = req.getParameter("patronymic");
        String post = req.getParameter("post");
        Employee theEmployee = new Employee(employeeId, surname, firstName, patronymic, post);
        employeeInterface.update(theEmployee);
        listEmployees(req, resp);
        LOGGER.info("Employee with id " + employeeId + " update");
    }


    /**
     * Method for add employee.
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String patronymic = req.getParameter("patronymic");
        String post = req.getParameter("post");
        Employee employee = new Employee(surname, firstName, patronymic , post);
        employeeInterface.add(employee);
        listEmployees(req, resp);
        LOGGER.info("Employee created");

    }

    /**
     * Method for get list employees.
     *
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        List<Employee> employees = employeeInterface.getAll();
        req.setAttribute("EMPLOYEE_LIST", employees);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);
    }
}
