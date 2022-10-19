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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
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
     * Хранит название кейса для выбора списка проектов.
     */
    private static final String LIST = "/list";

    /**
     * Хранит текст для исключения при выборе неизвестной команды.
     */
    private static final String UNKNOWN_COMMAND_OF_PROJECT_CONTROLLER = "Unknown command of Employee Controller:";

    /**
     * Хранит название кейса для обозначения списка проектов.
     */
    private static final String PROJECTS = "projects";

    /**
     * Хранит константу для обозначения проекта, при редактировании проекта.
     */
    private static final String PROJECT = "project";

    /**
     * Хранит константу для обозначения индекса задачи в списке проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Хранит константу для обозначения задачи проекта.
     */
    private static final String TASK = "task";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * Сервис для работы с Project.
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
     * @throws ServiceException при ошибке удаления задачи из списка проекта
     */
    private void deleteTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {
        
        int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
        Project project = (Project) req.getSession().getAttribute(PROJECT);

        projectService.deleteTask(project, project.getTasksList().get(taskIndex));

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
     */
    private void editTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession();
        Project project = (Project) session.getAttribute(PROJECT);
        int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
        Task existingTask = project.getTasksList().get(taskIndex);

        Utils.setTaskDataInJsp(req, existingTask);
        session.setAttribute(TASK_INDEX, taskIndex);
        session.setAttribute(TASK, existingTask);

        req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
    }

    /**
     * Отображает форму для редактирования проект.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при попытке получения данных проекта
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        HttpSession session = req.getSession();

        if (session.getAttribute(PROJECT) == null) {
            Integer projectId = Integer.valueOf(req.getParameter(PROJECT_ID));
            setDataAboutProject(session, projectService.getById(projectId));
        }

        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }

    /**
     * Заносит данные о проекте в сессию.
     *
     * @param session сессия
     * @param existingProject проект
     */
    private static void setDataAboutProject(HttpSession session, Project existingProject) {
        session.setAttribute(PROJECT, existingProject);
        session.setAttribute(TITLE_OF_PROJECT, existingProject.getTitle());
        session.setAttribute(DESCRIPTION, existingProject.getDescription());
    }

    /**
     * Удаляет проект из БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при попытке удаления проекта
     */
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        Integer projectId = Integer.valueOf(req.getParameter(PROJECT_ID));
        projectService.delete(projectId);
        resp.sendRedirect(PROJECTS);
    }

    /**
     * Открывает страницу создания задачи и вносит данные о новой задаче в форму.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    private void newTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession();
        Project project = (Project) session.getAttribute(PROJECT);

        Task task = new Task();
        task.setProjectId(project.getId());
        session.setAttribute(TASK, task);

        req.getRequestDispatcher("/add-task-in-project.jsp").forward(req, resp);
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
        req.getRequestDispatcher(ADD_PROJECT_FORM_JSP).forward(req, resp);
    }

    /**
     * Выводит список проектов.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException ошибка при получении данных сущностей
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        req.getSession().invalidate();
        Utils.setDataToList(req);

        req.getRequestDispatcher("/projects.jsp").forward(req, resp);
    }


    /**
     * Изменяет данные проекта в БД.
     *
     * @param req запрос
     * @param resp ответ
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws ServiceException при ошибке сохранения проекта
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Project project = (Project) req.getSession().getAttribute(PROJECT);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.isEmpty()) {
            Project theProject = getProject(project, paramsMap);
            projectService.save(theProject);

            resp.sendRedirect(PROJECTS);
        }
        else {
            req.getSession().setAttribute(PROJECT, project);
            setDataToJspAfterValidation(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
        }
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
     * @throws ServiceException ошибка при работе сервиса с сущностью
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Project project = (Project) req.getSession().getAttribute(PROJECT);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.isEmpty()) {
            Project theProject = getProject(project, paramsMap);
            projectService.save(theProject);

            resp.sendRedirect(PROJECTS);
        }
        else {
            setDataToJspAfterValidation(req, paramsMap, errorsMap);
            req.getRequestDispatcher(ADD_PROJECT_FORM_JSP).forward(req, resp);
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
