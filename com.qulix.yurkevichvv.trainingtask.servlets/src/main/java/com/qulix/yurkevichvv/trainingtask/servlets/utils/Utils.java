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
package com.qulix.yurkevichvv.trainingtask.servlets.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;


/**
 * Утилиты для классов модуля.
 *
 * @author Q-YVV
 */
public class Utils {


    /**
     * Проверяет Map на пустоту значений.
     *
     * @return Возвращает true, если список пустой или содержащий только пустые строки.
     */
    public static boolean isBlankMap(Map<String, String> map) {
        return map.isEmpty() || map.values().stream().allMatch(String::isEmpty);
    }

    /**
     * Переводит String в Integer.
     *
     * @param s строка для конвертации.
     * @return конвертированное значение.
     */
    public static Integer convertStringToInteger(String s) {
        if (s.equals("null")) {
            return null;
        }
        else {
            return Integer.parseInt(s);
        }
    }


    /**
     * Создает и обновляет данные для выпадающих списков.
     *
     * @param req запрос
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public static void setDataToDropDownList(HttpServletRequest req) throws DaoException {
        List<Employee> employees = new EmployeeDao().getAll();
        List<Project> projects = new ProjectDao().getAll();
        req.getServletContext().setAttribute("EMPLOYEE_LIST", employees);
        req.getServletContext().setAttribute("PROJECT_LIST", projects);
        req.getServletContext().setAttribute("STATUS_LIST", Status.values());

    }

    /**
     * Заносит начальные данные в поля формы для Задачи.
     *
     * @param req запрос
     * @param existingTask задача
     */
    public static void setTaskDataInJsp(HttpServletRequest req, Task existingTask) {
        req.setAttribute("taskId", existingTask.getId());
        req.setAttribute("status", existingTask.getStatus().getId());
        req.setAttribute("title", existingTask.getTitle());
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
    }
}
