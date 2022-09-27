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
    public void save(Employee employee) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            if (employee.getId() == null){
                employeeDao.add(employee, connection);
            }
            else {
                employeeDao.update(employee, connection);
            }
        } catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error adding employee", e);
        }
    }


    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionController.getConnection();

        try (connection){
            System.out.println(id);
            employeeDao.delete(id, connection);
        } catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error deleting employee by id", e);        }

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
