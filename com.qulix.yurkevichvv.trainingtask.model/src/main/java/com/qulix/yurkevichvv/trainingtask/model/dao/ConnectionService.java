package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Провайдер подключения к БД и закрытия подключения.
 *
 * @author Q-YVV
 */
public class ConnectionService {

    /**
     * Имя драйвера JDBC.
     */
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    /**
     * Путь к БД.
     */
    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true;sql.syntax_mys=true";

    /**
     * Имя пользователя БД.
     */
    private static final String USER = "SA";

    /**
     * Пароль к БД.
     */
    private static final String PASS = "";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionService.class.getName());

    /**
     * Устанавливает соединение с БД.
     *
     * @return подключение к БД
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static Connection getConnection() throws DaoException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(PATH, USER, PASS);
        }
        catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionService getConnection() error", e);
            throw new DaoException("The database is temporarily unavailable. Try again later", e);
        }
    }

    /**
     * Отправляет транзакцию в БД.
     *
     * @param connection соединение {@link Connection}
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void commitConnection(Connection connection) throws DaoException {
        try {
            connection.commit();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionService commitConnection() error", e);
            throw new DaoException("Error commit transaction", e);
        }
    }

    /**
     * Отменяет все изменения в транзакции.
     *
     * @param connection соединение {@link Connection}
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void rollbackConnection(Connection connection) throws DaoException {
        try {
            connection.rollback();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionService rollbackConnection() error", e);
            throw new DaoException("Error rollback transaction", e);
        }
    }
}
