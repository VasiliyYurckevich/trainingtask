package com.qulix.yurkevichvv.trainingtask.model.services;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaskService implements IService<Task> {

    private TaskDao taskDao = new TaskDao();

    @Override
    public void add(Task task) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            taskDao.add(task, connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error adding task", e);
        }
    }

    @Override
    public void update(Task task) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            taskDao.update(task, connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error updating task", e);
        }
    }



    @Override
    public List<Task> findAll() {
        return  taskDao.getAll();
    }

    @Override
    public Task getById(Integer id) {
        return taskDao.getById(id);
    }

    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            taskDao.delete(id, connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error during getting task by id", e);
        }
    }
}
