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

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.EmployeeConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.ProjectConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.StatusConverter;

/**
 * Утилиты для классов модуля.
 *
 * @author Q-YVV
 */
public class Utils {

    /**
     * Заносит начальные данные в поля формы для Задачи.
     *
     * @param req запрос
     * @param task задача
     */
    public static void setTaskDataInJsp(HttpServletRequest req, Task task) {
        setDropDownLists(req);
        req.setAttribute("taskId", task.getId());
        req.setAttribute("status", task.getStatus().getId());
        req.setAttribute("title", task.getTitle());
        req.setAttribute("workTime", task.getWorkTime());
        req.setAttribute("beginDate", task.getBeginDate());
        req.setAttribute("endDate", task.getEndDate());
        req.setAttribute("projectId", task.getProjectId());
        req.setAttribute("employeeId", task.getEmployeeId());
    }

    public static void setDropDownLists(HttpServletRequest req) {
        req.setAttribute("ProjectList", new ProjectConverter().convertList(new ProjectService().findAll()));
        req.setAttribute("StatusList", new StatusConverter().convertList(List.of(Status.values())));
        req.setAttribute("EmployeeList", new EmployeeConverter().convertList(new EmployeeService().findAll()));
    }
}
