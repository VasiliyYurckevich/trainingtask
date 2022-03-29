package com.qulix.yurkevichvv.trainingtask.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static Connection connection = null;


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(PATH, USER, PASS);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


}



