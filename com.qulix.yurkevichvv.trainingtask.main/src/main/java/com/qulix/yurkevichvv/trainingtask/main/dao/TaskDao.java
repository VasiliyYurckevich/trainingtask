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
package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.ConnectionProvider;
import com.qulix.yurkevichvv.trainingtask.main.entity.Status;
import com.qulix.yurkevichvv.trainingtask.main.entity.Task;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

/**
 * Содержит методы для работы объектов класса "Задача" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Task
 */
public class TaskDao implements IDao<Task> {


    /**
     * Хранит константу для имени колонки ID проекта в БД.
     */
    private static final String TASK_ID = "id";

    /**
     * Хранит константу для имени колонки статуса проекта в БД.
     */
    private static final String STATUS = "status";

    /**
     * Хранит константу для имени колонки названия задачи в БД.
     */
    private static final String TITLE = "title";

    /**
     * Хранит константу для имени колонки проекта, в который входит задача, в БД.
     */
    private static final String PROJECT_ID = "project_id";

    /**
     * Хранит константу для имени колонки времени работы над задачей в БД.
     */
    private static final String WORK_TIME = "work_time";

    /**
     * Хранит константу для имени колонки даты начала работы над задачей в БД.
     */
    private static final String BEGIN_DATE = "begin_date";

    /**
     * Хранит константу для имени колонки даты конца работы над задачей в БД.
     */
    private static final String END_DATE = "end_date";

    /**
     * Хранит константу для имени колонки привязанного к задаче сотрудника в БД.
     */
    private static final String EMPLOYEE_ID = "employee_id";

    /**
     * Логгер для записи логов.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskDao.class.getName());


    /**
     *  Константа для запроса добавления задачи в БД.
     */
    private static final String INSERT_TASK_SQL = "INSERT INTO TASK" +
        " (status, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";

    /**
     * Константа для запроса получения задач из БД.
     */
    private static final String SELECT_ALL_TASK = "SELECT * FROM TASK;";

    /**
     * Константа для запроса получения задачи из БД по идентификатору.
     */
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM TASK WHERE id = ?;";

    /**
     * Константа для запроса получения задач из БД по проекту.
     */
    private static final String SELECT_TASK_BY_PROJECT = "SELECT * FROM TASK WHERE project_id = ?;";

    /**
     * Константа для запроса удаления задачи из БД по идентификатору.
     */
    private static final String DELETE_TASK_SQL = "DELETE FROM TASK WHERE id = ?;";

    /**
     * Константа для запроса обновления задачи в БД.
     */
    private static final String UPDATE_TASK_SQL = "UPDATE TASK SET status = ?, title = ?, work_time = ?, " +
        "begin_date = ?, end_date = ?, project_id = ?, employee_id = ? WHERE id = ?;";

    /**
     * Хранит константу для вывода состояния SQL.
     */
    private static final String SQLSTATE = "SQLState: ";


    @Override
    public boolean add(Task task) throws DaoException, PathNotValidException  {
    
        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL)) {
            setDataInToStatement(task, preparedStatement);
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при добавлении задачи в БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }


    @Override
    public boolean update(Task task) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL)) {
            int index = setDataInToStatement(task, preparedStatement);
            preparedStatement.setInt(index, task.getId());

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при обновлении задачи в БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    /**
     * Внесение данных о задаче в выражение SQL.
     *
     * @param task объект класса "Задача".
     * @param preparedStatement выражение SQL.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private int setDataInToStatement(Task task, PreparedStatement preparedStatement) throws DaoException {
        try {
            int index = 1;
            preparedStatement.setInt(index++, task.getStatus().getId());
            preparedStatement.setString(index++, task.getTitle());
            preparedStatement.setLong(index++, task.getWorkTime());
            preparedStatement.setString(index++, task.getBeginDate().toString());
            preparedStatement.setString(index++, task.getEndDate().toString());

            if (task.getProjectId() == null) {
                preparedStatement.setNull(index++, 0);
            }
            else {
                preparedStatement.setInt(index++, task.getProjectId());
            }
            if (task.getEmployeeId() == null) {
                preparedStatement.setNull(index++, 0);
            }
            else {
                preparedStatement.setInt(index++, task.getEmployeeId());
            }
            return index;
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при внесении данных о задаче в выражение SQL", e);
        }
    }


    @Override
    public boolean delete(Integer id) throws DaoException, PathNotValidException {
        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при удалении задачи из БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    /**
     * Получение всех задач определенного проекта из БД.
     *
     * @param id идентификатор проекта.
     * @return все задачи проекта.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public List<Task> getTasksInProject(Integer id) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();
        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задач проекта из БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    @Override
    public List<Task> getAll() throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK)) {
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении всех задач из БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    /**
     * Получение добавление задачи в лист.
     *
     * @param tasks лист задач.
     * @param resultSet выражение SQL.
     * @return лист задач.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private List<Task> getList(List<Task> tasks, ResultSet resultSet) throws DaoException {
        try {
            while (resultSet.next()) {
                Task task = new Task();
                setDataFromDatabase(task, resultSet);
                tasks.add(task);
            }
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задач из БД", e);
        }
        return tasks;
    }

    @Override
    public Task getById(Integer id) throws DaoException, PathNotValidException {

        Connection connection = ConnectionProvider.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                setDataFromDatabase(task, resultSet);
                return task;
            }
            else {
                throw new DaoException("Не найден сотрудник с такими данными");
            }
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задачи по идентификатору из БД", e);
        }
        finally {
            ConnectionProvider.closeConnection(connection);
        }
    }

    /**
     * Заполнение объекта Task данными из БД.
     *
     * @param resultSet выражение SQL.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void setDataFromDatabase(Task task, ResultSet resultSet) throws DaoException {
        try {
            task.setId(resultSet.getInt(TASK_ID));
            task.setStatus(Status.getStatusById(Integer.parseInt(resultSet.getString(STATUS))));
            task.setTitle(resultSet.getString(TITLE));
            task.setWorkTime(resultSet.getInt(WORK_TIME));
            task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
            task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            task.setProjectId(resultSet.getInt(PROJECT_ID));

            if (resultSet.getInt(EMPLOYEE_ID) != 0) {
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
            }
            else {
                task.setEmployeeId(null);
            }
        }
        catch (SQLException e) {
            LOGGER.severe(e.toString());
            LOGGER.severe(SQLSTATE + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных задачи из БД", e);
        }
    }
}
