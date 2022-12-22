package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import com.qulix.yurkevichvv.trainingtask.model.temporary.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.temporary.ProjectTemporaryService;
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
     * Хранит название кейса для выбора списка проектов.
     */
    private static final String LIST = "/list";

    /**
     * Хранит название JSP редактирования проекта.
     */
    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    /**
     * Хранит название JSP редактирования задачи проекта.
     */
    public static final String EDIT_TASK_IN_PROJECT_JSP = "/edit-task-in-project.jsp";

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
    private ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();


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
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT);

        projectTemporaryService.deleteTask(projectTemporaryData, projectTemporaryData.getTasksList().get(taskIndex));

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
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) session.getAttribute(PROJECT);

        Utils.setTaskDataInJsp(req, getTask(req, projectTemporaryData));

        req.getRequestDispatcher(EDIT_TASK_IN_PROJECT_JSP).forward(req, resp);
    }

    /**
     * Находит или создает новую задачу в проекте.
     *
     * @param req запрос
     * @param project проект
     * @return задача проекта для редактирования
     */
    private Task getTask(HttpServletRequest req, ProjectTemporaryData projectTemporaryData) {
        Task task;
        if (req.getParameter(TASK_INDEX) != null) {
            int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
            task = projectTemporaryData.getTasksList().get(taskIndex);

            req.getSession().setAttribute(TASK_INDEX, taskIndex);
        }
        else {
            task = new Task();
            task.setProjectId(projectTemporaryData.getId());

            req.getSession().setAttribute(TASK_INDEX, null);
        }
        return task;
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

        Project project;

        if (req.getParameter(PROJECT_ID) != null) {
            project = projectService.getById(Integer.valueOf(req.getParameter(PROJECT_ID)));
        }
        else {
            project = new Project();
        }
        setDataAboutProject(req.getSession(), project);
        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }

    /**
     * Заносит данные о проекте в сессию.
     *
     * @param session сессия
     * @param existingProject проект
     */
    private static void setDataAboutProject(HttpSession session, Project existingProject) {
        session.setAttribute(PROJECT, new ProjectTemporaryData(existingProject));
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

        ProjectTemporaryData project = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT);

        Map<String, String> paramsMap = getDataFromForm(req);
        Map<String, String> errorsMap = ValidationService.checkProjectData(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            ProjectTemporaryData theProject = getProject(project, paramsMap);
            projectTemporaryService.save(theProject);

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
     * @return проект
     */
    private static ProjectTemporaryData getProject(ProjectTemporaryData projectTemporaryData, Map<String, String> paramsMap) {
        projectTemporaryData.setTitle(paramsMap.get(TITLE_OF_PROJECT));
        projectTemporaryData.setDescription(paramsMap.get(DESCRIPTION));
        return projectTemporaryData;
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
