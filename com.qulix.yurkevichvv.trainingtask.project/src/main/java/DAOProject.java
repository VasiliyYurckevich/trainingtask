import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with database table "project".
 *<p> {@link DAOProject} using for write data about projects in database.</p>
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
public class DAOProject implements DAOInterface<Project> {

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final String PROJECT_ID = "project_Id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    
    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description) VALUES (?,?);";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT ;";
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE project_Id = ?;";
    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE project_Id = ?;";
    private static final String UPDATE_PROJECT_SQL = "UPDATE PROJECT SET title = ?,  description = ? WHERE project_Id = ?;";


    /**
     * Method to add new project to database.
     */
    @Override
    public boolean add(Project project) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL);
            preparedStatement.setString(ONE, project.getTitle());
            preparedStatement.setString(TWO, project.getDescription());

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method to update project in database.
     */
    @Override
    public boolean update(Project project) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL);
            preparedStatement.setString(ONE, project.getTitle());
            preparedStatement.setString(TWO, project.getDescription());
            preparedStatement.setInt(THREE, project.getId());

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method to delete project from database.
     */
    @Override
    public boolean delete(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL);
            preparedStatement.setInt(ONE, id);

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();

        }
    }

    /**
     * Method to get all projects from database.
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
     */
    @Override
    public Project getById(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID);
            try {
                preparedStatement.setInt(ONE, id);
            }
            catch (NullPointerException e) {
                preparedStatement.setNull(ONE, 0);
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
