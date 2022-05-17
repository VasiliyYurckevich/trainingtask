
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import model.Project;

/**
 * Class for working with database table "project".
 *<p> {@link DAOProject} using for write data about projects in database.</p>
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0

 */
@SuppressWarnings ({"checkstyle:MultipleStringLiterals", "checkstyle:MagicNumber"})
public class DAOProject implements DAOInterface<Project> {

    private static final String TABLE_NAME = "project";
    private static final String PROJECT_ID = "project_Id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    //SQL queries
    private static final String INSERT_PROJECT_SQL = "INSERT INTO " + TABLE_NAME + " (title, description) VALUES (?,?);";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM " + TABLE_NAME + ";";
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private static final String DELETE_PROJECT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private static final String UPDATE_PROJECT_SQL = "UPDATE " + TABLE_NAME +
        " SET title = ?,  description = ? WHERE " + PROJECT_ID + " = ?;";




    /**
     * Method to add new project to database.
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
        }
        finally {
            DBConnection.closeConnection();

        }
    }

    /**
     * Method to update project in database.
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
        }
        finally {
            DBConnection.closeConnection();
        }
    }
    /**
     * Method to delete project from database.
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
        }
        finally {
            DBConnection.closeConnection();

        }
    }
    /**
     * Method to get all projects from database.
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
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method to get project by id from database.
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
            try {
                preparedStatement.setInt(1, id);
            }
            catch (NullPointerException e) {
                preparedStatement.setNull(1, 0);
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
        finally {
            DBConnection.closeConnection();
        }
    }
}
