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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.IDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;
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
     * Пробел.
     */
    private static final String SPACE = " ";

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
     * Хранит константу для обозначения списка проектов.
     */
    private static final String PROJECT_LIST = "PROJECT_LIST";

    /**
     * Хранит константу для обозначения списка задач.
     */
    private static final String TASKS_LIST = "TASKS_LIST";

    /**
     * Хранит константу для обозначения списка сотрудников, ответственных за задачи.
     */
    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    /**
     * Хранит константу для порядкового номера задачи в списке задач проекта.
     */
    private static final String NUMBER_IN_LIST = "numberInList";

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
     * Переменная доступа к методам классов DAO.
     */
    private IDao<Task> tasksInterface;

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());


    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        tasksInterface = new TaskDao();
    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/update":
                    updateTask(req, resp);
                    break;
                case "/updateTaskInProject":
                    updateTaskInProject(req, resp);
                    break;
                case "/newTaskInProject":
                    newTaskInProject(req, resp);
                    break;
                default:
                    addTask(req, resp);
                    break;
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw e;
        }
    }

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = "/list";
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
                default:
                    listTasks(req, resp);
                    break;
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
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
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        String taskId = req.getParameter(TASK_ID);
        Task existingTask = tasksInterface.getById(Integer.parseInt(taskId));
        Utils.setTaskDataInJsp(req, existingTask);
        Utils.setDataToDropDownList(req);
        req.setAttribute(PROJECT_ID, existingTask.getProjectId());
        req.getSession().setAttribute("task", existingTask);

        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Сохраняет изменения в задаче в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {

        int taskId = Integer.parseInt(req.getParameter(TASK_ID));
        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectTaskData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Task task = getTask(paramsList);
            task.setId(taskId);
            tasksInterface.update(task);
            resp.sendRedirect(TASKS);
        }
        else {
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Integer.parseInt(paramsList.get(PROJECT_ID).trim()));
            req.setAttribute(TASK_ID, taskId);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Заполняет поля задачи.
     *
     * @param paramsList поля задачи
     */
    private static Task getTask(Map<String, String> paramsList) {
        Task task = new Task();
        task.setStatus(Status.getStatusById(Integer.parseInt(paramsList.get(STATUS))));
        task.setTitle(paramsList.get(TITLE));
        task.setWorkTime(Integer.parseInt(paramsList.get(WORK_TIME)));
        task.setBeginDate(Date.valueOf(paramsList.get(BEGIN_DATE)));
        task.setEndDate(Date.valueOf(paramsList.get(END_DATE)));
        task.setProjectId(Integer.valueOf(paramsList.get(PROJECT_ID)));
        if (!paramsList.get(EMPLOYEE_ID).isEmpty()) {
            task.setEmployeeId(Integer.valueOf(paramsList.get(EMPLOYEE_ID)));
        }
        else {
            task.setEmployeeId(null);
        }
        return task;
    }

    /**
     * Добавляет новую задачу во время редактирования проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        List<Task> tasksListInProject = (List<Task>) req.getSession().getAttribute(TASKS_LIST);
        List<String> employeeListInProject = (List<String>) req.getSession().getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectTaskData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Task task = getTask(paramsList);
            tasksListInProject.add(task);
            String numberInList = String.valueOf(tasksListInProject.size());
            getEmployeesInProject(req, numberInList, task);
            setListOfTasksInProject(req, tasksListInProject, employeeListInProject);

            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_JSP);
            dispatcher.forward(req, resp);
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Изменяет данные задачи во время редактирования проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        List<Task> tasksListInProject = (List<Task>) req.getSession().getAttribute(TASKS_LIST);

        Integer taskId = null;
        if (!req.getParameter(TASK_ID).equals("")) {
            taskId = Integer.valueOf(req.getParameter(TASK_ID));
        }
        String numberInList = (String) req.getSession().getAttribute(NUMBER_IN_LIST);
        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectTaskData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Task task = getTask(paramsList);
            task.setId(taskId);
            tasksListInProject.set(Integer.parseInt(numberInList), task);
            List<String> employeeListInProject = getEmployeesInProject(req, numberInList, task);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_JSP);
            setListOfTasksInProject(req, tasksListInProject, employeeListInProject);
            req.getSession().setAttribute(NUMBER_IN_LIST, numberInList);
            dispatcher.forward(req, resp);
        }
        else {
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
            req.setAttribute(TASK_ID, taskId);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Обновляет список задач в проекте во время его редактирования.
     *
     * @param req запрос
     * @param tasksListInProject список задач в проекте
     * @param employeeListInProject список сотрудников привязанных к задаче в проекте
     */
    private static void setListOfTasksInProject(HttpServletRequest req,
        List<Task> tasksListInProject, List<String> employeeListInProject) {

        req.getSession().setAttribute(TASKS_LIST, tasksListInProject);
        req.getSession().setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
    }

    /**
     * Вносит данные о сотруднике связанном с задачей в список задач проекта.
     *
     * @param req запрос
     * @param numberInList номер задачи в списке проекта
     * @param task задача
     * @return список сотрудников привязанных к проекту.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static List<String> getEmployeesInProject(HttpServletRequest req, String numberInList, Task task)
        throws DaoException {

        List<String> employeeListInProject = (List<String>) req.getSession().getAttribute(EMPLOYEE_IN_TASKS_LIST);
        if (task.getEmployeeId() == null) {
            try {
                employeeListInProject.set(Integer.parseInt(numberInList), null);
            }
            catch (IndexOutOfBoundsException e) {
                employeeListInProject.add(null);

            }
        }
        else {
            try {
                employeeListInProject.set(Integer.parseInt(numberInList), getNameEmployee(task).toString());
            }
            catch (IndexOutOfBoundsException e) {
                employeeListInProject.add(getNameEmployee(task).toString());
            }
        }
        return employeeListInProject;
    }

    /**
     * Открывает форму добавления задачи.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {

        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
        Utils.setDataToDropDownList(req);

        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет задачу из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, IOException {

        String taskId = req.getParameter(TASK_ID);
        tasksInterface.delete(Integer.parseInt(taskId));
        resp.sendRedirect(TASKS);
    }

    /**
     * Создает новую задачу.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void addTask(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {

        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectTaskData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Task task = getTask(paramsList);
            tasksInterface.add(task);
            resp.sendRedirect(TASKS);
        }
        else {
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Integer.parseInt(paramsList.get(PROJECT_ID).trim()));
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Вносит данные о задаче в форму.
     *
     * @param paramsList список данных из формы
     */
    private void setDataAboutTaskInJsp(HttpServletRequest req,
        Map<String, String> paramsList, Map<String, String> errorsList) {

        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(STATUS, paramsList.get(STATUS));
        req.setAttribute(TITLE, paramsList.get(TITLE));
        req.setAttribute(WORK_TIME, paramsList.get(WORK_TIME).trim());
        req.setAttribute(BEGIN_DATE, paramsList.get(BEGIN_DATE).trim());
        req.setAttribute(END_DATE, paramsList.get(END_DATE).trim());
        if (!paramsList.get(EMPLOYEE_ID).isEmpty()) {
            req.setAttribute(EMPLOYEE_ID, Integer.valueOf(paramsList.get(EMPLOYEE_ID)));
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
     * @throws DaoException если произошла ошибка при записи/получении данных из БД.
     */
    private void listTasks(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        List<Task> tasks = tasksInterface.getAll();
        List<Project> projects = new ProjectDao().getAll();
        List<String> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Task t: tasks) {
            setEmployeeList(employeeOfTask, t);
            Project project = new ProjectDao().getById(t.getProjectId());
            projectsOfTask.add(project);
        }
        req.setAttribute(PROJECT_LIST, projects);
        req.setAttribute(TASKS_LIST, tasks);
        req.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeOfTask);
        req.setAttribute("PROJ_LIST", projectsOfTask);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Выводит список имен исполнителей задачи.
     *
     * @param employeeOfTask список имен исполнителей задачи
     * @param task задача
     */
    private void setEmployeeList(List<String> employeeOfTask, Task task) {
        if (task.getEmployeeId() != null) {
            StringBuffer stringBuffer = getNameEmployee(task);
            employeeOfTask.add(stringBuffer.toString());
        }
        else {
            employeeOfTask.add(null);
        }
    }

    /**
     * Возвращает имя исполнителя задачи.
     *
     * @param task задача
     * @return имя исполнителя задачи
     */
    private static StringBuffer getNameEmployee(Task task) {
        Employee employee = new EmployeeDao().getById(task.getEmployeeId());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(employee.getSurname());
        stringBuffer.append(SPACE);
        stringBuffer.append(employee.getFirstName());
        stringBuffer.append(SPACE);
        stringBuffer.append(employee.getPatronymic());
        return stringBuffer;
    }

    /**
     * Вносит задачу из формы в список.
     *
     * @param req запрос
     * @return список данных из формы
     */
    private Map<String, String> getDataFromForm(HttpServletRequest req) {
        Map<String, String> paramsList = new HashMap<>();
        paramsList.put(STATUS, req.getParameter(STATUS));
        paramsList.put(TITLE , req.getParameter(TITLE).trim());
        paramsList.put(WORK_TIME, req.getParameter(WORK_TIME).trim());
        paramsList.put(BEGIN_DATE, req.getParameter(BEGIN_DATE).trim());
        paramsList.put(END_DATE, req.getParameter(END_DATE).trim());
        if (req.getParameter(PROJECT_ID) != null) {
            paramsList.put(PROJECT_ID, req.getParameter(PROJECT_ID).trim());
        }
        else {
            paramsList.put(PROJECT_ID, req.getSession().getAttribute(PROJECT_ID).toString());
        }
        paramsList.put(EMPLOYEE_ID, req.getParameter(EMPLOYEE_ID));
        return paramsList;
    }
}


