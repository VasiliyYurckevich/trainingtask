package com.qulix.yurkevichvv.trainingtask.DAO;

import com.qulix.yurkevichvv.trainingtask.Connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProject implements DAOInterface<Project>{

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private boolean query;

    private static String TABLE_NAME = "project";
    private static String PROJECT_ID = "projectId";
    private static String TITLE = "title";
    private static String DISCRIPTION = "discription";

    private final String INSERT_PROJECT_SQL = "INSERT INTO " + TABLE_NAME + " (title, discription) VALUES (?,?);";
    private final String SELECT_ALL_PROJECTS = "SELECT * FROM "+ TABLE_NAME + ";";
    private final String SELECT_PROJECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    //private final String SELECT_EMPLOYEE_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME + " = ?;";
    private final String DELETE_PROJECT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PROJECT_ID + " = ?;";
    private final String UPDATE_PROJECT_SQL = "UPDATE " + TABLE_NAME + " SET title = ?,  discription = ? WHERE " + PROJECT_ID + " = ?;";



    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getProjectId() {
        return PROJECT_ID;
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static String getDISCRIPTION() {
        return DISCRIPTION;
    }

    @Override
    public boolean add(Project project) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL);
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDiscription());
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();

        }
    }

    @Override
    public boolean update(Project project) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL);
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDiscription());
            preparedStatement.setInt(3, project.getId());
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL);
            preparedStatement.setInt(1, id);
            query = preparedStatement.execute();

            return query;
        }finally {
            DBConnection.closeConnection();

        }
    }

    @Override
    public List getAll() throws SQLException {
        connection = DBConnection.getConnection();
        try {
            List<Project> projects = new ArrayList<>();
            resultSet = connection.createStatement().executeQuery(SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt(PROJECT_ID));
                project.setTitle(resultSet.getString(TITLE));
                project.setDiscription(resultSet.getString(DISCRIPTION));
                projects.add(project);
            }

            return projects;
        }finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public Project getById(Integer id) throws SQLException {
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Project project = new Project();
            while (resultSet.next()) {
                project.setTitle(resultSet.getString(TITLE));
                project.setDiscription(resultSet.getString(DISCRIPTION));

            }

            return project;
        }finally {
            DBConnection.closeConnection();

        }
    }
}