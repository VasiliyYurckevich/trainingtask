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

public class    DAOTask implements DAOInterface<Tasks>{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private boolean query;

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


    private final String INSERT_TASK_SQL = "INSERT INTO " + TABLE_NAME + " (flag, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";
    private final String SELECT_ALL_TASK = "SELECT * FROM "+ TABLE_NAME + ";";
    private final String SELECT_TASK_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String SELECT_TASK_BY_PROJECT = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    //private final String SELECT_EMPLOYEE_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME + " = ?;";
    private final String DELETE_TASK_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + TASK_ID + " = ?;";
    private final String UPDATE_TASK_SQL = "UPDATE " + TABLE_NAME + " SET flag = ?,  title = ?,  work_time = ?,begin_date = ?,  end_date = ?,project_id = ?,employee_id = ? WHERE " + TASK_ID + " = ?;";



    @Override
    public boolean add(Tasks tasks) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_TASK_SQL);
            preparedStatement.setString(1, tasks.getFlag());
            preparedStatement.setString(2, tasks.getTitle());
            preparedStatement.setInt(3, tasks.getWorkTime());
            preparedStatement.setString(4, tasks.getBeginDate().toString());
            preparedStatement.setString(5, tasks.getEndDate().toString());
            preparedStatement.setInt(6, tasks.getProject_id());
            preparedStatement.setInt(7, tasks.getEmployee_id());
            query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public boolean update(Tasks tasks) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL);
            preparedStatement.setString(1, tasks.getFlag());
            preparedStatement.setString(2, tasks.getTitle());
            preparedStatement.setInt(3, tasks.getWorkTime());
            preparedStatement.setString(4, tasks.getBeginDate().toString());
            preparedStatement.setString(5, tasks.getEndDate().toString());
            preparedStatement.setInt(6, tasks.getProject_id());
            preparedStatement.setInt(7, tasks.getEmployee_id());
            preparedStatement.setInt(8, tasks.getId());



            query = preparedStatement.execute();

            return query;
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean delete(Integer id) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(DELETE_TASK_SQL);
            preparedStatement.setInt(1, id);
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();
        }
    }

    public List<Tasks> getTaskInProject(Integer id) throws SQLException {
        connection = DBConnection.getConnection();
        List<Tasks> tasks = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT);
            preparedStatement.setString(1, String.valueOf(id));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tasks task = new Tasks();
                task.setId(resultSet.getInt(TASK_ID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setProject_id(resultSet.getInt(PROJECT_ID));

                tasks.add(task);
            }
            return tasks;
        }finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public List getAll() throws SQLException {
        connection = DBConnection.getConnection();
        try {
            List<Tasks> tasks = new ArrayList<Tasks>();
            resultSet = connection.createStatement().executeQuery(SELECT_ALL_TASK);
            while (resultSet.next()) {
                Tasks task = new Tasks();
                task.setId(resultSet.getInt(TASK_ID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
                task.setEmployee_id(resultSet.getInt(EMPLOYEE_ID));
                tasks.add(task);
            }

            return tasks;
        }finally {
            DBConnection.closeConnection();
        }

    }




    @Override
    public Tasks getById(Integer taskID) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);
            preparedStatement.setInt(1, taskID);
            resultSet = preparedStatement.executeQuery();
            Tasks task = new Tasks();
            while (resultSet.next()) {
                task.setId(resultSet.getInt(taskID));
                task.setFlag(resultSet.getString(FLAG));
                task.setTitle(resultSet.getString(TITLE));
                task.setProject_id(resultSet.getInt(PROJECT_ID));
                task.setWorkTime(resultSet.getInt(WORK_TIME));
                task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
                task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            }
            return task;
        }finally {
            DBConnection.closeConnection();

        }
    }
}

