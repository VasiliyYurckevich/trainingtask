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

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface IDao<T> {

    /**
     * Добавляет новую сущность в БД.
     *
     * @param t Сущность для добавления.
     * @return успешность операции.
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    boolean add(T t) throws DaoException, PathNotValidException;

    /**
     * Обновляет сущность в БД.
     *
     * @param t Сущность для обновления.
     * @return успешность операции.
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    boolean update(T t) throws DaoException, PathNotValidException;

    /**
     * Удаляет сущность из БД.
     *
     * @param t Сущность для удаления.
     * @return успешность операции.
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    boolean delete(Integer t) throws DaoException, PathNotValidException;

    /**
     * Возвращает все сущности определеного класса из БД.
     *
     * @return Список сущностей
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    List<T> getAll() throws DaoException, PathNotValidException;

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность
     * @throws PathNotValidException если путь не валидный или название параметра не совпадает с ожидаемым
     * @throws DaoException если произошла ошибка при записи/полусении данных из БД
     */
    T getById(Integer id) throws DaoException, PathNotValidException;
}
