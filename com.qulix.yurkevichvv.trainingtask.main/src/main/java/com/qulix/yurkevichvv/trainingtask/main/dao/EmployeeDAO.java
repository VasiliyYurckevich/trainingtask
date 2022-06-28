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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.ConnectionManipulator;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;

/**
 * Содержит методы для работы объектов класса "Сотрудник" с БД.
 *
 * @author Q-YVV
 * @see Employee
 * @see IDao
 */
public class EmployeeDAO implements IDao<Employee> {

    /**
     * Хранит константу для колонки ID сотрудника в БД.
     */
    private static final String EMPLOYEE_ID = "id";

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
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());


    /**
     * Константа для запроса на добавление нового сотрудника в БД.
     */
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO EMPLOYEE (surname, first_name, patronymic, post)" +
        " VALUES (?,?,?,?);";

    /**
     * Константа для запроса на получение всех сотрудников из БД.
     */
    private static final String SELECT_ALL_CLIENT = "SELECT * FROM EMPLOYEE;";

    /**
     * Константа для запроса на получение сотрудника по его ID из БД.
     */
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE id = ?;";

    /**
     * Константа для запроса на удаление данных сотрудника в БД.
     */
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM EMPLOYEE WHERE id = ?;";

    /**
     * Константа для запроса на обновление данных сотрудника в БД.
     */
    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE" +
        " SET surname = ?, first_name = ?, patronymic = ?, post = ? WHERE id = ?;";


    @Override
    public boolean add(Employee employee) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, employee.getSurname());
            preparedStatement.setString(index++, employee.getFirstName());
            preparedStatement.setString(index++, employee.getPatronymic());
            preparedStatement.setString(index, employee.getPost());

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when adding a new employee to the database");
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public boolean update(Employee employee) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, employee.getSurname());
            preparedStatement.setString(index++, employee.getFirstName());
            preparedStatement.setString(index++, employee.getPatronymic());
            preparedStatement.setString(index++, employee.getPost());
            preparedStatement.setInt(index, employee.getId());
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when trying to change employee data", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when trying to delete employee data", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }


    @Override
    public List<Employee> getAll() throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENT)) {
            List<Employee> employees = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = getEmployeeFromDB(resultSet);
                employees.add(employee);
            }
            return employees;
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when getting employee data", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public Employee getById(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = getEmployeeFromDB(resultSet);
                return employee;
            }
            else {
                throw new DaoException("An employee with such data was not found");
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when getting employee data", e);
        }
        finally {
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
            employee.setId(resultSet.getInt(EMPLOYEE_ID));
            employee.setSurname(resultSet.getString(SURNAME));
            employee.setFirstName(resultSet.getString(FIRST_NAME));
            employee.setPatronymic(resultSet.getString(PATRONYMIC));
            employee.setPost(resultSet.getString(POST));
            return employee;
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when getting employee data from the database", e);
        }
    }
}
