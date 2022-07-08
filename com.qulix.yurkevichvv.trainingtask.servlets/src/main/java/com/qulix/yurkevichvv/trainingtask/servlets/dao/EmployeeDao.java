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
package com.qulix.yurkevichvv.trainingtask.servlets.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionManipulator;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.servlets.utils.PreparedStatementHelper;

/**
 * Содержит методы для работы объектов класса "Сотрудник" с БД.
 *
 * @author Q-YVV
 * @see Employee
 * @see IDao
 */
public class EmployeeDao implements IDao<Employee> {

    /**
     * Двоеточие.
     */
    private static final String COLON = ":";

    /**
     * Хранит константу для колонки фамилии сотрудника в БД.
     */
    private static final String SURNAME = "surname";

    /**
     * Хранит константу для колонки имени сотрудника в БД.
     */
    private static final String FIRST_NAME = "first_name";

    /**
     * Хранит константу для колонки отчества сотрудника в БД.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Хранит константу для колонки должности сотрудника в БД.
     */
    private static final String POST = "post";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeDao.class.getName());

    /**
     * Константа для запроса на добавление нового сотрудника в БД.
     */
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO EMPLOYEE (surname, first_name, patronymic, post)" +
        " VALUES (:surname, :first_name, :patronymic, :post);";

    /**
     * Константа для запроса на получение всех сотрудников из БД.
     */
    private static final String SELECT_ALL_CLIENT = "SELECT * FROM EMPLOYEE;";

    /**
     * Константа для запроса на получение сотрудника по его ID из БД.
     */
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE id = :id;";

    /**
     * Константа для запроса на удаление данных сотрудника в БД.
     */
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM EMPLOYEE WHERE id = :id;";

    /**
     * Константа для запроса на обновление данных сотрудника в БД.
     */
    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE" +
        " SET surname = :surname, first_name = :first_name, patronymic = :patronymic, post = :post WHERE id = :id;";

    /**
     * Хранит константу для колонки ID сотрудника в БД.
     */
    private static final String ID = "id";


    @Override
    public void add(Employee employee) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_EMPLOYEE_SQL, connection)) {
            preparedStatementHelper.setString(COLON + SURNAME, employee.getSurname());
            preparedStatementHelper.setString(COLON + FIRST_NAME, employee.getFirstName());
            preparedStatementHelper.setString(COLON + PATRONYMIC, employee.getPatronymic());
            preparedStatementHelper.setString(COLON + POST, employee.getPost());
            preparedStatementHelper.execute();
            LOGGER.log(Level.INFO, "Created employee");
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when adding a new employee to the database", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public void update(Employee employee) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_CLIENT_SQL, connection)) {
            preparedStatementHelper.setString(COLON + SURNAME, employee.getSurname());
            preparedStatementHelper.setString(COLON + FIRST_NAME, employee.getFirstName());
            preparedStatementHelper.setString(COLON + PATRONYMIC, employee.getPatronymic());
            preparedStatementHelper.setString(COLON + POST, employee.getPost());
            preparedStatementHelper.setInt(COLON + ID, employee.getId());
            preparedStatementHelper.execute();
            LOGGER.log(Level.INFO, "Employee with id {0} updated", employee.getId());
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when trying to change employee data", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_EMPLOYEE_SQL, connection)) {
            preparedStatementHelper.setInt(COLON + ID, id);
            preparedStatementHelper.execute();
            LOGGER.log(Level.INFO, "Employee with id {0} deleted", id);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when trying to delete employee data", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public List<Employee> getAll() throws DaoException {

        List<Employee> employees = new ArrayList<>();
        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_CLIENT, connection);
            ResultSet resultSet = preparedStatementHelper.getPreparedStatement().executeQuery()) {

            while (resultSet.next()) {
                Employee employee = getEmployeeFromDB(resultSet);
                employees.add(employee);
            }
            resultSet.close();

            return employees;
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when getting all employee data", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public Employee getById(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();
        PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_EMPLOYEE_BY_ID, connection);
        preparedStatementHelper.setInt(COLON + ID, id);

        try (ResultSet resultSet = preparedStatementHelper.getPreparedStatement().executeQuery()) {

            if (resultSet.next()) {
                return getEmployeeFromDB(resultSet);
            }
            else {
                throw new DaoException("An employee with such data was not found");
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when getting employee data", e);
            throw new DaoException(e);
        }
        finally {
            preparedStatementHelper.close();
            ConnectionManipulator.closeConnection(connection);
        }
    }

    /**
     * Заполнение объекта Employee данными из БД.
     *
     * @param resultSet результирующий набор данных
     * @return сотрудник
     */
    private static Employee getEmployeeFromDB(ResultSet resultSet) {
        try {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt(ID));
            employee.setSurname(resultSet.getString(SURNAME));
            employee.setFirstName(resultSet.getString(FIRST_NAME));
            employee.setPatronymic(resultSet.getString(PATRONYMIC));
            employee.setPost(resultSet.getString(POST));

            return employee;
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when getting employee data from the database", e);
            throw new DaoException(e);
        }
    }
}
