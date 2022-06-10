package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;

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

    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE SET surname = ?, first_name = ?, patronymic = ?, post = ?" +
        " WHERE id = ?;";


    @Override
    public boolean add(Employee employee) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            preparedStatement.setString(Nums.ONE.getValue(), employee.getSurname());
            preparedStatement.setString(Nums.TWO.getValue(), employee.getFirstName());
            preparedStatement.setString(Nums.THREE.getValue(), employee.getPatronymic());
            preparedStatement.setString(Nums.FOUR.getValue(), employee.getPost());

            return preparedStatement.execute();
        }
        catch (SQLException e){
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при добавлении нового сотрудника в БД");
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public boolean update(Employee employee) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL)) {
            preparedStatement.setString(Nums.ONE.getValue(), employee.getSurname());
            preparedStatement.setString(Nums.TWO.getValue(), employee.getFirstName());
            preparedStatement.setString(Nums.THREE.getValue(), employee.getPatronymic());
            preparedStatement.setString(Nums.FOUR.getValue(), employee.getPost());
            preparedStatement.setInt(Nums.FIVE.getValue(), employee.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при попытке изменить данные о сотруднике", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)) {
            preparedStatement.setInt(Nums.ONE.getValue(), id);

            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при удалении сотрудника из базы данных",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public List<Employee> getAll() throws DaoException {

        Connection connection = DBConnection.getConnection();

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
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных о сотрудниках", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public Employee getById(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)){
            if (id == null) {
                preparedStatement.setNull(Nums.ONE.getValue(), Nums.ZERO.getValue());
            }
            else {
                preparedStatement.setInt(Nums.ONE.getValue(), id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();

            while (resultSet.next()) {
                if (resultSet.getInt(EMPLOYEE_ID) != 0) {
                    employee.setId(resultSet.getInt(EMPLOYEE_ID));
                }
                else {
                    employee.setId(null);
                }
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setPatronymic(resultSet.getString(PATRONYMIC));
                employee.setPost(resultSet.getString(POST));
            }
            return employee;
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных о сотруднике", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }
}
