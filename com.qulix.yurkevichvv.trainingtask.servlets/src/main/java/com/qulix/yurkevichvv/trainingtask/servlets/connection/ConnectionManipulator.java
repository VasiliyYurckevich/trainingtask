/*
 * Copyright 2007 Qulix Systems, Inc. All rights reserved.
 * QULIX SYSTEMS PROPRIETARY/CONFIDENTIAL. Use is subject to license
 * terms.
 * Copyright (c) 2003-2007 Qulix Systems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Qulix Systems. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * QULIX MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.qulix.yurkevichvv.trainingtask.servlets.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;

/**
 * Провайдер подключения к БД и закрытия подключения.
 *
 * @author Q-YVV
 */
public class ConnectionManipulator {

    /**
     * Хранит константу названия драйвера JDBC.
     */
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    /**
     * Хранит константу пути к БД.
     */
    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true;sql.syntax_mys=true";

    /**
     * Хранит константу имени пользователя БД.
     */
    private static final String USER = "SA";

    /**
     * Хранит константу пароля к БД.
     */
    private static final String PASS = "";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionManipulator.class.getName());

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
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConnectionManipulator getConnection() error", e);
            throw new DaoException("The database is temporarily unavailable. Try again later", e);
        }
    }

    /**
     * Закрывает соединение с БД.
     *
     * @param connection соединение
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null && !connection.isClosed() && connection.getAutoCommit()) {
                connection.close();
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionManipulator closeConnection() error", e);
            throw new DaoException("Error closing the database connection", e);
        }
    }

    /**
     * Отправляет транзакцию в БД.
     *
     * @param connection соединение
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void commitConnection(Connection connection) throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionManipulator commitConnection() error", e);
            throw new DaoException("Error commit transaction", e);
        }
    }

    /**
     * Отменяет все изменения в транзакции.
     *
     * @param connection соединение
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void rollbackConnection(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.rollback();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ConnectionManipulator rollbackConnection() error", e);
            throw new DaoException("Error rollback transaction", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }
}
