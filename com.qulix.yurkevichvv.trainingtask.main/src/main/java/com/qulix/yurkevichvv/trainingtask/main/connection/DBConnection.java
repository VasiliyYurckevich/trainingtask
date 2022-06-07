package com.qulix.yurkevichvv.trainingtask.main.connection;

import com.qulix.yurkevichvv.trainingtask.main.controllers.ProjectController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Устанавливет контакт между приложением и БД.
 *
 * @author Q-YVV
 */
public class DBConnection {


    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true;sql.syntax_mys=true";

    private static final String USER = "SA";

    private static final String PASS = "";

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    /**
     * Получение подключения к БД.
     */
    private static Connection connection = null;


    /**
     * Подключение к БД.
     *
     * @return подключение к БД.
     * @throws SQLException ошибка подключения к БД.
     */
    public static Connection getConnection(){

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(PATH, USER, PASS);
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        catch (SQLException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return connection;
    }

    /**
     * Закрытие подключения к БД.
     *
     * @param preparedStatement выражение SQL.
     * @throws SQLException исключение БД.
     */
    public static void closeConnection(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null && !preparedStatement.isClosed()) {
            preparedStatement.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}



