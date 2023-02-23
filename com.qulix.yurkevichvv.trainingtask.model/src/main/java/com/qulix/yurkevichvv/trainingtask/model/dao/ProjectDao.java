
package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

/**
 * Содержит методы для работы объектов класса "Проект" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Project
 */
public class ProjectDao implements IDao<Project> {

    /**
     * ID проекта в БД.
     */
    private static final String ID = "project.id";

    /**
     * Название проекта в БД.
     */
    private static final String TITLE = "project.title";

    /**
     * Описание сотрудника в БД.
     */
    private static final String DESCRIPTION = "project.description";

    /**
     * Логгер для ведения журнала действий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectDao.class.getName());

    /**
     * Запрос добавления проекта в БД.
     */
    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description)" +
        " VALUES (:project.title, :project.description);";

    /**
     * Запрос всех проектов из БД.
     */
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT;";

    /**
     * Запрос получения проекта по его ID.
     */
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE id = :project.id;";

    /**
     * Запрос удаления проекта по его ID.
     */
    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE id = :project.id;";

    /**
     * Запрос обновления проекта в БД.
     */
    private static final String UPDATE_PROJECT_SQL =
        "UPDATE PROJECT SET title = :project.title, description = :project.description WHERE id = :project.id;";

    @Override
    public Integer add(Project project, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_PROJECT_SQL, connection)) {
            preparedStatementHelper.setString(TITLE, project.getTitle());
            preparedStatementHelper.setString(DESCRIPTION, project.getDescription());
            Integer generatedKey = preparedStatementHelper.executeUpdateWithGeneratedKey();
            if (generatedKey > 0) {
                LOGGER.log(Level.INFO, "Project created");
                return generatedKey;
            }
            else {
                throw new DaoException("Error when adding a project to the database");
            }
        }
    }

    @Override
    public void update(Project project, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_PROJECT_SQL, connection)) {
            preparedStatementHelper.setString(TITLE, project.getTitle());
            preparedStatementHelper.setString(DESCRIPTION, project.getDescription());
            preparedStatementHelper.setInt(ID, project.getId());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Project with id {0} updated", project.getId());
            }
            else {
                throw new DaoException("Error when updating the project in the database");
            }
        }
    }

    @Override
    public void delete(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_PROJECT_SQL, connection)) {
            preparedStatementHelper.setInt(ID, id);
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Project with id {0} deleted", id);
            }
            else {
                throw new DaoException("Error when deleting a project from the database");
            }
        }
    }

    @Override
    public List<Project> getAll(Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_PROJECTS, connection);
            ResultSet resultSet = preparedStatementHelper.executeQuery()) {
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                Project project = getProjectFromDB(resultSet);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException e) {
            throw new DaoException("Error when working with ResultSet getting all project", e);
        }
    }

    /**
     * Заполнение объекта Project данными из БД.
     *
     * @param resultSet результирующий набор данных
     * @return проект
     */
    protected static Project getProjectFromDB(ResultSet resultSet) {

        try {
            Project project = new Project();
            project.setId(resultSet.getInt(ID));
            project.setTitle(resultSet.getString(TITLE));
            project.setDescription(resultSet.getString(DESCRIPTION));
            return project;
        }
        catch (SQLException e) {
            throw new DaoException("Error when retrieving task data from the database", e);
        }
    }

    @Override
    public Project getById(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_PROJECT_BY_ID, connection)) {
            preparedStatementHelper.setInt(ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
                if (resultSet.next()) {
                    return getProjectFromDB(resultSet);
                }
                else {
                    throw new DaoException("A project with such data was not found");
                }
            }
            catch (SQLException e) {
                throw new DaoException("Error when trying get project from ResultSet", e);
            }
        }
    }
}
