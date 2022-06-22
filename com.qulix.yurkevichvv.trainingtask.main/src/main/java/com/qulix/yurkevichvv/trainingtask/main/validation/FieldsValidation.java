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
package com.qulix.yurkevichvv.trainingtask.main.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;

/**
 * Валидация для полей вводимых данных.
 *
 * @author Q-YVV
 */
public class FieldsValidation {

    /**
     * Проверяет на валидность вводимую строку.
     *
     * @param s Строка для валидации
     * @param length максимальная длина строки
     * @return Строка с ошибкой или пустой строкой
     */
    public static String stringValidityCheck(String s, int length) {
        StringBuffer error = new StringBuffer();
        checkForAnEmptyField(s, error);
        if (error.length() == 0) {
            if (s.trim().length() > length) {
                error.append("Максимальная длинна ввода: ");
                error.append(length);
                error.append(" символов");
            }
        }
        return error.toString();
    }

    /**
     * Проверяет численное поле.
     *
     * @param s Строка для валидации
     * @return Строка с ошибкой или пустой строкой
     */
    public static String numberValidityCheck(String s) {
        StringBuffer error = new StringBuffer();
        checkForAnEmptyField(s, error);
        if (error.length() == 0) {
            if (!s.trim().matches("^[0-9]+$")) {
                error.append("Поле принимает только цифры");
            }
            else {
                try {
                    Integer.parseInt(s.trim());
                }
                catch (NumberFormatException e) {
                    error.append("Значение ввода должно быть в промежутке от 0 до 2147483647");
                }
            }
        }
        return error.toString();
    }

    /**
     * Проверяет поле на пустоту.
     *
     * @param s Строка для валидации
     * @param error Строка с ошибкой
     */
    private static void checkForAnEmptyField(String s, StringBuffer error) {
        if (s.isEmpty() || s.trim().length() == 0) {
            error.append("Поле для ввода не должно быть пустым");
        }
    }

    /**
     * Проверяет валидность дат.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @return Строка с ошибкой или пустой строкой
     */
    public static Map<String, String> dateValidityCheck(String beginDate, String endDate) {
        StringBuffer errorBeginDate = new StringBuffer();
        StringBuffer errorEndDate = new StringBuffer();
        Map<String, String> listErrors = new HashMap<>();

        boolean beginDateValid = isDateFormatValid(beginDate, errorBeginDate);
        boolean endDateValid = isDateFormatValid(endDate, errorEndDate);

        if (beginDateValid && endDateValid) {
            checkingTheConsistencyOfDates(beginDate, endDate, errorEndDate);
        }
        listErrors.put("beginDate", errorBeginDate.toString());
        listErrors.put("endDate", errorEndDate.toString());
        return listErrors;
    }

    /**
     * Проверяет вводимые даты на логичность последовательности даты начала и даты окончания.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @param error Строка с ошибкой
     */
    private static void checkingTheConsistencyOfDates(String beginDate, String endDate, StringBuffer error) {
        LocalDate parsedBeginDate = LocalDate.parse(beginDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        if (parsedBeginDate.isAfter(parsedEndDate)) {
            error.append("Дата начала задачи не может быть больше даты окончания задачи");
        }
    }

    /**
     * Проверят формат и существование даты.
     *
     * @param date Дата для проверки.
     * @param error Строка с ошибкой.
     * @return true если дата валидная, false если нет.
     */
    private static boolean isDateFormatValid(String date, StringBuffer error) {
        try {
            LocalDate.parse(date , DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
        }
        catch (DateTimeParseException e) {
            error.append("Введите существующую дату в формате ГГГГ-ММ-ДД");
            return false;
        }
        return true;
    }
}
