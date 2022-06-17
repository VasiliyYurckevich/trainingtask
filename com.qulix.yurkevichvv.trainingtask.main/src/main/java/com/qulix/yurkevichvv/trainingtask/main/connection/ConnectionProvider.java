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
     * Хранит константу для вывода состояния SQL.
     */
    private static final String SQL_STATE = "SQL State  : ";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionProvider.class.getName());

    /**
     * Получение подключения к БД.
     */
    private static Connection connection = null;


    /**
     * Устанавливает соединение с БД.
     *
     * @return подключение к БД.
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    public static Connection getConnection() throws DaoException, PathNotValidException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(PATH, USER, PASS);
            return connection;
        }
        catch (ClassNotFoundException e) {
            LOGGER.severe("Не удалось загрузить драйвер " + JDBC_DRIVER);
            LOGGER.severe(e.toString());
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new PathNotValidException("Драйвер не найден", e);
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.severe(e.toString());
            LOGGER.severe(SQL_STATE + e.getSQLState());
            throw new DaoException("БД временно недоступна. Повторите попытку позже", e);
        }
    }

    /**
     * Закрывает соединение с БД.
     *
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД.
     */
    public static void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            LOGGER.severe(e.toString());
            LOGGER.severe(SQL_STATE + e.getSQLState());
            throw new DaoException("Ошибка закрытия подключения к БД.", e);
        }
    }
}
