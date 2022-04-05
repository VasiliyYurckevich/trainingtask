package controller;


import dao.DAOEmployee;
import dao.DAOInterface;
import model.Employee;
import utilits.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for Employees
 *
 * @author YurkevichVV
 * @version 1.0
 * @since 1.0
 *
 */
public class EmployeeController extends HttpServlet {

    private static final long serialVersionUID = 12345L;
    private DAOInterface<Employee> employeeInterface;

    public static final Logger logger = Logger.getLogger(EmployeeController.class.getName());
    /**
     * Initialize the Employee servlet.
     */
    public void init() throws ServletException,NullPointerException {
        super.init();
        employeeInterface = new DAOEmployee();

    }
    /**
     * Processes requests for HTTP  POST method.
     * methods.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
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
        }catch (SQLException e){
            logger.warning((Supplier<String>) e);
        }
    }
    /**
     * Processes requests for HTTP  GET method.
     * methods.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
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
        }catch (SQLException e){
            logger.warning((Supplier<String>) e);

        }

    }

    /**
     * Method for open update employee form
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws Exception
     */
    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer employeeId = Integer.valueOf(req.getParameter("employeeId"));
        Employee existingEmployee= employeeInterface.getById(employeeId);
        req.setAttribute("employeeId",employeeId);
        req.setAttribute("surname", Util.htmlSpecialChars(existingEmployee.getSurname()));
        req.setAttribute("firstName", Util.htmlSpecialChars(existingEmployee.getFirstName()));
        req.setAttribute("patronymic", Util.htmlSpecialChars(existingEmployee.getPatronymic()));
        req.setAttribute("post", Util.htmlSpecialChars(existingEmployee.getPost()));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-employee-form.jsp");
        existingEmployee.setId(employeeId);
        req.setAttribute("employee", existingEmployee);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add employee form
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws Exception
     */
    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-employee-form.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete employee
     * @param req servlet request
     * @param resp servlet response
     * @throws Exception
     */
    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp){
        try {
            Integer theEmployeeId = Integer.valueOf(req.getParameter("employeeId"));
            employeeInterface.delete(theEmployeeId);
            listEmployees(req, resp);
            logger.info("Employee with id " + theEmployeeId + " deleted");
        } catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);

        }

    }

    /**
     * Method for update employee
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws Exception
     */
    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int employeeId = Integer.parseInt(req.getParameter("employeeId"));
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String patronymic = req.getParameter("patronymic");
        String post= req.getParameter("post");
        Employee theEmployee = new Employee(employeeId, surname, firstName, patronymic, post);
        employeeInterface.update(theEmployee);
        listEmployees(req, resp);
        logger.info("Employee with id "+employeeId+" update");
    }


    /**
     * Method for add employee
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws Exception
     */
    private void addEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

       String surname = req.getParameter("surname");
       String firstName = req.getParameter("firstName");
       String patronymic = req.getParameter("patronymic");
       String post = req.getParameter("post");
       Employee employee = new Employee( surname, firstName,patronymic , post);
       employeeInterface.add(employee);
       listEmployees(req, resp);
       logger.info("Employee created");

    }

    /**
     * Method for get list employees
     *
     * @param req
     * @param resp
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    private void listEmployees(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Employee> employees = employeeInterface.getAll();
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("EMPLOYEE_LIST", employees);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);
    }
}