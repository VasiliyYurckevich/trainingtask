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
package com.qulix.yurkevichvv.trainingtask.api.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.exceptions.DaoException;

/**
 * Содержит методы для работы объектов класса "Проект" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Project
 */
public class ProjectDao implements IDao<Project>, Serializable {

    /**
     * Хранит константу для колонки ID проекта в БД.
     */
    private static final String ID = "id";

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
    private static final String INSERT_PROJECT_SQL = "INSERT INTO PROJECT (title, description) VALUES (:title, :description);";

    /**
     * Константа для запроса всех проектов из БД.
     */
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECT;";

    /**
     * Константа для запроса получения проекта по его ID.
     */
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE id = :id;";

    /**
     * Константа для запроса удаления проекта по его ID.
     */
    private static final String DELETE_PROJECT_SQL = "DELETE FROM PROJECT WHERE id = :id;";

    /**
     * Константа для запроса обновления проекта в БД.
     */
    private static final String UPDATE_PROJECT_SQL = 
        "UPDATE PROJECT SET title = :title, description = :description WHERE id = :id;";

    @Override
    public void add(Project project, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_PROJECT_SQL, connection)) {
            preparedStatementHelper.setString(TITLE, project.getTitle());
            preparedStatementHelper.setString(DESCRIPTION, project.getDescription());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Project created");
            }
            else {
                LOGGER.log(Level.INFO, "Project creation failed");
            }
        }
        catch (DaoException e) {
            LOGGER.log(Level.SEVERE, "Error when adding a project to the database", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionController.closeConnection(connection);
        }
    }

    @Override
    public void update(Project project, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_PROJECT_SQL, connection)) {
            preparedStatementHelper.setString(TITLE, project.getTitle());
            preparedStatementHelper.setString(DESCRIPTION, project.getDescription());
            preparedStatementHelper.setInt(ID, project.getId());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Project with id {0} updated", project.getId());
            }
            else {
                LOGGER.log(Level.INFO, "Project with id {0} updating failed", project.getId());
            }
        }
        catch (DaoException e) {
            LOGGER.log(Level.SEVERE, "Error when updating the project in the database", e);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id, Connection connection) throws DaoException {
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_PROJECT_SQL, connection)) {
            preparedStatementHelper.setInt(ID, id);
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Project with id {0} deleted", id);
            }
            else {
                LOGGER.log(Level.INFO, "Project with id {0} deleting failed", id);
            }
        }
        catch (DaoException e) {
            LOGGER.log(Level.SEVERE, "Error when deleting a project from the database", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionController.closeConnection(connection);
        }
    }

    @Override
    public List<Project> getAll() throws DaoException {

        Connection connection = ConnectionController.getConnection();

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_PROJECTS, connection);
            ResultSet resultSet = preparedStatementHelper.executeQuery()) {
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                Project project = getProjectFromDB(resultSet);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException | DaoException e) {
            LOGGER.log(Level.SEVERE, "Error when getting all projects from the database", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionController.closeConnection(connection);
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
            project.setId(resultSet.getInt(ID));
            project.setTitle(resultSet.getString(TITLE));
            project.setDescription(resultSet.getString(DESCRIPTION));

            return project;
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error when retrieving task data from the database", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Project getById(Integer id) throws DaoException {

        Connection connection = ConnectionController.getConnection();
        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_PROJECT_BY_ID, connection)) {
            preparedStatementHelper.setInt(ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
                if (resultSet.next()) {
                    return getProjectFromDB(resultSet);
                }
                else {
                    throw new DaoException("A project with such data was not found");
                }
            }
        }
        catch (DaoException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error when getting a project by id from the database", e);
            throw new DaoException(e);
        }
        finally {
            ConnectionController.closeConnection(connection);
        }
    }
}
