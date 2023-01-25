package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.TaskView;
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
     * Список проектов.
     */
    private static final String LIST = "/list";

    /**
     * JSP редактирования проекта.
     */
    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";
    
    /**
     * Обозначение ID проекта.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Обозначение название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Обозначение описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Обозначение действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Сообщение при выборе неизвестной команды.
     */
    private static final String UNKNOWN_COMMAND_OF_PROJECT_CONTROLLER = "Unknown command of Project Controller:";

    /**
     * Обозначение списка проектов.
     */
    private static final String PROJECTS = "projects";

    /**
     * Обозначение проекта, при редактировании проекта.
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Обозначение индекса задачи в списке проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * Сервис для работы с Project.
     */
    private final ProjectService projectService = new ProjectService();

    /**
     * Переменная доступа к методам работы с задачами проекта.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            if (action.equals("/save")) {
                updateProject(req, resp);
            }
            else {
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
                case "/editTask":
                    editTaskInProjectForm(req, resp);
                    break;
                case "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/deleteTask":
                    deleteTask(req, resp);
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
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
        HttpSession session = req.getSession();
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) session.getAttribute(PROJECT_TEMPORARY_DATA);

        projectTemporaryService.deleteTask(projectTemporaryData, projectTemporaryData.getTasksList().get(taskIndex));

        session.setAttribute(PROJECT_TEMPORARY_DATA, projectTemporaryData);


        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
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

        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        Utils.setTaskDataInJsp(req, getTask(req, projectTemporaryData));

        req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
    }

    /**
     * Находит или создает новую задачу в проекте.
     *
     * @param req запрос
     * @param projectTemporaryData данные проект
     * @return задача проекта для редактирования
     */
    private Task getTask(HttpServletRequest req, ProjectTemporaryData projectTemporaryData) {

        if (!req.getParameter(TASK_INDEX).isBlank()) {
            int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
            req.setAttribute(TASK_INDEX, taskIndex);
            return projectTemporaryData.getTasksList().get(taskIndex);
        }
        else {
            Task task = new Task();
            task.setProjectId(projectTemporaryData.getId());
            return task;
        }
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
        
        ProjectTemporaryData projectTemporaryData = getProjectData(req);
        
        session.setAttribute(PROJECT_TEMPORARY_DATA, projectTemporaryData);
        session.setAttribute("TASK_LIST", TaskView.convertTasksList(projectTemporaryData.getTasksList()));
        
        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }

    /**
     * Возвращает временные данные проекта.
     *
     * @param req запрос
     * @return временные данные проекта
     */
    private ProjectTemporaryData getProjectData(HttpServletRequest req) {
        if (req.getSession().getAttribute(PROJECT_TEMPORARY_DATA) != null) {
            return (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        }

        if (!req.getParameter(PROJECT_ID).isBlank()) {
            return new ProjectTemporaryData(projectService.getById(Integer.valueOf(req.getParameter(PROJECT_ID))));
        }

        return new ProjectTemporaryData(new Project());
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
        projectService.delete(Integer.valueOf(req.getParameter(PROJECT_ID)));
        resp.sendRedirect(PROJECTS);
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

        HttpSession session = req.getSession();
        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);
        req.setAttribute("PROJECT_LIST", projectService.findAll());

        req.getRequestDispatcher("projects.jsp").forward(req, resp);
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

        ProjectTemporaryData project = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            
            setProjectTemporaryData(project, paramsMap);
            projectTemporaryService.save(project);

            resp.sendRedirect(PROJECTS);
        }
        else {
            setDataToJspAfterValidation(req, paramsMap, errorsMap);
            req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
        }
    }

    /**
     * Создает проект с полученными данными.
     *
     * @param paramsMap данные из формы
     */
    private static void setProjectTemporaryData(ProjectTemporaryData projectTemporaryData, Map<String, String> paramsMap) {
        projectTemporaryData.getProject().setTitle(paramsMap.get(TITLE_OF_PROJECT));
        projectTemporaryData.getProject().setDescription(paramsMap.get(DESCRIPTION));
    }

    /**
     * Получает данные из формы.
     *
     * @param req запрос
     * @return список данных
     */
    private Map<String, String> getDataFromForm(HttpServletRequest req) {
        Map<String, String> paramsMap = new HashMap<>();
        HttpSession session = req.getSession();
        
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) session.getAttribute(PROJECT_TEMPORARY_DATA);
        projectTemporaryData.getProject().setTitle(req.getParameter(TITLE_OF_PROJECT));
        projectTemporaryData.getProject().setDescription(req.getParameter(DESCRIPTION));
        
        paramsMap.put(TITLE_OF_PROJECT, projectTemporaryData.getProject().getTitle());
        paramsMap.put(DESCRIPTION, projectTemporaryData.getProject().getDescription());
        return paramsMap;
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
