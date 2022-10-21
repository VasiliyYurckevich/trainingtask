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
package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.servlets.validation.ValidationService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Задача".
 *
 * @author Q-YVV
 * @see Task
 */
public class TaskController extends HttpServlet {

    /**
     * Хранит название кейса для выбора списка задач.
     */
    public static final String LIST = "/list";

    /**
     * Хранит название JSP редактирования задачи.
     */
    private static final String EDIT_TASK_FORM_JSP = "/edit-task-form.jsp";

    /**
     * Хранит константу для обозначения действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Хранит константу для обозначения ID задачи.
     */
    private static final String TASK_ID = "taskId";

    /**
     * Хранит константу для обозначения статуса задачи.
     */
    private static final String STATUS = "status";

    /**
     * Хранит константу для обозначения названия задачи.
     */
    private static final String TITLE = "title";

    /**
     * Хранит константу для обозначения ID проекта задачи.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Хранит константу для обозначения времени на выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Хранит константу для обозначения даты начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Хранит константу для обозначения даты окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    /**
     * Хранит константу для обозначения ID сотрудника, ответственного за задачу.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Хранит константу для порядкового номера задачи в списке задач проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Хранит текст для исключения при выборе неизвестной команды.
     */
    public static final String UNKNOWN_COMMAND_OF_TASK_CONTROLLER = "Unknown command of Task Controller:";

    /**
     * Хранит название JSP редактирования проекта.
     */
    private static final String EDIT_PROJECT_JSP = "/edit-project-form.jsp";

    /**
     * Хранит название JSP добавления задачи.
     */
    private static final String ADD_TASK_FORM_JSP = "/add-task-form.jsp";

    /**
     * Хранит константу для страницы списка задач.
     */
    private static final String TASKS = "tasks";

    /**
     * Хранит константу для проекта.
     */
    private static final String PROJECT = "project";

    /**
     * Хранит константу для задачи.
     */
    private static final String TASK = "task";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());

    /**
     * Переменная доступа к методам работы с сущностями Project.
     */
    private ProjectService projectService = new ProjectService();

    /**
     * Переменная доступа к методам работы с сущностями Task.
     */
    private TaskService taskService = new TaskService();

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/save":
                    saveTask(req, resp);
                    break;
                case "/updateTaskInProject":
                    updateTaskInProject(req, resp);
                    break;
                case "/newTaskInProject":
                    newTaskInProject(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_TASK_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doPost method in Task Controller", e);
            throw e;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = LIST;
            }

            switch (action) {
                case "/edit":
                    editTaskForm(req, resp);
                    break;
                case "/delete":
                    deleteTask(req, resp);
                    break;
                case "/new":
                    newTaskForm(req, resp);
                    break;
                case LIST:
                    listTasks(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_TASK_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doGet method in Task Controller", e);
            throw e;
        }
    }

    /**
     * Открывает и заполняет форму редактирования задачи.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServiceException, ServletException, IOException {

        final Task task;
        if (req.getParameter(TASK_ID) != null) {
            task = taskService.getById(Integer.valueOf(req.getParameter(TASK_ID)));
        }
        else {
            task = new Task();
            System.out.println(task);
        }
        req.getSession().setAttribute(TASK, task);
        Utils.setTaskDataInJsp(req, task);
        req.getRequestDispatcher(EDIT_TASK_FORM_JSP).forward(req, resp);
    }

    /**
     * Сохраняет изменения в задаче в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void saveTask(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        HttpSession session = req.getSession();

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkTaskData(paramsMap);

        if (errorsMap.isEmpty()) {
            Task task = (Task) session.getAttribute(TASK);
            updateTaskData(paramsMap, task);
            taskService.save(task);
            resp.sendRedirect(TASKS);
        }
        else {
            setDataAboutTaskInJsp(req, paramsMap, errorsMap);
            req.setAttribute(PROJECT_ID, Integer.parseInt(paramsMap.get(PROJECT_ID)));
            req.getRequestDispatcher(EDIT_TASK_FORM_JSP).forward(req, resp);
        }
    }

    /**
     * Заполняет поля задачи.
     *
     * @param paramsMap поля задачи
     */
    private static void updateTaskData(Map<String, String> paramsMap, Task task) {
        task.setStatus(Status.getStatusById(Integer.parseInt(paramsMap.get(STATUS))));
        task.setTitle(paramsMap.get(TITLE));
        task.setWorkTime(Integer.valueOf(paramsMap.get(WORK_TIME)));
        task.setBeginDate(LocalDate.parse(paramsMap.get(BEGIN_DATE)));
        task.setEndDate(LocalDate.parse(paramsMap.get(END_DATE)));
        task.setProjectId(Integer.valueOf(paramsMap.get(PROJECT_ID)));
        if (!paramsMap.get(EMPLOYEE_ID).isEmpty()) {
            task.setEmployeeId(Integer.valueOf(paramsMap.get(EMPLOYEE_ID)));
        }
        else {
            task.setEmployeeId(null);
        }
    }

    /**
     * Добавляет новую задачу во время редактирования проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkTaskData(paramsMap);

        if (errorsMap.isEmpty()) {
            HttpSession session = req.getSession();
            Project project = (Project) session.getAttribute(PROJECT);
            Task task = (Task) session.getAttribute(TASK);

            updateTaskData(paramsMap, task);
            projectService.addTask(project, task);
            req.getRequestDispatcher(EDIT_PROJECT_JSP).forward(req, resp);
        }
        else {
            setDataAboutTaskInJsp(req, paramsMap, errorsMap);
            req.getRequestDispatcher("/add-task-in-project.jsp").forward(req, resp);
        }
    }

    /**
     * Изменяет данные задачи во время редактирования проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {


        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkTaskData(paramsMap);

        if (errorsMap.isEmpty()) {
            HttpSession session = req.getSession();
            Project project = (Project) session.getAttribute(PROJECT);
            Integer taskIndex = (Integer) req.getSession().getAttribute(TASK_INDEX);
            Task task = (Task) session.getAttribute(TASK);

            updateTaskData(paramsMap, task);
            projectService.updateTask(project, taskIndex, task);
            req.getRequestDispatcher(EDIT_PROJECT_JSP).forward(req, resp);
        }
        else {
            setDataAboutTaskInJsp(req, paramsMap, errorsMap);
            req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
        }
    }


    /**
     * Открывает форму добавления задачи.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        req.getSession().setAttribute(TASK, new Task());
        req.getRequestDispatcher(ADD_TASK_FORM_JSP).forward(req, resp);
    }

    /**
     * Удаляет задачу из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        String taskId = req.getParameter(TASK_ID);
        taskService.delete(Integer.parseInt(taskId));
        resp.sendRedirect(TASKS);
    }

    /**
     * Вносит данные о задаче в форму.
     *
     * @param paramsMap список данных из формы
     */
    private void setDataAboutTaskInJsp(HttpServletRequest req,
        Map<String, String> paramsMap, Map<String, String> errorsMap) {
        System.out.println(paramsMap);
        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(STATUS, paramsMap.get(STATUS));
        req.setAttribute(TITLE, paramsMap.get(TITLE));
        req.setAttribute(WORK_TIME, paramsMap.get(WORK_TIME));
        req.setAttribute(BEGIN_DATE, paramsMap.get(BEGIN_DATE).trim());
        req.setAttribute(END_DATE, paramsMap.get(END_DATE).trim());
        if (!paramsMap.get(EMPLOYEE_ID).isEmpty()) {
            req.setAttribute(EMPLOYEE_ID, Integer.valueOf(paramsMap.get(EMPLOYEE_ID)));
        }
        else {
            req.setAttribute(EMPLOYEE_ID, "");
        }
    }

    /**
     * Выводит список задач.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка работы сервисов с сущностью
     */
    private void listTasks(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        req.getSession().invalidate();
        Utils.setDataToList(req);

        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }

    /**
     * Вносит задачу из формы в список.
     *
     * @param req запрос
     * @return список данных из формы
     */
    private Map<String, String> getDataFromForm(HttpServletRequest req) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(STATUS, req.getParameter(STATUS));
        paramsMap.put(TITLE , req.getParameter(TITLE).trim());
        paramsMap.put(WORK_TIME, req.getParameter(WORK_TIME).trim());
        paramsMap.put(BEGIN_DATE, req.getParameter(BEGIN_DATE).trim());
        paramsMap.put(END_DATE, req.getParameter(END_DATE).trim());
        if (req.getParameter(PROJECT_ID) != null) {
            paramsMap.put(PROJECT_ID, req.getParameter(PROJECT_ID));
        }
        else {
            Task task = (Task) req.getSession().getAttribute(TASK);
            paramsMap.put(PROJECT_ID, task.getProjectId().toString());
        }
        paramsMap.put(EMPLOYEE_ID, req.getParameter(EMPLOYEE_ID));
        return paramsMap;
    }
}
