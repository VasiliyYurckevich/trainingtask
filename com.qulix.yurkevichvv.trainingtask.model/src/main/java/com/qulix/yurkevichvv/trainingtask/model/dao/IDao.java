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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface IDao<T extends Entity> {

    /**
     * Добавляет новую сущность в БД.
     *
     * @param object Сущность для добавления.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    void add(T object, Connection connection) throws DaoException;


    /**
     * Обновляет сущность в БД.
     *
     * @param object Сущность для обновления.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    void update(T object, Connection connection) throws DaoException;

    /**
     * Удаляет сущность из БД.
     *
     * @param id Идентификатор сущности.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    void delete(Integer id, Connection connection) throws DaoException;

    /**
     * Возвращает все сущности определенного класса из БД.
     *
     * @return Список сущностей
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    List<T> getAll(Connection connection) throws DaoException, SQLException;

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    T getById(Integer id, Connection connection) throws DaoException, SQLException;
}
