package DAO;

import com.qulix.yurkevichvv.trainingtask.Connection.DBConnection;
import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with database table tasks
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class    DAOTask implements DAOInterface<Task>{

    private Connection connection = null;//connection to database
    private PreparedStatement preparedStatement = null;//statement for database
    private ResultSet resultSet = null;//result set for database
    private boolean query;//result of query

    //Schema and table names
    private static String SCHEMA_NAME = "PUBLIC";
    private static String TABLE_NAME = "task";
    private static String TASK_ID = "id";
    private static String STATUS = "status";
    private static String TITLE = "title";
    private static String PROJECT_ID = "project_id";
    private static String WORK_TIME = "work_time";
    private static String BEGIN_DATE = "begin_date";
    private static String END_DATE = "end_date";
    private static String EMPLOYEE_ID = "employee_id";

    //SQL queries
    private final String INSERT_TASK_SQL = "INSERT INTO " + TABLE_NAME + " (status, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";
    private final String SELECT_ALL_TASK = "SELECT * FROM "+ TABLE_NAME + ";";
    private final String SELECT_TASK_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String SELECT_TASK_BY_PROJECT = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private final String DELETE_TASK_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String UPDATE_TASK_SQL = "UPDATE " + TABLE_NAME + " SET status = ?,  title = ?,  work_time = ?,begin_date = ?,  end_date = ?,project_id = ?,employee_id = ? WHERE " + TASK_ID + " = ?;";


    /**
     * Method for adding new task to database
     *
     * @param task - task for adding
     * @return  true if task added, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean add(Task task) throws SQLException {
        connection = DBConnection.getConnection(); //get connection to database

        try {
            preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
            //set parameters for query
            preparedStatement.setString(1, task.getStatus());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setLong(3, task.getWorkTime());
            preparedStatement.setString(4, task.getBeginDate().toString());
            preparedStatement.setString(5, task.getEndDate().toString());
            if (task.getProjectId()==null){
                preparedStatement.setNull(6,5);//if project_id is null, set it to null
            }else {
                preparedStatement.setInt(6, task.getProjectId());//if project_id is not null, set it to value
            }
            if (task.getEmployeeId()==null){
                preparedStatement.setNull(7,5);//if employee_id is null, set it to null
            }else {
                preparedStatement.setInt(7, task.getEmployeeId());//if employee_id is not null, set it to value
            }
            query = preparedStatement.execute();//execute query
            return query;
            }
        finally {
            DBConnection.closeConnection();//close connection to database
        }
    }

    /**
     * Method for updating task in database
     *
     * @param task - task for updating
     * @return true if task updated, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean update(Task task) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database
        try {
            preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
            //set parameters for query
            preparedStatement.setString(1, task.getStatus());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setLong(3, task.getWorkTime());
            preparedStatement.setString(4, task.getBeginDate().toString());
            preparedStatement.setString(5, task.getEndDate().toString());
            if (task.getProjectId()==null){
                preparedStatement.setNull(6,5);//if project_id is null, set it to null
            }else {
                preparedStatement.setInt(6, task.getProjectId());//if project_id is not null, set it to value
            }            if (task.getEmployeeId()==null){
                preparedStatement.setNull(7,5);
            }else {
                preparedStatement.setInt(7, task.getEmployeeId());
            }
            preparedStatement.setInt(8, task.getId());

            query = preparedStatement.execute();//execute query

            return query;
        }
        finally {
            DBConnection.closeConnection();//close connection to database
        }
    }

    /**
     * Method for deleting task from database
     *
     * @param id - id of task for deleting
     * @return true if task deleted, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean delete(Integer id) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database

        try {
            preparedStatement = connection.prepareStatement(DELETE_TASK_SQL);
            preparedStatement.setInt(1, id);//set parameter for query
            query = preparedStatement.execute();//execute query

            return query;
        }finally {
            DBConnection.closeConnection();//close connection to database
        }
    }

    /**
     * Method for getting all tasks in project from database
     *
     * @param id - id of project
     * @return list of tasks
     * @throws SQLException - if something wrong with database
     */
    public List<Task> getTaskInProject(Integer id) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database

        List<Task> tasks = new ArrayList<>();//create list of tasks
        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT);
            preparedStatement.setString(1, String.valueOf(id));//set parameter for query
            resultSet = preparedStatement.executeQuery();//execute query
            while (resultSet.next()) {
                Task task = new Task();//create task
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setStatus(resultSet.getString(STATUS));
                task.setTitle(resultSet.getString(TITLE));
                task.setProjectId(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getLong(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
                tasks.add(task);//add task to list
            }
            return tasks;
        }finally {
            DBConnection.closeConnection();//close connection to database
        }
    }

    /**
     * Method for getting all tasks from database
     *
     * @return list of tasks
     * @throws SQLException - if something wrong with database
     */
    @Override
    public List getAll() throws SQLException {
        connection = DBConnection.getConnection();//get connection to database
        try {
            List<Task> tasks = new ArrayList<Task>();//create list of tasks
            resultSet = connection.createStatement().executeQuery(SELECT_ALL_TASK);
            while (resultSet.next()) {
                Task task = new Task();//create task
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setStatus(resultSet.getString(STATUS));
                task.setTitle(resultSet.getString(TITLE));
                task.setProjectId(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getLong(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
                tasks.add(task);//add task to list
            }
            return tasks;
        }finally {
            DBConnection.closeConnection();//close connection to database
        }
    }

    /**
     * Method for getting task by id from database
     *
     * @param id - id of task
     * @return task
     * @throws SQLException- if something wrong with database
     */
    @Override
    public Task getById(Integer id) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database

        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);
            preparedStatement.setInt(1, id);//set parameter for query
            resultSet = preparedStatement.executeQuery();//execute query
            Task task = new Task();//create task
            while (resultSet.next()) {
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setStatus(resultSet.getString(STATUS));
                task.setTitle(resultSet.getString(TITLE));
                task.setWorkTime(resultSet.getLong(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setProjectId(resultSet.getInt(PROJECT_ID));
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
            }
            return task;
        }finally {
            DBConnection.closeConnection();//close connection to database
        }
    }
}