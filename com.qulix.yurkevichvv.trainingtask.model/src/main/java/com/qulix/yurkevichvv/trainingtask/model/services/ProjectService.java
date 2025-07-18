package com.qulix.yurkevichvv.trainingtask.model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.dao.ConnectionService;
import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

/**
 * Сервис для работы с Project.
 *
 * @author Q-YVV
 */
public class ProjectService implements IService<Project> {

    /**
     * DAO-объект для взаимодействия Project с БД.
     */
    private final ProjectDao projectDao = new ProjectDao();

    @Override
    public void delete(Integer id) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            projectDao.delete(id, connection);
        }
        catch (SQLException | DaoException e) {
            throw new ServiceException("Error during deleting project by id", e);
        }
    }

    @Override
    public List<Project> findAll() throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            return projectDao.getAll(connection);
        }
        catch (SQLException e) {
            throw new ServiceException("Error during getting all project", e);
        }
    }

    @Override
    public Project getById(Integer id) throws ServiceException {
        Connection connection = ConnectionService.getConnection();

        try (connection) {
            return projectDao.getById(id, connection);
        }
        catch (SQLException e) {
            throw new ServiceException("Error during getting project by id", e);
        }
    }
}
