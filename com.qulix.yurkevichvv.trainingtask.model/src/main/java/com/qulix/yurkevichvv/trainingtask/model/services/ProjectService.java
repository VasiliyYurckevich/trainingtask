package com.qulix.yurkevichvv.trainingtask.model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

public class ProjectService implements IProjectService, IService<Project>  {
    private final ProjectDao projectDao = new ProjectDao();

    private final TaskDao taskDao = new TaskDao();

    @Override
    public void save(Project project) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            connection.setAutoCommit(false);

            if (project.getId() == null){
                projectDao.add(project, connection);
                Integer generatedKey = projectDao.getGeneratedKey();
                project.getTasksList().forEach(task -> task.setProjectId(generatedKey));
            } else {
                projectDao.update(project,connection);
            }
            updateTasks(project, connection);

            ConnectionController.commitConnection(connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error during saving project", e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionController.getConnection();

        try (connection) {
            projectDao.delete(id, connection);
        }
        catch (SQLException e) {
            ConnectionController.rollbackConnection(connection);
            throw new ServiceException("Error deleting project by id", e);
        }

    }

    @Override
    public List<Project> findAll() {
        return projectDao.getAll();
    }

    @Override
    public Project getById(Integer id) {
        return projectDao.getById(id);
    }

    @Override
    public List<Task> getProjectsTasks(Project project) {
        return project.getTasksList();
    }

    @Override
    public void deleteTask(Project project, Task task) {
        project.getDeletedTasksList().add(task);
        project.getTasksList().remove(task);
    }

    @Override
    public void addTask(Project project, Task task) {
        project.getTasksList().add(task);
    }

    @Override
    public void updateTask(Project project, Integer index, Task task) {
        project.getTasksList().set(index, task);
    }

    private void updateTasks(Project project, Connection connection) {

        project.getTasksList().forEach(task -> {
            if (task.getId() == null) {
                taskDao.add(task, connection);
            } else {
                taskDao.update(task, connection);
            }
        });

        project.getDeletedTasksList().stream().filter(task -> task.getId() != null).
            forEach(task -> taskDao.delete(task.getId(), connection));
    }

}
