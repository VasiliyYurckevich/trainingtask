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
package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Содержит методы для работы объектов класса "Задача" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Task
 */
public class    TaskDao implements IDao<Task>, Serializable {

    /**
     * Хранит константу для имени колонки ID проекта в БД.
     */
    private static final String ID = "id";

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
     * Константа для запроса добавления задачи в БД.
     */
    private static final String INSERT_TASK_SQL = "INSERT INTO TASK" +
        " (status, title, work_time, begin_date,end_date, project_id, employee_id)" +
        " VALUES (:status, :title, :work_time, :begin_date, :end_date, :project_id, :employee_id);";

    /**
     * Константа для запроса получения задач из БД.
     */
    private static final String SELECT_ALL_TASK = "SELECT * FROM TASK;";

    /**
     * Константа для запроса получения задачи из БД по идентификатору.
     */
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM TASK WHERE id = :id;";

    /**
     * Константа для запроса получения задач из БД по проекту.
     */
    private static final String SELECT_TASK_BY_PROJECT = "SELECT * FROM TASK WHERE project_id = :project_id;";

    /**
     * Константа для запроса удаления задачи из БД по идентификатору.
     */
    private static final String DELETE_TASK_SQL = "DELETE FROM TASK WHERE id = :id;";

    /**
     * Константа для запроса обновления задачи в БД.
     */
    private static final String UPDATE_TASK_SQL = "UPDATE TASK SET status = :status, title = :title," +
        " work_time = :work_time, begin_date = :begin_date, end_date = :end_date, project_id = :project_id," +
        " employee_id = :employee_id WHERE id = :id;";

    @Override
    public void add(Task task, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_TASK_SQL, connection)) {
            setDataInToStatement(task, preparedStatementHelper);
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Created new task");
            }
            else {
                throw new DaoException("Error when adding a task to the database");
            }
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
        
    }

    @Override
    public void update(Task task, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_TASK_SQL, connection)) {
            setDataInToStatement(task, preparedStatementHelper);
            preparedStatementHelper.setInt(ID, task.getId());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Task with id {0} was updated", task.getId());
            }
            else {
                throw new DaoException("Error when updating a task in the database");
            }
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
        
    }

    /**
     * Внесение данных о задаче в выражение SQL.
     *
     * @param task объект класса "Задача"
     * @param preparedStatementHelper выражение SQL
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void setDataInToStatement(Task task, PreparedStatementHelper preparedStatementHelper) throws DaoException {
        try {
            preparedStatementHelper.setInt(STATUS, task.getStatus().getId());
            preparedStatementHelper.setString(TITLE, task.getTitle());
            preparedStatementHelper.setInt(WORK_TIME, task.getWorkTime());
            preparedStatementHelper.setDate(BEGIN_DATE, task.getBeginDate());
            preparedStatementHelper.setDate(END_DATE, task.getEndDate());
            preparedStatementHelper.setInt(PROJECT_ID, task.getProjectId());
            preparedStatementHelper.setInt(EMPLOYEE_ID, task.getEmployeeId());
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_TASK_SQL, connection)) {
            preparedStatementHelper.setInt(ID, id);
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Task with id {0} was deleted", id);
            }
            else {
                LOGGER.log(Level.INFO, "Task with id {0} deleting failed", id);
            }
        }
        catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Получение всех задач определенного проекта из БД.
     *
     * @param id идентификатор проекта
     * @return все задачи проекта
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public List<Task> getTasksInProject(Integer id) throws DaoException {

        Connection connection = ConnectionController.getConnection();
        List<Task> tasks = new ArrayList<>();
        PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_TASK_BY_PROJECT, connection);
        preparedStatementHelper.setInt(PROJECT_ID, id);

        try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
            return getList(tasks, resultSet);
        }
        catch (DaoException | SQLException e) {
            throw new DaoException("Error when getting project tasks from the database", e);
        }
        finally {
            preparedStatementHelper.close();
        }
    }

    @Override
    public List<Task> getAll() throws DaoException {

        Connection connection = ConnectionController.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_TASK, connection);
            ResultSet resultSet = preparedStatementHelper.executeQuery()) {
            List<Task> tasks = new ArrayList<>();
            return getList(tasks, resultSet);
        }
        catch (DaoException | SQLException e) {
            throw new DaoException("Error when getting all tasks from the database", e);
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
        catch (DaoException | SQLException e) {
            throw new DaoException("Error when getting tasks from the database", e);
        }
        return tasks;
    }

    @Override
    public Task getById(Integer id) throws DaoException {

        Connection connection = ConnectionController.getConnection();
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_TASK_BY_ID, connection)) {
            preparedStatementHelper.setInt(ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    setDataFromDatabase(task, resultSet);
                    return task;
                }
                else {
                    throw new DaoException("An employee with such data was not found");
                }
            }
        }
        catch (DaoException | SQLException e) {
            throw new DaoException("An employee with such data was not found", e);
        }
    }

    /**
     * Заполнение объекта Task данными из БД.
     *
     * @param resultSet результат выполнения SQL-запроса
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void setDataFromDatabase(Task task, ResultSet resultSet) throws DaoException {
        try {
            task.setId(resultSet.getInt(ID));
            task.setStatus(Status.getStatusById(Integer.parseInt(resultSet.getString(STATUS))));
            task.setTitle(resultSet.getString(TITLE));
            task.setWorkTime(resultSet.getInt(WORK_TIME));
            task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
            task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            task.setProjectId(resultSet.getInt(PROJECT_ID));
            task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
        }
        catch (DaoException | SQLException e) {
            throw new DaoException("Error when retrieving task data from the database", e);
        }
    }
}
