package com.qulix.yurkevichvv.trainingtask.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.DAO.DAOEmployee;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.model.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(displayName = "EmployeeServlet", urlPatterns = {"/employees"})
public class EmployeeController extends HttpServlet {

    private static final long serialVersionUID = 12345L;
    private DAOInterface<Employee> employeeInterface;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            employeeInterface = new DAOEmployee();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String theCommand = req.getParameter("command");

            if (theCommand == null) {
                theCommand = "/list";
            }

            switch (theCommand) {
                case "/list":
                    listEmployees(req, resp);
                    break;
                case "/add":
                    addEmployee(req, resp);
                    break;
                case "/update":
                    updateEmployee(req, resp);
                    break;
                case "/delete":
                    deleteEmployee(req, resp);
                    break;
                case "/load":
                    loadEmployee(req, resp);
                    break;
                case "/new ":
                    addEmployeeForm(req, resp);
                    break;
                case "/edit":
                    updateEmployeeForm(req, resp);
                default:
                    listEmployees(req, resp);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("employeeId"));
        Employee existingEmployee = employeeInterface.getById(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("employee-form.jsp");
        req.setAttribute("user", existingEmployee);
        dispatcher.forward(req, resp);
    }

    private void addEmployeeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-employee-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void loadEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theEmployeeId = req.getParameter("employeeId");
        Employee theEmployee = employeeInterface.getById(Integer.parseInt(theEmployeeId));

        req.setAttribute("THE_ID", theEmployee.getId());
        req.setAttribute("THE_SURNAME", theEmployee.getSurname());
        req.setAttribute("THE_FIRSTNAME", theEmployee.getFirstName());
        req.setAttribute("THE_LASTNAME", theEmployee.getLastName());
        req.setAttribute("THE_POST", theEmployee.getPost());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/update-employee-form.jsp");
        dispatcher.forward(req, resp);
    }


    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

        Integer theEmployeeId = Integer.valueOf(req.getParameter("employeeId"));
        employeeInterface.delete(theEmployeeId);
        listEmployees(req, resp);

    }

    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("employeeId"));
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String post= req.getParameter("post");

        Employee theEmployee = new Employee(id, surname, firstName, lastName, post);

        employeeInterface.update(theEmployee);

        listEmployees(req, resp);
    }



    private void addEmployee(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String surname = req.getParameter("surname");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String post = req.getParameter("post");

        Employee employee = new Employee( surname, firstName, lastName, post);

        employeeInterface.add(employee);

        listEmployees(req, resp);
    }

    private void listEmployees(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Employee> employees = employeeInterface.getAll();

        req.setAttribute("EMPLOYEE_LIST", employees);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/employees.jsp");
        dispatcher.forward(req, resp);

    }


}