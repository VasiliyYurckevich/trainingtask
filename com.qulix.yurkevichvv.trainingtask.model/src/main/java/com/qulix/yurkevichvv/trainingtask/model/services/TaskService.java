package com.qulix.yurkevichvv.trainingtask.model.services;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

import java.sql.Connection;
import java.util.List;

public class TaskService implements IService<Task> {

    private TaskDao taskDao = new TaskDao();

    @Override
    public void add(Task entity) {

    }

    @Override
    public void update(Task task) {
        Connection connection = ConnectionController.getConnection();

        try {
            if (task.getId() == null) {
                taskDao.add(task, connection);
            }
            else {
                taskDao.update(task, connection);
            }
        }
        finally {
            ConnectionController.closeConnection(connection);
        }
    }



    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public Task getById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionController.getConnection();

        try {
            taskDao.delete(id, connection);
        }
        finally {
            ConnectionController.closeConnection(connection);

        }
    }
}
