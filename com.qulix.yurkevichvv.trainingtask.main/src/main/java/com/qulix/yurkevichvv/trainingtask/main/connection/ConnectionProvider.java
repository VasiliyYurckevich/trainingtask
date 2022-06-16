package com.qulix.yurkevichvv.trainingtask.main.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

/**
 * Провайдер подключения к БД.
 *
 * @author Q-YVV
 */
public class ConnectionProvider {


    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true;sql.syntax_mys=true";

    private static final String USER = "SA";

    private static final String PASS = "";

    private static final String SQL_STATE = "SQL State  : ";

    private static final Logger LOGGER = Logger.getLogger(ConnectionProvider.class.getName());

    /**
     * Получение подключения к БД.
     */
    private static Connection connection = null;


    /**
     * Устанавливает соединение с БД.
     *
     * @return подключение к БД.
     * @throws SQLException ошибка подключения к БД.
     */
    public static Connection getConnection() throws DaoException, PathNotValidException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(PATH, USER, PASS);
            return connection;
        }
        catch (ClassNotFoundException e) {
            LOGGER.severe("Не удалось загрузить драйвер " + JDBC_DRIVER);
            LOGGER.severe(e.getStackTrace().toString());
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new PathNotValidException("Драйвер не найден", e);
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.severe(e.getStackTrace().toString());
            LOGGER.severe(SQL_STATE + e.getSQLState());
            throw new DaoException("БД временно недоступна. Повторите попытку позже", e);
        }
    }

    /**
     * Закрывает соединение с БД.
     *
     * @throws SQLException исключение БД.
     */
    public static void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.severe(e.getStackTrace().toString());
            LOGGER.severe(SQL_STATE + e.getSQLState());
            throw new DaoException("Ошибка закрытия подключения к БД.", e);
        }
    }
}
