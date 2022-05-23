import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with database table "task".
 *<p> {@link DAOTask} using for write data about task in database.</p>
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
public class DAOTask implements DAOInterface<Task> {

    private static final  String TASK_ID = "id";

    private static final  String STATUS = "status";

    private static final  String TITLE = "title";

    private static final  String PROJECT_ID = "project_id";



    private static final  String WORK_TIME = "work_time";

    private static final  String BEGIN_DATE = "begin_date";

    private static final  String END_DATE = "end_date";

    private static final  String EMPLOYEE_ID = "employee_id";



    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final int FIVE = 5;

    private static final int SIX = 6;

    private static final int SEVEN = 7;

    private static final int EIGHT = 8;



    private static final String INSERT_TASK_SQL = "INSERT INTO TASK" +

        " (status, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";

    private static final String SELECT_ALL_TASK = "SELECT * FROM TASK;";

    private static final String SELECT_TASK_BY_ID = "SELECT * FROM TASK WHERE id = ?;";

    private static final String SELECT_TASK_BY_PROJECT = "SELECT * FROM TASK WHERE project_id = ?;";

    private static final String DELETE_TASK_SQL = "DELETE FROM TASK WHERE id = ?;";

    private static final String UPDATE_TASK_SQL = "UPDATE TASK SET status = ?,  title = ?,  work_time = ?," +

        " begin_date = ?,  end_date = ?, project_id = ?, employee_id = ? WHERE id = ?;";



    /**
     * Method for adding new task to database.
     *
     */
    @Override
    public boolean add(Task task) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
        try {
            setDataInToStatement(task, preparedStatement);
            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for updating task in database.
     *
     */
    @Override
    public boolean update(Task task) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
        try {
            setDataInToStatement(task, preparedStatement);
            preparedStatement.setInt(EIGHT, task.getId());

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for setting data in to statement.
     *
     */
    private PreparedStatement setDataInToStatement(Task task, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(ONE, task.getStatus());
        preparedStatement.setString(TWO, task.getTitle());
        preparedStatement.setLong(THREE, task.getWorkTime());
        preparedStatement.setString(FOUR, task.getBeginDate().toString());
        preparedStatement.setString(FIVE, task.getEndDate().toString());
        if (task.getProjectId() == null) {
            preparedStatement.setNull(SIX, FIVE);
        }
        else {
            preparedStatement.setInt(SIX, task.getProjectId());
        }
        if (task.getEmployeeId() == null) {
            preparedStatement.setNull(SEVEN, FIVE);
        }
        else {
            preparedStatement.setInt(SEVEN, task.getEmployeeId());
        }
        return preparedStatement;
    }

    /**
     * Method for deleting task from database.
     *
     */
    @Override
    public boolean delete(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_SQL);
        try {
            preparedStatement.setInt(ONE, id);
            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for getting all tasks in project from database.
     *
     */
    public List<Task> getTasksInProject(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        List<Task> tasks = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT);

        try {
            preparedStatement.setString(ONE, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for getting all tasks from database.
     *
     */
    @Override
    public List<Task> getAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK);
        try {
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for getting list of tasks from database.
     *
     */
    private List<Task> getList(List<Task> tasks, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Task task = new Task();
            setDataFromJSP(resultSet, task);

            tasks.add(task);

        }
        return tasks;
    }

    /**
     * Method for getting task by id from database.
     *
     */
    @Override
    public Task getById(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);

        try {
            preparedStatement.setInt(ONE, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Task task = new Task();
            while (resultSet.next()) {
                setDataFromJSP(resultSet, task);
            }
            return task;
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    private Task setDataFromJSP(ResultSet resultSet, Task task) throws SQLException {
        task.setId(resultSet.getInt(TASK_ID));
        task.setStatus(resultSet.getString(STATUS));
        task.setTitle(resultSet.getString(TITLE));
        task.setWorkTime(resultSet.getLong(WORK_TIME));
        task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
        task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
        task.setProjectId(resultSet.getInt(PROJECT_ID));
        if (resultSet.getInt(EMPLOYEE_ID) != 0) {
            task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
        }
        else {
            task.setEmployeeId(null);
        }
        return task;
    }
}
