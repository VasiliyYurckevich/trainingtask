package com.qulix.yurkevichvv.trainingtask.model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
public class ProjectTemporaryService implements IProjectTemporaryService {

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

            if (projectTemporaryData.getId() == null) {
                Integer projectId = projectDao.add(projectTemporaryData.getProject(), connection);
                projectTemporaryData.setId(projectId);
            }
            else {
                projectDao.update(projectTemporaryData.getProject(), connection);
            }

            updateTasks(projectTemporaryData, connection);
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
            return taskDao.getProjectTasks(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during getting project tasks", e);
        }
    }

    @Override
    public void deleteTask(ProjectTemporaryData projectTemporaryData, Task task) throws ServiceException {
        projectTemporaryData.getTasksList().remove(task);
    }

    @Override
    public void addTask(ProjectTemporaryData projectTemporaryData, Task task) throws ServiceException {
        task.setProject(projectTemporaryData.getProject());
        projectTemporaryData.getTasksList().add(task);
    }

    @Override
    public void updateTask(ProjectTemporaryData projectTemporaryData, Integer index, Task task) throws ServiceException {
        task.setProject(projectTemporaryData.getProject());
        projectTemporaryData.getTasksList().set(index, task);
    }

    /**
     * Обновляет задачи связанные с проектом.
     *
     * @param projectTemporaryData проект
     * @param connection соединение
     */
    private void updateTasks(ProjectTemporaryData projectTemporaryData, Connection connection) {

        List<Task> tasksToDelete = taskDao.getProjectTasks(projectTemporaryData.getId(), connection);

        //очищает список для удаления от содержащихся в обновленном списке задач проекта
        tasksToDelete.removeIf(existingTask -> projectTemporaryData.getTasksList()
            .stream().anyMatch(updatedTask -> existingTask.getId().equals(updatedTask.getId())));

        tasksToDelete.forEach(task -> taskDao.delete(task.getId(), connection));

        //добавление/обновление задач проекта
        projectTemporaryData.getTasksList().forEach(task -> {
            if (Optional.ofNullable(task.getId()).isEmpty()) {
                taskDao.add(task, connection);
            }
            else {
                taskDao.update(task, connection);
            }
        }
        );
    }
}
