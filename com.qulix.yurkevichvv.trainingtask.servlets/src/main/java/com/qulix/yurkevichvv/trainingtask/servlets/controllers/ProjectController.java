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
import java.sql.Connection;
import java.util.Enumeration;
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
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.dao.DaoException;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.servlets.validation.ValidationService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Проект".
 *
 * @author Q-YVV
 * @see Project
 */
public class ProjectController extends HttpServlet {

    /**
     * Хранит название JSP добавления проекта.
     */
    private static final String ADD_PROJECT_FORM_JSP = "/add-project-form.jsp";

    /**
     * Хранит название JSP редактирования сотрудника.
     */
    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    /**
     * Хранит константу для обозначения ID проекта.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Хранит константу для обозначения название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Хранит константу для обозначения описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Хранит константу для обозначения действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Хранит константу для порядкового номера задачи в списке задач проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Хранит константу для обозначения задач проекта.
     */
    private static final String TASKS_LIST = "TASKS_LIST";


    /**
     * Хранит название кейса для выбора списка проектов.
     */
    private static final String LIST = "/list";

    /**
     * Хранит текст для исключения при выборе неизвестной команды.
     */
    public static final String UNKNOWN_COMMAND_OF_PROJECT_CONTROLLER = "Unknown command of Employee Controller:";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * Хранит название кейса для обозначения списка проектов.
     */
    private static final String PROJECTS = "projects";

    /**
     * Хранит константу для обозначения проекта, при редактировании проекта.
     */
    public static final String PROJECT = "project";

    /**
     * Переменная доступа к методам классов DAO.
     */
    private final ProjectService projectService = new ProjectService();

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/update":
                    updateProject(req, resp);
                    break;
                case "/add":
                    addProject(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_PROJECT_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doPost method in Project Controller", e);
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
                case "/delete":
                    deleteProject(req, resp);
                    break;
                case "/editTaskForm":
                    editTaskInProjectForm(req, resp);
                    break;
                case "/addTaskForm":
                    newTaskInProjectForm(req, resp);
                    break;
                case "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
                case "/deleteTaskInProject":
                    deleteTaskInProject(req, resp);
                    break;
                case LIST:
                    listProjects(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(UNKNOWN_COMMAND_OF_PROJECT_CONTROLLER + action);
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in doGet method in Project Controller", e);
            throw e;
        }
    }

    /**
     * Удаляет задачу из списка задач во время редактирования проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void deleteTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        
        HttpSession session = req.getSession();
        Task task = (Task) req.getAttribute("task");
        Project project = (Project) session.getAttribute(PROJECT);

        projectService.deleteTask(project, task);

        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Отображает форму для редактирования задачи в проекте.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void editTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        HttpSession session = req.getSession();
        Project project = (Project) session.getAttribute(PROJECT);
        Task existingTask = (Task) req.getAttribute("task");

        Utils.setTaskDataInJsp(req, existingTask);
        session.setAttribute(PROJECT, project);

        Utils.setDataToList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
        session.setAttribute("task", existingTask);
        dispatcher.forward(req, resp);
    }

    /**
     * Отображает форму для редактирования проект.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession();
        String existingProject =  req.getParameter(PROJECT);
        System.out.println(existingProject);
        //setDataAboutProject(session, existingProject);

        session.setAttribute(PROJECT, existingProject);
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Возвращает список задач проекта.
     *
     * @param existingProject проект
     * @param session сессия
     * @return список задач в проекте
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static List<Task> getTasksInProject(Project existingProject, HttpSession session)
        throws DaoException {

        List<Task> tasksListInProject = (List<Task>) session.getAttribute(TASKS_LIST);
        if (tasksListInProject == null) {
            tasksListInProject = new TaskDao().getTasksInProject(existingProject.getId());
        } 
        return tasksListInProject;
    }

    /**
     * Заносит данные о проекте в сессию.
     *
     * @param session сессия
     * @param existingProject проект
     */
    private static void setDataAboutProject(HttpSession session, Project existingProject) {
        session.setAttribute(PROJECT, existingProject);
        System.out.println(existingProject);
        session.setAttribute(TITLE_OF_PROJECT, existingProject.getTitle());
        session.setAttribute(DESCRIPTION, existingProject.getDescription());
    }

    /**
     * Удаляет проект из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, IOException {

        Project project = (Project) req.getAttribute(PROJECT);
        projectService.delete(project.getId());
        resp.sendRedirect(PROJECTS);
    }

    /**
     * Открывает страницу создания задачи и вносит данные о новой задаче в форму.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получение данных из БД
     */
    private void newTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        HttpSession session = req.getSession();
        Project project = (Project) session.getAttribute(PROJECT);
        session.setAttribute(PROJECT, project);
        Utils.setDataToList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Открывает форму для создания проекта.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    /**
     * Выводит список проектов.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получение данных из БД
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        Utils.setDataToList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет атрибуты сервлета в сессии.
     *
     * @param session текущая сессия
     */
    private void removeServletAttributes(HttpSession session) {
        //Collections.list(session.getAttributeNames()).stream().filter(name -> name != "PROJECT_LIST").forEach(name -> session.removeAttribute(name));

        Enumeration keys = session.getAttributeNames();

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key != "PROJECT_LIST"){
                session.removeAttribute(key);
            }
        }
    }

    /**
     * Изменяет данные проекта в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        Project project = (Project) req.getSession().getAttribute(PROJECT);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.isEmpty()) {
            Project theProject = getProject(project, paramsMap);
            projectService.save(theProject);
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsMap, errorsMap);
            req.getSession().setAttribute(PROJECT, project);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Создает и обновляет задачи проекта во время изменения проекта.
     *
     * @param taskDao объект для работы с задачами
     * @param session сессия
     * @param projectId идентификатор проекта
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static void updateTasksFromProjectEditing(TaskDao taskDao, Connection connection,
        HttpSession session, Integer projectId) throws DaoException {

    /*    List<Task> tasksListInProject = (List<Task>) session.getAttribute(TASKS_LIST);
        List<Integer> deleteTaskIdProject = (List<Integer>) session.getAttribute(DELETED_LIST);

        for (Task task : tasksListInProject) {
            task.setProjectId(projectId);
            if (task.getId() != null) {
                taskDao.update(task, connection);
            }
            else {
                taskDao.add(task, connection);
            }
        }
        for (Integer id : deleteTaskIdProject) {
            if (id != null) {
                taskDao.delete(id, connection);
            }
        }*/
    }

    /**
     * Создает проект с полученными данными.
     *
     * @param paramsMap данные из формы
     * @return проект
     */
    private static Project getProject(Project project, Map<String, String> paramsMap) {
        project.setTitle(paramsMap.get(TITLE_OF_PROJECT));
        project.setDescription(paramsMap.get(DESCRIPTION));
        return project;
    }

    /**
     * Получает данные из формы.
     *
     * @param req запрос
     * @return список данных
     */
    private Map<String, String> getDataFromForm(HttpServletRequest req) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(TITLE_OF_PROJECT, req.getParameter(TITLE_OF_PROJECT));
        paramsMap.put(DESCRIPTION, req.getParameter(DESCRIPTION));
        return paramsMap;
    }

    /**
     * Добавляет новый проект в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, DaoException, IOException {

        Project project = (Project) req.getSession().getAttribute(PROJECT);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.isEmpty()) {
            Project theProject = getProject(project, paramsMap);
            projectService.save(theProject);
            resp.sendRedirect(PROJECTS);
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsMap, errorsMap);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Вносит в форму введенные данные и сообщения об ошибках.
     *
     * @param req запрос
     * @param paramsMap данные из формы
     * @param errorsMap сообщения об ошибках
     */
    private void setDataToJspAfterValidation(HttpServletRequest req,
        Map<String, String> paramsMap, Map<String, String> errorsMap) {

        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(TITLE_OF_PROJECT, paramsMap.get(TITLE_OF_PROJECT).trim());
        req.setAttribute(DESCRIPTION, paramsMap.get(DESCRIPTION).trim());
    }
}
