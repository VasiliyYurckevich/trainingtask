package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.entity.Project;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;


/**
 * Содержит методы для работы объектов класса "Проект" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Project
 */
public class ProjectDao implements IDao<Project> {

    private static final String PROJECT_ID = "id";

    private static final String TITLE = "title";

    private static final String DESCRIPTION = "description";

    private static final Logger LOGGER = Logger.getLogger(ProjectDao.class.getName());


    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description) VALUES (?,?);";

    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT ;";

    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE id = ?;";

    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE id = ?;";

    private static final String UPDATE_PROJECT_SQL = "UPDATE PROJECT SET title = ?, description = ? WHERE id = ?;";



    @Override
    public boolean add(Project project) throws DaoException, PathNotValidException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, project.getTitle());
            preparedStatement.setString(index++, project.getDescription());
            return preparedStatement.execute();
        }catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при добавлении проекта в БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean update(Project project) throws DaoException, PathNotValidException {

        Connection connection = DBConnection.getConnection();

        try (CallableStatement preparedStatement = connection.prepareCall(UPDATE_PROJECT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, project.getTitle());
            preparedStatement.setString(index++, project.getDescription());
            preparedStatement.setInt(index++, project.getId());

            return preparedStatement.execute();
        }
        catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при обновлении проекта в БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean delete(Integer id) throws DaoException, PathNotValidException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL)){
            int index = 1;
            preparedStatement.setInt(index++, id);

            return preparedStatement.execute();
        }
        catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при удалении проекта из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public List<Project> getAll() throws DaoException, PathNotValidException {

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PROJECTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt(PROJECT_ID));
                project.setTitle(resultSet.getString(TITLE));
                project.setDescription(resultSet.getString(DESCRIPTION));
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении всех проектов из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public Project getById(Integer id) throws DaoException, PathNotValidException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            int index = 1;
            if (id == null) {
                preparedStatement.setNull(index, 0);
            }
            else {
                preparedStatement.setInt(index, id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Project project = new Project();
            while (resultSet.next()) {
                project.setId(id);
                project.setTitle(resultSet.getString(TITLE));
                project.setDescription(resultSet.getString(DESCRIPTION));
            }
            return project;
        }
        catch (SQLException e){
            LOGGER.severe(e.toString());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении проекта по id из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }
}
