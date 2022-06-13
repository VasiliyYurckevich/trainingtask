package com.qulix.yurkevichvv.trainingtask.main.connection;

import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Устанавливает контакт между приложением и БД.
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
    public static Connection getConnection( ) throws DaoException, PathNotValidException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(PATH, USER, PASS);
        }
        catch (ClassNotFoundException e) {
            LOGGER.severe("Не удалось загрузить драйвер " + JDBC_DRIVER);
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new PathNotValidException("Драйвер не найден", e);
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            LOGGER.severe("SQL State  : " + e.getSQLState());
            throw new DaoException("БД временно недоступна. Повторите попытку позже", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return connection;
    }

    /**
     * Закрытие подключения к БД.
     *
     * @throws SQLException исключение БД.
     */
    public static void closeConnection() throws DaoException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            LOGGER.severe(e.getSQLState());
            throw new DaoException("Ошибка закрытия подключения к БД.", e);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
