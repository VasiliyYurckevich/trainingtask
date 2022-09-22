package com.qulix.yurkevichvv.trainingtask.model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;

public class EmployeeService implements IService<Employee> {

    private final EmployeeDao employeeDao = new EmployeeDao();


    @Override
    public void add(Employee employee) {
        Connection connection = ConnectionController.getConnection();

        try(connection) {
            employeeDao.add(employee, connection);
        } catch (SQLException e) {
            throw new ServiceException(e);
        } finally {
            ConnectionController.closeConnection(connection);
        }
    }

    @Override
    public void update(Employee employee) {
        Connection connection = ConnectionController.getConnection();

        try(connection) {
            employeeDao.update(employee, connection);
        } catch (SQLException e) {
            throw new ServiceException(e);
        } finally {
            ConnectionController.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionController.getConnection();

        try {
            employeeDao.delete(id, connection);
        }
        finally {
            ConnectionController.closeConnection(connection);

        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.getAll();
    }

    @Override
    public Employee getById(Integer id) {
        return employeeDao.getById(id);
    }
}
