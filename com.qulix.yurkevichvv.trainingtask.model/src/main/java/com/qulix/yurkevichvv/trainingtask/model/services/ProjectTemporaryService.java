package com.qulix.yurkevichvv.trainingtask.model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionService;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Сервис для работы с временными данными проекта.
 *
 * @author Q-YVV
 */
public class  ProjectTemporaryService implements IProjectTemporaryService {

    /**
     * DAO для работы с БД задач.
     */
    private final TaskDao taskDao = new TaskDao();

    @Override
    public void save(ProjectTemporaryData projectTemporaryData) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            connection.setAutoCommit(false);

            ProjectDao projectDao = new ProjectDao();

            Integer projectId;
            if (projectTemporaryData.getId() == null) {
                projectId = projectDao.add(projectTemporaryData.getProject(), connection);
                projectTemporaryData.setId(projectId);
            }
            else {
                projectDao.update(projectTemporaryData.getProject(), connection);
                projectId = projectTemporaryData.getId();
            }
            updateTasks(projectTemporaryData, connection, projectId);
            ConnectionService.commitConnection(connection);
        }
        catch (SQLException | DaoException e) {
            ConnectionService.rollbackConnection(connection);
            throw new ServiceException("Error during saving project", e);
        }
    }

    @Override
    public List<Task> getProjectsTasks(Integer id) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            return taskDao.getProjectTasksInDB(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during getting project tasks", e);
        }
    }

    @Override
    public void deleteTask(ProjectTemporaryData project, Task task) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            project.getTasksList().remove(task);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting project task", e);
        }
    }

    @Override
    public void addTask(ProjectTemporaryData project, Task task) throws ServiceException {
        project.getTasksList().add(task);
    }

    @Override
    public void updateTask(ProjectTemporaryData project, Integer index, Task task) throws ServiceException {
        project.getTasksList().set(index, task);
    }

    /**
     * Обновляет задачи связанные с проектом.
     *
     * @param project проект
     * @param connection соединение
     */
    private void updateTasks(ProjectTemporaryData project, Connection connection, Integer projectId) {

        List<Task> tasksToDelete = taskDao.getProjectTasksInDB(project.getId(), connection);
        tasksToDelete.removeAll(project.getTasksList());
        tasksToDelete.forEach(task -> taskDao.delete(task.getId(), connection));

        project.getTasksList().forEach(task -> {
            task.setProjectId(projectId);
            if (task.getId() == null) {
                taskDao.add(task, connection);
            }
            else {
                taskDao.update(task, connection);
            }
        }
        );
    }
}
