package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.ConnectionProvider;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

/**
 * Содержит методы для работы обьектов класса "Сотрудник" с БД.
 *
 * @author Q-YVV
 * @see Employee
 * @see IDao
 */
public class EmployeeDAO implements IDao<Employee> {

    private static final String EMPLOYEE_ID = "id";

    private static final String SURNAME = "surname";

    private static final String FIRST_NAME = "first_name";

    private static final String PATRONYMIC = "patronymic";

    private static final String POST = "post";

    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());


    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO EMPLOYEE (surname, first_name, patronymic, post)" +
        " VALUES (?,?,?,?);";

    private static final String SELECT_ALL_CLIENT = "SELECT * FROM EMPLOYEE;";

    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE id = ?;";

    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM EMPLOYEE WHERE id = ?;";

    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE" +
        " SET surname = ?, first_name = ?, patronymic = ?, post = ? WHERE id = ?;";


    @Override
    public boolean add(Employee employee) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, employee.getSurname());
            preparedStatement.setString(index++, employee.getFirstName());
            preparedStatement.setString(index++, employee.getPatronymic());
            preparedStatement.setString(index, employee.getPost());

            return preparedStatement.execute();
        }
        catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при добавлении нового сотрудника в БД");
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    @Override
    public boolean update(Employee employee) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, employee.getSurname());
            preparedStatement.setString(index++, employee.getFirstName());
            preparedStatement.setString(index++, employee.getPatronymic());
            preparedStatement.setString(index++, employee.getPost());
            preparedStatement.setInt(index, employee.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при попытке изменить данные о сотруднике", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(Integer id) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при удалении сотрудника из базы данных",e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }


    @Override
    public List<Employee> getAll() throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENT)){
            List<Employee> employees = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

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
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных о сотрудниках", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    @Override
    public Employee getById(Integer id) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)){

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
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных о сотруднике", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }
}
