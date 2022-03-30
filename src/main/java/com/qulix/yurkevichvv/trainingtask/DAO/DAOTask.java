package com.qulix.yurkevichvv.trainingtask.DAO;

import com.qulix.yurkevichvv.trainingtask.Connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.model.Tasks;

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
public class    DAOTask implements DAOInterface<Tasks>{

    private Connection connection = null;//connection to database
    private PreparedStatement preparedStatement = null;//statement for database
    private ResultSet resultSet = null;//result set for database
    private boolean query;//result of query

    //Schema and table names
    private static String SCHEMA_NAME = "PUBLIC";
    private static String TABLE_NAME = "tasks";
    private static String TASK_ID = "id";
    private static String FLAG = "flag";
    private static String TITLE = "title";
    private static String PROJECT_ID = "project_id";
    private static String WORK_TIME = "work_time";
    private static String BEGIN_DATE = "begin_date";
    private static String END_DATE = "end_date";
    private static String EMPLOYEE_ID = "employee_id";

    //SQL queries
    private final String INSERT_TASK_SQL = "INSERT INTO " + TABLE_NAME + " (flag, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";
    private final String SELECT_ALL_TASK = "SELECT * FROM "+ TABLE_NAME + ";";
    private final String SELECT_TASK_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String SELECT_TASK_BY_PROJECT = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private final String DELETE_TASK_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String UPDATE_TASK_SQL = "UPDATE " + TABLE_NAME + " SET flag = ?,  title = ?,  work_time = ?,begin_date = ?,  end_date = ?,project_id = ?,employee_id = ? WHERE " + TASK_ID + " = ?;";


    /**
     * Method for adding new task to database
     *
     * @param tasks - task for adding
     * @return  true if task added, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean add(Tasks tasks) throws SQLException {
        connection = DBConnection.getConnection(); //get connection to database

        try {
            preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
            //set parameters for query
            preparedStatement.setString(1, tasks.getFlag());
            preparedStatement.setString(2, tasks.getTitle());
            preparedStatement.setInt(3, tasks.getWorkTime());
            preparedStatement.setString(4, tasks.getBeginDate().toString());
            preparedStatement.setString(5, tasks.getEndDate().toString());
            preparedStatement.setInt(6, tasks.getProject_id());
            if (tasks.getEmployee_id()==null){
                preparedStatement.setNull(7,5);//if employee_id is null, set it to null
            }else {
                preparedStatement.setInt(7, tasks.getEmployee_id());//if employee_id is not null, set it to value
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
     * @param tasks - task for updating
     * @return true if task updated, false if not
     * @throws SQLException - if something wrong with database
     */
    @Override
    public boolean update(Tasks tasks) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database
        try {
            preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
            //set parameters for query
            preparedStatement.setString(1, tasks.getFlag());
            preparedStatement.setString(2, tasks.getTitle());
            preparedStatement.setInt(3, tasks.getWorkTime());
            preparedStatement.setString(4, tasks.getBeginDate().toString());
            preparedStatement.setString(5, tasks.getEndDate().toString());
            preparedStatement.setInt(6, tasks.getProject_id());
            if (tasks.getEmployee_id()==null){
                preparedStatement.setNull(7,5);
            }else {
                preparedStatement.setInt(7, tasks.getEmployee_id());
            }            preparedStatement.setInt(8, tasks.getId());

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
    public List<Tasks> getTaskInProject(Integer id) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database

        List<Tasks> tasks = new ArrayList<>();//create list of tasks
        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT);
            preparedStatement.setString(1, String.valueOf(id));//set parameter for query
            resultSet = preparedStatement.executeQuery();//execute query
            while (resultSet.next()) {
                Tasks task = new Tasks();//create task
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setEmployee_id(resultSet.getInt(EMPLOYEE_ID));
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
            List<Tasks> tasks = new ArrayList<Tasks>();//create list of tasks
            resultSet = connection.createStatement().executeQuery(SELECT_ALL_TASK);
            while (resultSet.next()) {
                Tasks task = new Tasks();//create task
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setEmployee_id(resultSet.getInt(EMPLOYEE_ID));
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
    public Tasks getById(Integer id) throws SQLException {
        connection = DBConnection.getConnection();//get connection to database

        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);
            preparedStatement.setInt(1, id);//set parameter for query
            resultSet = preparedStatement.executeQuery();//execute query
            Tasks task = new Tasks();//create task
            while (resultSet.next()) {
                //set parameters for task
                task.setId(resultSet.getInt(TASK_ID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setEmployee_id(resultSet.getInt(EMPLOYEE_ID));
            }
            return task;
        }finally {
            DBConnection.closeConnection();//close connection to database
        }
    }
}