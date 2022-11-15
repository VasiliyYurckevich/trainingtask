package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;

/**
 * Содержит методы для работы объектов класса "Сотрудник" с БД.
 *
 * @author Q-YVV
 * @see Employee
 * @see IDao
 */
public class EmployeeDao implements IDao<Employee>, Serializable {

    /**
     * Хранит константу для колонки фамилии сотрудника в БД.
     */
    private static final String SURNAME = "surname";

    /**
     * Хранит константу для колонки имени сотрудника в БД.
     */
    private static final String FIRST_NAME = "first_name";

    /**
     * Хранит константу для колонки отчества сотрудника в БД.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Хранит константу для колонки должности сотрудника в БД.
     */
    private static final String POST = "post";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeDao.class.getName());

    /**
     * Константа для запроса на добавление нового сотрудника в БД.
     */
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO EMPLOYEE (surname, first_name, patronymic, post)" +
        " VALUES (:surname, :first_name, :patronymic, :post);";

    /**
     * Константа для запроса на получение всех сотрудников из БД.
     */
    private static final String SELECT_ALL_CLIENT = "SELECT * FROM EMPLOYEE;";

    /**
     * Константа для запроса на получение сотрудника по его ID из БД.
     */
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE id = :id;";

    /**
     * Константа для запроса на удаление данных сотрудника в БД.
     */
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM EMPLOYEE WHERE id = :id;";

    /**
     * Константа для запроса на обновление данных сотрудника в БД.
     */
    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE" +
        " SET surname = :surname, first_name = :first_name, patronymic = :patronymic, post = :post WHERE id = :id;";

    /**
     * Хранит константу для колонки ID сотрудника в БД.
     */
    private static final String ID = "id";

    @Override
    public void add(Employee employee, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_EMPLOYEE_SQL, connection)) {
            preparedStatementHelper.setString(SURNAME, employee.getSurname());
            preparedStatementHelper.setString(FIRST_NAME, employee.getFirstName());
            preparedStatementHelper.setString(PATRONYMIC, employee.getPatronymic());
            preparedStatementHelper.setString(POST, employee.getPost());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Created employee");
            }
            else {
                throw new DaoException("Error when adding a new employee to the database");
            }
        }
        catch (DaoException e) {
            throw e;
        }
        
    }

    @Override
    public void update(Employee employee, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_CLIENT_SQL, connection)) {
            preparedStatementHelper.setString(SURNAME, employee.getSurname());
            preparedStatementHelper.setString(FIRST_NAME, employee.getFirstName());
            preparedStatementHelper.setString(PATRONYMIC, employee.getPatronymic());
            preparedStatementHelper.setString(POST, employee.getPost());
            preparedStatementHelper.setInt(ID, employee.getId());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Employee with id {0} updated", employee.getId());
            }
            else {
                throw new DaoException("Error when trying to change employee data");

            }
        }
        catch (DaoException e) {
            throw e;
        }
        
    }

    @Override
    public void delete(Integer id, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_EMPLOYEE_SQL, connection)) {
            preparedStatementHelper.setInt(ID, id);
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Employee with id {0} deleted", id);
            }
            else {
                throw new DaoException("Error when trying to delete employee data");
            }
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
        
    }

    @Override
    public List<Employee> getAll(Connection connection) throws DaoException {

        List<Employee> employees = new ArrayList<>();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_CLIENT, connection);
            ResultSet resultSet = preparedStatementHelper.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = getEmployeeFromDB(resultSet);
                employees.add(employee);
            }

            return employees;
        }
        catch (SQLException e) {
            throw new DaoException("Error when getting all employee data", e);
        }
        
    }

    @Override
    public Employee getById(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_EMPLOYEE_BY_ID, connection)) {
            preparedStatementHelper.setInt(ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {

                if (resultSet.next()) {
                    return getEmployeeFromDB(resultSet);
                }
                else {
                    throw new DaoException("An employee with such data was not found");
                }
            }
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
        
    }

    /**
     * Заполнение объекта Employee данными из БД.
     *
     * @param resultSet результирующий набор данных
     * @return сотрудник
     */
    private static Employee getEmployeeFromDB(ResultSet resultSet) {
        try {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt(ID));
            employee.setSurname(resultSet.getString(SURNAME));
            employee.setFirstName(resultSet.getString(FIRST_NAME));
            employee.setPatronymic(resultSet.getString(PATRONYMIC));
            employee.setPost(resultSet.getString(POST));

            return employee;
        }
        catch (SQLException e) {
            throw new DaoException("Error when getting employee data from the database", e);
        }
    }
}
