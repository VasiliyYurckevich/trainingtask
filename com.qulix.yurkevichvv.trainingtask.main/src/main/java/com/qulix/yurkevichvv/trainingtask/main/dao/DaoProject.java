package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.main.connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.entity.Project;
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;


/**
 * Содержит методы для работы обьектов класса "Проект" с БД.
 *
 * @author Q-YVV
 * @see DaoInterface
 * @see Project
 */
public class DaoProject implements DaoInterface<Project> {

    private static final String PROJECT_ID = "project_Id";

    private static final String TITLE = "title";

    private static final String DESCRIPTION = "description";
    
    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description) VALUES (?,?);";

    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT ;";

    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE project_Id = ?;";

    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE project_Id = ?;";

    private static final String UPDATE_PROJECT_SQL = "UPDATE PROJECT SET title = ?, description = ? WHERE project_Id = ?;";



    @Override
    public boolean add(Project project) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL)) {
            preparedStatement.setString(Nums.ONE.getValue(), project.getTitle());
            preparedStatement.setString(Nums.TWO.getValue(), project.getDescription());

            return preparedStatement.execute();
        }catch (SQLException e){
            throw new DaoException("Ошибка при добавлении проекта в БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean update(Project project) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL)) {
            preparedStatement.setString(Nums.ONE.getValue(), project.getTitle());
            preparedStatement.setString(Nums.TWO.getValue(), project.getDescription());
            preparedStatement.setInt(Nums.THREE.getValue(), project.getId());

            return preparedStatement.execute();
        }
        catch (SQLException e){
            throw new DaoException("Ошибка при обновлении проекта в БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean delete(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL)){
            preparedStatement.setInt(Nums.ONE.getValue(), id);

            return preparedStatement.execute();
        }
        catch (SQLException e){
            throw new DaoException("Ошибка при удалении проекта из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public List<Project> getAll() throws DaoException {

        Connection connection = DBConnection.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PROJECTS);) {
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
            throw new DaoException("Ошибка при получении всех проектов из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public Project getById(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            if (id == null) {
                preparedStatement.setNull(Nums.ONE.getValue(), Nums.ZERO.getValue());
            }
            else {
                preparedStatement.setInt(Nums.ONE.getValue(), id);
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
            throw new DaoException("Ошибка при получении проекта по id из БД",e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }
}
