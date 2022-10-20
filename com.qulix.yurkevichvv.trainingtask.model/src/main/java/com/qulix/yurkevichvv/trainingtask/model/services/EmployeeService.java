package com.qulix.yurkevichvv.trainingtask.model.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;

/**
 * Сервис для работы с Employee.
 *
 * @author Q-YVV
 */
public class EmployeeService implements Serializable, IService<Employee> {

    /**
     * DAO-объект для взаимодействия Employee с БД.
     */
    private final EmployeeDao employeeDao = new EmployeeDao();

    @Override
    public void save(Employee employee) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            if (employee.getId() == null) {
                employeeDao.add(employee, connection);
            }
            else {
                employeeDao.update(employee, connection);
            }
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during saving employee", e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            employeeDao.delete(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting employee by id", e);
        }

    }

    @Override
    public List<Employee> findAll() throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return employeeDao.getAll(connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during getting all employees", e);
        }
    }

    @Override
    public Employee getById(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return employeeDao.getById(id, connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during get employee by id", e);
        }
    }
}
