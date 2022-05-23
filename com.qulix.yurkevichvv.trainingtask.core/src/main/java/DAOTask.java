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
            preparedStatement.setInt(Nums.EIGHT.getValue(), task.getId());

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
        preparedStatement.setString(Nums.ONE.getValue(), task.getStatus());
        preparedStatement.setString(Nums.TWO.getValue(), task.getTitle());
        preparedStatement.setLong(Nums.THREE.getValue(), task.getWorkTime());
        preparedStatement.setString(Nums.FOUR.getValue(), task.getBeginDate().toString());
        preparedStatement.setString(Nums.FIVE.getValue(), task.getEndDate().toString());
        if (task.getProjectId() == null) {
            preparedStatement.setNull(Nums.SIX.getValue(), Nums.ZERO.getValue());
        }
        else {
            preparedStatement.setInt(Nums.SIX.getValue(), task.getProjectId());
        }
        if (task.getEmployeeId() == null) {
            preparedStatement.setNull(Nums.SEVEN.getValue(), Nums.ZERO.getValue());
        }
        else {
            preparedStatement.setInt(Nums.SEVEN.getValue(), task.getEmployeeId());
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
            preparedStatement.setInt(Nums.ONE.getValue(), id);
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
            preparedStatement.setString(Nums.ONE.getValue(), String.valueOf(id));
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
            preparedStatement.setInt(Nums.ONE.getValue(), id);
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
        task.setWorkTime(resultSet.getInt(WORK_TIME));
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
