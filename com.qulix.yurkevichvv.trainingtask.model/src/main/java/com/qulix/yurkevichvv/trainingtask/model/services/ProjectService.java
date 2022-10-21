package com.qulix.yurkevichvv.trainingtask.model.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Сервис для работы с Project.
 *
 * @author Q-YVV
 */
public class ProjectService implements IProjectService, Serializable {

    /**
     * DAO-объект для взаимодействия Project с БД.
     */
    private final ProjectDao projectDao = new ProjectDao();

    /**
     * DAO-объект для взаимодействия Task с БД.
     */
    private final TaskDao taskDao = new TaskDao();

    @Override
    public void save(Project project) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            connection.setAutoCommit(false);

            if (project.getId() == null) {
                projectDao.add(project, connection);
                Integer generatedKey = projectDao.getGeneratedKey();
                project.getTasksList().forEach(task -> task.setProjectId(generatedKey));
            }
            else {
                projectDao.update(project, connection);
            }
            updateTasks(project, connection);

            ConnectionController.commitConnection(connection);
        }
        catch (SQLException | DaoException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error during saving project", e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            projectDao.delete(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting project by id", e);
        }

    }

    @Override
    public List<Project> findAll() throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return projectDao.getAll(connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during getting all project", e);
        }
    }

    @Override
    public Project getById(Integer id) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return projectDao.getById(id, connection);
        }
        catch (DaoException | SQLException e) {
            throw new ServiceException("Error during getting project by id", e);
        }
    }

    @Override
    public List<Task> getProjectsTasks(Project project) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            return project.getTasksList();
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during getting project tasks", e);
        }
    }

    @Override
    public void deleteTask(Project project, Task task) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            project.getDeletedTasksList().add(task);
            project.getTasksList().remove(task);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting project task", e);
        }
    }

    @Override
    public void addTask(Project project, Task task) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            project.getTasksList().add(task);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during adding project task", e);
        }
    }

    @Override
    public void updateTask(Project project, Integer index, Task task) throws ServiceException {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            project.getTasksList().set(index, task);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during updating project task", e);
        }
    }

    /**
     * Обновляет задачи связанные с проектом.
     *
     * @param project проект
     * @param connection соединение
     */
    private void updateTasks(Project project, Connection connection) {

        project.getTasksList().forEach(task -> {
            if (task.getId() == null) {
                taskDao.add(task, connection);
            }
            else {
                taskDao.update(task, connection);
            }
        }
        );

        project.getDeletedTasksList().stream().filter(task -> task.getId() != null).
            forEach(task -> taskDao.delete(task.getId(), connection));
    }
}
