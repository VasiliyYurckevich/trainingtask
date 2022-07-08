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
     * @param paramsList Список параметров для валидации
     * @return Список ошибок
     */
    public static Map<String, String> inspectEmployeeData(Map<String, String> paramsList) {
        Map<String, String> errorList = new HashMap<>(paramsList.size());
        errorList.put(SURNAME, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(SURNAME), SHORT_LENGTH));
        errorList.put(FIRST_NAME, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(FIRST_NAME), SHORT_LENGTH));
        errorList.put(PATRONYMIC, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(PATRONYMIC), SHORT_LENGTH));
        errorList.put(POST, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(POST), SHORT_LENGTH));
        return errorList;
    }

    /**
     * Проверяет вводимые данные о проекте.
     *
     * @param paramsList Список параметров для валидации
     * @return Список ошибок
     */
    public static Map<String, String> inspectProjectData(Map<String, String> paramsList) {
        Map<String, String> errorList = new HashMap<>(paramsList.size());
        errorList.put(TITLE_OF_PROJECT,
            FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(TITLE_OF_PROJECT), SHORT_LENGTH));
        errorList.put(DESCRIPTION, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(DESCRIPTION), LONG_LENGTH));
        return errorList;
    }

    /**
     * Проверяет вводимые данные о задаче.
     *
     * @param paramsList Список параметров для валидации
     * @return Список ошибок
     */
    public static Map<String, String> inspectTaskData(Map<String, String> paramsList) {
        Map<String, String> errorList = new HashMap<>();
        errorList.put(STATUS, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(STATUS), SHORT_LENGTH));
        errorList.put(TITLE, FieldsValidation.inspectValidityOfTheEnteredString(paramsList.get(TITLE), SHORT_LENGTH));
        errorList.put(WORK_TIME, FieldsValidation.inspectNumberValidity(paramsList.get(WORK_TIME)));
        errorList.putAll(FieldsValidation.inspectDateValidity(paramsList.get("beginDate"),
            paramsList.get("endDate")));
        return errorList;
    }

}
