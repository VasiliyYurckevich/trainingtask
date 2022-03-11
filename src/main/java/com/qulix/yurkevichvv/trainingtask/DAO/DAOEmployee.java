package com.qulix.yurkevichvv.trainingtask.DAO;


import com.qulix.yurkevichvv.trainingtask.Connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOEmployee implements DAOInterface<Employee> {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private boolean query;

    private static String TABLE_NAME = "employee";
    private static String EMPLOYEE_ID = "employeeId";
    private static String SURNAME = "surname";
    private static String FIRST_NAME = "first_name";
    private static String LAST_NAME = "last_name";
    private static String POST = "post";

    private final String INSERT_EMPLOYEE_SQL = "INSERT INTO " + TABLE_NAME + " (surname, first_name, last_name, post) VALUES (?,?,?,?);";
    private final String SELECT_ALL_CLIENT = "SELECT * FROM "+ TABLE_NAME + ";";
    private final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMPLOYEE_ID + " = ?;";
    //private final String SELECT_EMPLOYEE_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME + " = ?;";
    private final String DELETE_EMPLOYEE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + EMPLOYEE_ID + " = ?;";
    private final String UPDATE_CLIENT_SQL = "UPDATE " + TABLE_NAME + " SET surname = ?, first_name = ?, last_name = ?, post = ? WHERE employeeId = ?;";





    @Override
    public boolean add(Employee employee) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getPost());
            query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL);
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getPost());
            preparedStatement.setInt(5, employee.getId());
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();
        }
    }



    @Override
    public boolean delete(Integer id) throws SQLException {

        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);
            preparedStatement.setInt(1, id);
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();
        }

    }

    @Override
    public List<Employee> getAll() throws SQLException {
        connection = DBConnection.getConnection();

        try {
            List<Employee> employees = new ArrayList<>();
            resultSet = connection.createStatement().executeQuery(SELECT_ALL_CLIENT);
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setLastName(resultSet.getString(LAST_NAME));
                employee.setPost(resultSet.getString(POST));
                employees.add(employee);
            }
            return employees;
        }finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public Employee getById(Integer  id) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            while (resultSet.next()) {
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setLastName(resultSet.getString(LAST_NAME));
                employee.setPost(resultSet.getString(POST));
            }

            return employee;
        }finally {
            DBConnection.closeConnection();

        }
    }
}