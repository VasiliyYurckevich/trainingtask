package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import model.Task;
/**
 * Class for working with database table "task".
 *<p> {@link DAOTask} using for write data about task in database.</p>
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
@SuppressWarnings ({"checkstyle:MultipleStringLiterals", "checkstyle:MagicNumber"})
public class DAOTask implements DAOInterface<Task> {

    private static final  String TABLE_NAME = "task";
    private static final  String TASK_ID = "id";
    private static final  String STATUS = "status";
    private static final  String TITLE = "title";
    private static final  String PROJECT_ID = "project_id";
    private static final  String WORK_TIME = "work_time";
    private static final  String BEGIN_DATE = "begin_date";
    private static final  String END_DATE = "end_date";
    private static final  String EMPLOYEE_ID = "employee_id";

    //SQL queries
    private static final String INSERT_TASK_SQL = "INSERT INTO " + TABLE_NAME +
        " (status, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";
    private static final String SELECT_ALL_TASK = "SELECT * FROM " + TABLE_NAME + ";";
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private static final String SELECT_TASK_BY_PROJECT = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private static final String DELETE_TASK_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private static final String UPDATE_TASK_SQL = "UPDATE " + TABLE_NAME + " SET status = ?,  title = ?,  work_time = ?," +
        " begin_date = ?,  end_date = ?, project_id = ?, employee_id = ? WHERE " + TASK_ID + " = ?;";


    /**
     * Method for adding new task to database.
     *
     * @param task - task for adding
     * @return  true if task added, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean add(Task task) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
        try {
            setDataInToStatement(task, preparedStatement);
            boolean query = preparedStatement.execute();
            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for updating task in database.
     *
     * @param task - task for updating
     * @return true if task updated, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean update(Task task) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
        try {
            setDataInToStatement(task, preparedStatement);
            preparedStatement.setInt(8, task.getId());
            boolean query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for setting data in to statement.
     *
     * @param task - task of setting data
     * @param preparedStatement - statement for setting data
     * @return true if data setting, false if not
     * @throws SQLException - if something wrong with database
     */
    private PreparedStatement setDataInToStatement(Task task, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, task.getStatus());
        preparedStatement.setString(2, task.getTitle());
        preparedStatement.setLong(3, task.getWorkTime());
        preparedStatement.setString(4, task.getBeginDate().toString());
        preparedStatement.setString(5, task.getEndDate().toString());
        if (task.getProjectId() == null) {
            preparedStatement.setNull(6, 5);
        }
        else {
            preparedStatement.setInt(6, task.getProjectId());
        }
        if (task.getEmployeeId() == null) {
            preparedStatement.setNull(7, 5);
        }
        else {
            preparedStatement.setInt(7, task.getEmployeeId());
        }
        return preparedStatement;
    }

    /**
     * Method for deleting task from database.
     *
     * @param id - id of task for deleting
     * @return true if task deleted, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean delete(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_SQL);
            preparedStatement.setInt(1, id);
            boolean query = preparedStatement.execute();
            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for getting all tasks in project from database.
     *
     * @param id - id of project
     * @return list of tasks
     * @throws SQLException - if something wrong with database
     */
    public List<Task> getTasksInProject(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for getting all tasks from database.
     *
     * @return list of tasks
     * @throws SQLException - if something wrong with database
     */
    @Override
    public List getAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            List<Task> tasks = new ArrayList<Task>();
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_TASK);
            return getList(tasks, resultSet);
        }
        finally {
            DBConnection.closeConnection();
        }
    }
    /**
     * Method for getting list of tasks from database.
     *
     * @return list of tasks
     * @throws SQLException - if something wrong with database
     */
    private List getList(List<Task> tasks, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt(TASK_ID));
            task.setStatus(resultSet.getString(STATUS));
            task.setTitle(resultSet.getString(TITLE));

            task.setWorkTime(resultSet.getLong(WORK_TIME));
            task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
            task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            if (resultSet.getInt(PROJECT_ID) != 0) {
                task.setProjectId(resultSet.getInt(PROJECT_ID));
            }
            else {
                task.setProjectId(null);
            }
            if (resultSet.getInt(EMPLOYEE_ID) != 0)  {
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
            }
            else {
                task.setEmployeeId(null);
            }

            tasks.add(task);

        }
        return tasks;
    }

    /**
     * Method for getting task by id from database.
     *
     * @param id - id of task
     * @return task
     * @throws SQLException if something wrong with database
     */
    @Override
    public Task getById(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Task task = new Task();
            while (resultSet.next()) {
                task.setId(resultSet.getInt(TASK_ID));
                task.setStatus(resultSet.getString(STATUS));
                task.setTitle(resultSet.getString(TITLE));
                task.setWorkTime(resultSet.getLong(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                if (resultSet.getInt(PROJECT_ID) != 0) {
                    task.setProjectId(resultSet.getInt(PROJECT_ID));
                }
                else {
                    task.setProjectId(null);
                }
                if (resultSet.getInt(EMPLOYEE_ID) != 0) {
                    task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
                }
                else {
                    task.setEmployeeId(null);
                }
            }
            return task;
        }
        finally {
            DBConnection.closeConnection();
        }
    }
}
