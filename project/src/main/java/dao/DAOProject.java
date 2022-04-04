package dao;

import connection.DBConnection;import model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with database table projects
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class DAOProject implements DAOInterface<Project>{

    private static String SCHEMA_NAME = "PUBLIC";
    private static String TABLE_NAME = "project";
    private static String PROJECT_ID = "project_Id";
    private static String TITLE = "title";
    private static String DESCRIPTION = "description";

    //SQL queries
    private final String INSERT_PROJECT_SQL = "INSERT INTO " + SCHEMA_NAME +"."+TABLE_NAME + " (title, description) VALUES (?,?);";
    private final String SELECT_ALL_PROJECTS = "SELECT * FROM "+SCHEMA_NAME +"."+ TABLE_NAME + ";";
    private final String SELECT_PROJECT_BY_ID = "SELECT * FROM " + SCHEMA_NAME +"."+TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private final String DELETE_PROJECT_SQL = "DELETE FROM " + SCHEMA_NAME +"."+TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private final String UPDATE_PROJECT_SQL = "UPDATE " + SCHEMA_NAME +"."+TABLE_NAME + " SET title = ?,  description = ? WHERE " + PROJECT_ID + " = ?;";




    /**
     * Method to add new project to database
     *
     * @param project - project to add
     * @return true if query is successful
     * @throws SQLException - if query is not successful
     */
    @Override
    public boolean add(Project project) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL);
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            boolean query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();

        }
    }

    /**
     * Method to update project in database
     *
     * @param project - project to update
     * @return true if query is successful
     * @throws SQLException - if query is not successful
     */
    @Override
    public boolean update(Project project) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL);
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setInt(3, project.getId());
            boolean query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();
        }
    }
    /**
     * Method to delete project from database
     *
     * @param id - id of project to delete
     * @return true if query is successful
     * @throws SQLException  - if query is not successful
     */
    @Override
    public boolean delete(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL);
            preparedStatement.setInt(1, id);
            boolean query = preparedStatement.execute();
            return query;
        }finally {
            DBConnection.closeConnection();

        }
    }
    /**
     * Method to get all projects from database
     *
     * @return list of projects
     * @throws SQLException - if query is not successful
     */
    @Override
    public List getAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            List<Project> projects = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt(PROJECT_ID));
                project.setTitle(resultSet.getString(TITLE));
                project.setDescription(resultSet.getString(DESCRIPTION));
                projects.add(project);
            }
            return projects;
        }finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method to get project by id from database
     *
     * @param id - id of project to get
     * @return project
     * @throws SQLException - if query is not successful
     */
    @Override
    public Project getById(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Project project = new Project();
            while (resultSet.next()) {
                project.setId(id);
                project.setTitle(resultSet.getString(TITLE));
                project.setDescription(resultSet.getString(DESCRIPTION));
            }
            return project;
        }finally {
            DBConnection.closeConnection();
        }
    }
}