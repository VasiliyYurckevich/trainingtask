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

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;

/**
 * Утилиты для классов модуля.
 *
 * @author Q-YVV
 */
public class Utils {

    /**
     * Создает и обновляет данные списков сущностей в БД.
     *
     * @param req запрос
     * @throws ServiceException если произошла ошибка при работе с сущностями
     */
    public static void setDataToList(HttpServletRequest req) throws ServiceException {
        List<Employee> employees = new EmployeeService().findAll();
        List<Project> projects = new ProjectService().findAll();
        List<Task> tasks = new TaskService().findAll();
        req.getSession().setAttribute("EMPLOYEE_LIST", employees);
        req.getSession().setAttribute("PROJECT_LIST", projects);
        req.getSession().setAttribute("STATUS_LIST", Status.values());
        req.getSession().setAttribute("TASKS_LIST", tasks);
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
