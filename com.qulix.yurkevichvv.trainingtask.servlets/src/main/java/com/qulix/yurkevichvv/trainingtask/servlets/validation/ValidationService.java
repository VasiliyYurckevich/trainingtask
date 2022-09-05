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
package com.qulix.yurkevichvv.trainingtask.servlets.validation;

import com.qulix.yurkevichvv.trainingtask.api.validation.FieldsValidation;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для валидации данных на соответственных страницах.
 *
 * @author Q-YVV
 * @see FieldsValidation
 */
public class ValidationService {
    
    /**
     * Хранит константу для обозначения статуса задачи.
     */
    private static final String STATUS = "status";

    /**
     * Хранит константу для обозначения названия задачи.
     */
    private static final String TITLE = "title";
    
    /**
     * Хранит константу для обозначения времени на выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Хранит константу для обозначения название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Хранит константу для обозначения описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Хранит константу для обозначения фамилии сотрудника.
     */
    private static final String SURNAME = "surname";

    /**
     * Хранит константу для обозначения даты начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Хранит константу для обозначения даты окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    /**
     * Хранит константу для обозначения имени сотрудника.
     */
    private static final String FIRST_NAME = "firstName";

    /**
     * Хранит константу для обозначения отчества сотрудника.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Хранит константу для обозначения должности сотрудника.
     */
    private static final String POST = "post";
    
    /**
     * Хранит константу длинны короткой строки.
     */
    private static final int SHORT_LENGTH = 50;
    
    /**
     * Хранит константу длинны короткой строки.
     */
    private static final int LONG_LENGTH = 250;

    /**
     * Проверяет вводимые данные о сотруднике.
     *
     * @param paramsMap Map, где ключ - имя соответствующего поля Employee, значение - введенное пользователем значение
     * @return Map, где ключ - имя соответствующего поля Employee, значение - сообщение об ошибке для данного поля
     */
    public static Map<String, String> checkEmployeeData(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.compute(SURNAME, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(SURNAME), SHORT_LENGTH));
        errorList.compute(FIRST_NAME, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(FIRST_NAME), SHORT_LENGTH));
        errorList.compute(PATRONYMIC, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(PATRONYMIC), SHORT_LENGTH));
        errorList.compute(POST,
            (k, v) -> FieldsValidation.checkString(paramsMap.get(POST), SHORT_LENGTH));
        return errorList;
    }

    /**
     * Проверяет вводимые данные о проекте.
     *
     * @param paramsMap Map, где ключ - имя соответствующего поля Project, значение - введенное пользователем значение
     * @return Map, где ключ - имя соответствующего поля Project, значение - сообщение об ошибке для данного поля
     */
    public static Map<String, String> checkProjectData(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.compute(TITLE_OF_PROJECT, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(TITLE_OF_PROJECT), SHORT_LENGTH));
        errorList.compute(DESCRIPTION, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(DESCRIPTION), LONG_LENGTH));
        return errorList;
    }

    /**
     * Проверяет вводимые данные о задаче.
     *
     * @param paramsMap Map, где ключ - имя соответствующего поля Task, значение - введенное пользователем значение
     * @return Map, где ключ - имя соответствующего поля Task, значение - сообщение об ошибке для данного поля
     */
    public static Map<String, String> checkTaskData(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>();
        errorList.compute(STATUS, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(STATUS), SHORT_LENGTH));
        errorList.compute(TITLE, (k, v) ->
            FieldsValidation.checkString(paramsMap.get(TITLE), SHORT_LENGTH));
        errorList.compute(WORK_TIME, (k, v) ->
            FieldsValidation.checkNumber(paramsMap.get(WORK_TIME)));
        errorList.putAll(FieldsValidation.checkDate(paramsMap.get(BEGIN_DATE), paramsMap.get(END_DATE)));
        return errorList;
    }

}
