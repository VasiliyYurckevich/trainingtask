package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import model.Employee;

/**
 * Class for working with database table "employee".
 *
 *<p> {@link DAOEmployee} using for write data about employee in database.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code DAOEmployee daoEmployee = new DAOEmployee();}
 * {@code daoEmployee.add(employee);}
 * {@code daoEmployee.delete(employee);}
 * {@code daoEmployee.update(2);}
 * {@code daoEmployee.getById(1);}
 * {@code daoEmployee.getAll();}
 *
 * </pre>
 *
 * <h2>Synchronization</h2>
 * <p>
 * This class is not guaranteed to be thread-safe so it should be synchronized externally.
 * </p>
 *
 * <h2>Known bugs</h2>
 * {@link DAOEmployee} does not handle overflows.
 *
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 * @see  DAOTask
 * @see  DAOProject
 * @see  DAOInterface
 * @see  Employee
 * @see  DBConnection
 */
@SuppressWarnings ({"checkstyle:MultipleStringLiterals", "checkstyle:MagicNumber"})
public class DAOEmployee implements DAOInterface<Employee> {

    private static final String SCHEMA_NAME = "PUBLIC";
    private static final String TABLE_NAME = "EMPLOYEE";
    //Columns names
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String SURNAME = "surname";
    private static final String FIRST_NAME = "first_name";
    private static final String PATRONYMIC = "patronymic";
    private static final String POST = "post";

    //SQL queries
    private static final  String INSERT_EMPLOYEE_SQL = "INSERT INTO " + TABLE_NAME +
        " (surname, first_name, patronymic, post) VALUES (?,?,?,?);";
    private static final String SELECT_ALL_CLIENT = "SELECT * FROM "  + TABLE_NAME + ";";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMPLOYEE_ID + " = ?;";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + EMPLOYEE_ID + " = ?;";
    private static final String UPDATE_CLIENT_SQL = "UPDATE " + TABLE_NAME +
        " SET surname = ?, first_name = ?, patronymic = ?, post = ? WHERE employee_id = ?;";




    /**
     * Method for writing new employee to database.
     *
     * @param employee - employee object
     * @return true if query is successful
     * @throws SQLException - if query is not successful
     */
    @Override
    public boolean add(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setString(4, employee.getPost());

            boolean query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }
    /**
     * Method for rewriting  employee to database.
     *
     * @param employee - employee object
     * @return list of employees
     * @throws SQLException - if query is not successful
     */
    @Override
    public boolean update(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL);
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setString(4, employee.getPost());
            preparedStatement.setInt(5, employee.getId());
            boolean query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for deleting employee from database.
     *
     * @param  id - employee id
     * @return true if query is successful
     * @throws SQLException - if query is not successful
     */

    @Override
    public boolean delete(Integer id) throws SQLException {

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);
            preparedStatement.setInt(1, id);
            boolean query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }

    }
    /**
     * Method for getting all employees from database.
     *
     * @return list of employees
     * @throws SQLException - if query is not successful
     */
    @Override
    public List<Employee> getAll() throws SQLException {

        Connection connection = DBConnection.getConnection();

        try {
            List<Employee> employees = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_CLIENT);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt(EMPLOYEE_ID));
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setPatronymic(resultSet.getString(PATRONYMIC));
                employee.setPost(resultSet.getString(POST));
                employees.add(employee);
            }
            return employees;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for getting employee by id.
     *
     * @param id - employee id
     * @return employee object
     * @throws SQLException - if query is not successful
     */
    @Override
    public Employee getById(Integer  id) throws SQLException {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            while (resultSet.next()) {
                employee.setId(resultSet.getInt(EMPLOYEE_ID));
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setPatronymic(resultSet.getString(PATRONYMIC));
                employee.setPost(resultSet.getString(POST));
            }

            return employee;
        }
        finally {
            DBConnection.closeConnection();

        }
    }
}
