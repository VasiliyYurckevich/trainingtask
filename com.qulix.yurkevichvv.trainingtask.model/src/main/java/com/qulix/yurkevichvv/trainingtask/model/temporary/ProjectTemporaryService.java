package com.qulix.yurkevichvv.trainingtask.model.temporary;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionService;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;

public class  ProjectTemporaryService implements IProjectTemporaryService {

    private TaskDao taskDao = new TaskDao();

    @Override
    public void save(ProjectTemporaryData projectTemporaryData) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            connection.setAutoCommit(false);

            ProjectDao projectDao = new ProjectDao();
            Integer generatedKey;
            if (projectTemporaryData.getId() == null) {
                generatedKey = projectDao.add(projectTemporaryData.getProject(), connection);
                projectTemporaryData.setId(generatedKey);
            }
            else {
                projectDao.update(projectTemporaryData.getProject(), connection);
                generatedKey = projectTemporaryData.getId();
            }
            updateTasks(projectTemporaryData,connection, generatedKey);
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
            List<Task> projects = taskDao.getProjectTasksInDB(id, connection);
            return projects;
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
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            project.getTasksList().add(task);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during adding project task", e);
        }
    }

    @Override
    public void updateTask(ProjectTemporaryData project, Integer index, Task task) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

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
    private void updateTasks(ProjectTemporaryData project, Connection connection, Integer generatedKey) {

        List<Task> tasksToDelete = taskDao.getProjectTasksInDB(project.getId(), connection);
        tasksToDelete.removeAll(project.getTasksList());
        tasksToDelete.forEach(task -> taskDao.delete(task.getId(), connection));

        project.getTasksList().forEach(task -> {

            task.setProjectId(generatedKey);
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
