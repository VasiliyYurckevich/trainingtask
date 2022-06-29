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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionManipulator;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;


/**
 * Содержит методы для работы объектов класса "Проект" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Project
 */
public class ProjectDao implements IDao<Project> {

    /**
     * Хранит константу для колонки ID проекта в БД.
     */
    private static final String PROJECT_ID = "id";

    /**
     * Хранит константу для колонки названия проекта в БД.
     */
    private static final String TITLE = "title";

    /**
     * Хранит константу для колонки описания сотрудника в БД.
     */
    private static final String DESCRIPTION = "description";
    /**
     * Логгер для ведения журнала действий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectDao.class.getName());


    /**
     * Константа для запроса добавления проекта в БД.
     */
    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description) VALUES (?,?);";

    /**
     * Константа для запроса всех проектов из БД.
     */
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT ;";

    /**
     * Константа для запроса получения проекта по его ID.
     */
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE id = ?;";

    /**
     * Константа для запроса удаления проекта по его ID.
     */
    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE id = ?;";

    /**
     * Константа для запроса обновления проекта в БД.
     */
    private static final String UPDATE_PROJECT_SQL = "UPDATE PROJECT SET title = ?, description = ? WHERE id = ?;";


    @Override
    public boolean add(Project project) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, project.getTitle());
            preparedStatement.setString(index, project.getDescription());
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when adding a project to the database", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public boolean update(Project project) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL)) {
            int index = 1;
            preparedStatement.setString(index++, project.getTitle());
            preparedStatement.setString(index++, project.getDescription());
            preparedStatement.setInt(index, project.getId());

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when updating the project in the database", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT_SQL)) {
            int index = 1;
            preparedStatement.setInt(index, id);

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when deleting a project from the database", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    @Override
    public List<Project> getAll() throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PROJECTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                Project project = getProjectFromDB(resultSet);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when getting all projects from the database", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }

    /**
     * Заполнение объекта Project данными из БД.
     *
     * @param resultSet результирующий набор данных
     * @return проект
     */
    private static Project getProjectFromDB(ResultSet resultSet) {
        try {
            Project project = new Project();
            project.setId(resultSet.getInt(PROJECT_ID));
            project.setTitle(resultSet.getString(TITLE));
            project.setDescription(resultSet.getString(DESCRIPTION));
            return project;
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when retrieving task data from the database", e);
        }
    }

    @Override
    public Project getById(Integer id) throws DaoException {

        Connection connection = ConnectionManipulator.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Project project = getProjectFromDB(resultSet);
                return project;
            }
            else {
                throw new DaoException("A project with such data was not found");
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException("Error when getting a project by id from the database", e);
        }
        finally {
            ConnectionManipulator.closeConnection(connection);
        }
    }
}
