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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;
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
    private static final String NUMBER_IN_LIST = "numberInList";

    /**
     * Хранит константу для обозначения списка задач проекта.
     */
    private static final String DELETED_LIST = "DELETED_LIST";

    /**
     * Хранит константу для обозначения задач проекта.
     */
    private static final String TASKS_LIST = "TASKS_LIST";

    /**
     * Хранит константу для обозначения сотрудников, назначенных на соответствующие задачи.
     */
    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    /**
     * Хранит константу для обозначения ID проекта, при редактировании проекта.
    */
    private static final String THIS_PROJECT_ID = "thisProjectId";

    /**
     * Хранит название кейса для выбора списка сотрудников.
     */
    private static final String LIST = "/list";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * Хранит название кейса для обозначения списка проектов.
     */
    private static final String PROJECTS = "projects";

    /**
     * Пробел.
     */
    public static final String SPACE = " ";

    /**
     * Переменная доступа к методам классов DAO.
     */
    private IDao<Project> projectInterface;

    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        projectInterface = new ProjectDao();
    }


    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        try {
            String action = req.getParameter(ACTION);


            switch (action) {
                case "/add":
                    addProject(req, resp);
                    break;
                case "/update":
                    updateProject(req, resp);
                    break;
            }

        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServletException(e);
        }
        catch (DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new DaoException(e);
        }
    }

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = LIST;
            }

            switch (action) {
                case LIST:
                    listProjects(req, resp);
                    break;
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
            }
        }
        catch (IOException | ServletException | DaoException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ServletException(e);
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

        ServletContext servletContext = getServletContext();
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        List<Integer> deleteTaskInProject = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<String> employeeListInProject = (List<String>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Integer id = tasksListInProject.get(Integer.parseInt(numberInList)).getId();

        tasksListInProject.remove(Integer.parseInt(numberInList));
        employeeListInProject.remove(Integer.parseInt(numberInList));
        if (id != null) {
            deleteTaskInProject.add(id);
        }
        setParametersAboutProjectEditing(servletContext, deleteTaskInProject, tasksListInProject, employeeListInProject);
        servletContext.setAttribute(NUMBER_IN_LIST, numberInList);
        editProjectForm(req, resp);
    }

    /**
     * Назначает списки задач и сотрудников для редактирования проекта.
     *
     * @param servletContext контекст сервлета
     * @param deleteTaskInProject список задач, которые были удалены из проекта
     * @param tasksListInProject список задач в проекте
     * @param employeeListInProject список имен сотрудников в проекте
     */
    private static void setParametersAboutProjectEditing(ServletContext servletContext,
        List<Integer> deleteTaskInProject, List<Task> tasksListInProject, List<String> employeeListInProject) {

        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        servletContext.setAttribute(DELETED_LIST, deleteTaskInProject);
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

        ServletContext servletcontext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletcontext.getAttribute(TASKS_LIST);
        Integer thisProjectId = (Integer) servletcontext.getAttribute(PROJECT_ID);
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);

        Task existingTask = tasksListInProject.get(Integer.parseInt(numberInList));
        Utils.setTaskDataInJsp(req, existingTask);
        servletcontext.setAttribute(PROJECT_ID, thisProjectId);

        Utils.setDataToDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
        servletcontext.setAttribute("task", existingTask);
        dispatcher.forward(req, resp);

    }

    /**
     * Отображает форму для редактирования проект.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        ServletContext servletContext = getServletContext();
        Integer thisProjectId = getProjectId(req, servletContext);
        Project existingProject = projectInterface.getById(thisProjectId);
        setDataAboutProject(servletContext, existingProject);
        existingProject.setId(thisProjectId);

        List<Task> tasksListInProject = getTasksInProject(existingProject, servletContext);
        List<String> employeeListInProject = getEmployeesInProject(servletContext, tasksListInProject);
        List<Integer> deletedTasks = getDeletedTasks(servletContext);

        servletContext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        servletContext.setAttribute("project", existingProject);
        setParametersAboutProjectEditing(servletContext, deletedTasks, tasksListInProject, employeeListInProject);
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);

    }

    /**
     * Возвращает список удаленных задач во время редактирования проекта.
     *
     * @param servletContext контекст сервлета
     * @return список удаленных задач
     */
    private static List<Integer> getDeletedTasks(ServletContext servletContext) {
        List<Integer> deletedTasks = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        if (deletedTasks == null) {
            deletedTasks = new ArrayList<>();
        }
        return deletedTasks;
    }

    /**
     * Возвращает список сотрудников привязанных к задачам проекта.
     *
     * @param servletContext контекст сервлета
     * @param tasksListInProject список задач в проекте
     * @return список сотрудников
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static List<String> getEmployeesInProject(ServletContext servletContext, List<Task> tasksListInProject)
        throws DaoException {

        List<String> employeeListInProject = (List<String>) servletContext.getAttribute("EMP_LIST");
        if (employeeListInProject == null) {
            employeeListInProject = new ArrayList<>();
            for (Task t : tasksListInProject) {
                if (t.getEmployeeId() != null) {
                    Employee employee = new EmployeeDao().getById(t.getEmployeeId());
                    StringBuilder stringBuffer = new StringBuilder();
                    stringBuffer.append(employee.getSurname());
                    stringBuffer.append(SPACE);
                    stringBuffer.append(employee.getFirstName());
                    stringBuffer.append(SPACE);
                    stringBuffer.append(employee.getPatronymic());
                    employeeListInProject.add(stringBuffer.toString());
                }
                else {
                    employeeListInProject.add(null);
                }
            }
        }
        return employeeListInProject;
    }

    /**
     * Возвращает список задач проекта.
     *
     * @param existingProject проект
     * @param servletContext контекст сервлета
     * @return список задач в проекте
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static List<Task> getTasksInProject(Project existingProject, ServletContext servletContext)
        throws DaoException {

        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        if (tasksListInProject == null) {
            tasksListInProject = new TaskDao().getTasksInProject(existingProject.getId());
        } 
        return tasksListInProject;
    }

    /**
     * Возвращает номер проекта.
     *
     * @param req запрос
     * @param servletContext контекст сервлета
     * @return номер проекта
     */
    private static Integer getProjectId(HttpServletRequest req, ServletContext servletContext) {
        Integer thisProjectId;
        try {
            thisProjectId = Integer.valueOf(req.getParameter(PROJECT_ID));
        }
        catch (NumberFormatException e) {
            thisProjectId = (Integer) servletContext.getAttribute(PROJECT_ID);

        }
        return thisProjectId;
    }

    /**
     * Заносит данные о проекте в контекст сервлета.
     *
     * @param servletContext контекст сервлета
     * @param existingProject проект
     */
    private static void setDataAboutProject(ServletContext servletContext, Project existingProject) {
        servletContext.setAttribute(PROJECT_ID, existingProject.getId());
        servletContext.setAttribute(TITLE_OF_PROJECT, existingProject.getTitle());
        servletContext.setAttribute(DESCRIPTION, existingProject.getDescription());
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

        Integer projectId = Integer.parseInt(req.getParameter(PROJECT_ID));
        projectInterface.delete(projectId);
        LOGGER.log(Level.INFO, "Task with id {0} was deleted", projectId);
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

        ServletContext servletcontext = getServletContext();
        Integer thisProjectId = (Integer) servletcontext.getAttribute(PROJECT_ID);
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);
        servletcontext.setAttribute(PROJECT_ID, thisProjectId);
        Utils.setDataToDropDownList(req);
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
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     * @throws DaoException если произошла ошибка при записи/получение данных из БД
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {

        Utils.setDataToDropDownList(req);
        removeServletAttributes();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет атрибуты сервлета.
     */
    private void removeServletAttributes() {
        ServletContext servletContext = getServletContext();
        servletContext.removeAttribute(TASKS_LIST);
        servletContext.removeAttribute(EMPLOYEE_IN_TASKS_LIST);
        servletContext.removeAttribute(DELETED_LIST);
        servletContext.removeAttribute(PROJECT_ID);
        servletContext.removeAttribute(TITLE_OF_PROJECT);
        servletContext.removeAttribute(DESCRIPTION);
        servletContext.removeAttribute(NUMBER_IN_LIST);
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

        TaskDao taskInterface = new TaskDao();
        ServletContext servletContext = getServletContext();

        Integer projectId = (Integer) servletContext.getAttribute(PROJECT_ID);
        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectProjectData(paramsList);
        if (Utils.isBlankMap(errorsList)) {
            Project theProject = getProject(paramsList);
            theProject.setId(projectId);
            updateTasksFromProjectEditing(taskInterface, servletContext, projectId);
            projectInterface.update(theProject);
            LOGGER.log(Level.INFO, "Project with id {0} updated", projectId);

            resp.sendRedirect(PROJECTS);

        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsList, errorsList);
            servletContext.setAttribute(PROJECT_ID, projectId);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Создает и обновляет задачи проекта во время изменения проекта.
     *
     * @param taskInterface интерфейс для работы с задачами
     * @param servletContext контекст сервлета
     * @param projectId идентификатор проекта
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private static void updateTasksFromProjectEditing(TaskDao taskInterface, ServletContext servletContext, Integer projectId)
        throws DaoException {

        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Integer> deleteTaskIdProject = (List<Integer>) servletContext.getAttribute(DELETED_LIST);

        for (Task task : tasksListInProject) {
            task.setProjectId(projectId);
            if (task.getId() != null) {
                taskInterface.update(task);
            }
            else {
                task.setId(null);
                taskInterface.add(task);
            }
        }
        for (Integer id : deleteTaskIdProject) {
            if (id != null) {
                taskInterface.delete(id);
            }
        }
    }

    /**
     * Создает проект с полученными данными.
     *
     * @param paramsList данные из формы
     * @return проект
     */
    private static Project getProject(Map<String, String> paramsList) {
        Project theProject = new Project();
        theProject.setTitle(paramsList.get(TITLE_OF_PROJECT));
        theProject.setDescription(paramsList.get(DESCRIPTION));
        return theProject;
    }

    /**
     * Получает данные из формы.
     *
     * @param req запрос
     * @return список данных
     */
    private Map<String, String> getDataFromForm(HttpServletRequest req) {
        Map<String, String> paramsList = new HashMap<>();
        paramsList.put(TITLE_OF_PROJECT, req.getParameter(TITLE_OF_PROJECT));
        paramsList.put(DESCRIPTION, req.getParameter(DESCRIPTION));
        return paramsList;
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

        Map<String, String> paramsList = getDataFromForm(req);
        Map<String, String> errorsList = ValidationService.inspectProjectData(paramsList);

        if (Utils.isBlankMap(errorsList)) {
            Project theProject = getProject(paramsList);
            projectInterface.add(theProject);
            LOGGER.log(Level.INFO, "Created new project");
            resp.sendRedirect(PROJECTS);

        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsList, errorsList);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Вносит в форму введенные данные и сообщения об ошибках.
     *
     * @param req запрос
     * @param paramsList данные из формы
     * @param errorsList сообщения об ошибках
     */
    private void setDataToJspAfterValidation(HttpServletRequest req,
        Map<String, String> paramsList, Map<String, String> errorsList) {

        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(TITLE_OF_PROJECT, paramsList.get(TITLE_OF_PROJECT).trim());
        req.setAttribute(DESCRIPTION, paramsList.get(DESCRIPTION).trim());
    }
}
