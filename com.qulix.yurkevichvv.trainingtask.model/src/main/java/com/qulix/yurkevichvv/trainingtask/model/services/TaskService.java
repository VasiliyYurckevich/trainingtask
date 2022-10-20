package com.qulix.yurkevichvv.trainingtask.model.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Сервис для работы с Task.
 *
 * @author Q-YVV
 */
public class TaskService implements Serializable, IService<Task> {

    /**
     * DAO-объект для взаимодействия Task с БД.
     */
    private final TaskDao taskDao = new TaskDao();

    @Override
    public void save(Task task) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            if (task.getId() == null) {
                taskDao.add(task, connection);
            }
            else {
                taskDao.update(task, connection);
            }
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during saving task", e);
        }
    }

    @Override
    public List<Task> findAll() throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return taskDao.getAll(connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during getting all tasks", e);
        }
    }

    @Override
    public Task getById(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return taskDao.getById(id, connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during getting task by id", e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            taskDao.delete(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting task by id", e);
        }
    }
}
