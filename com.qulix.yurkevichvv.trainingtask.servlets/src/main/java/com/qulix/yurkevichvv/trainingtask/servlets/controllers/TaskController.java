package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.TaskValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Задача".
 *
 * @author Q-YVV
 * @see Task
 */
public class TaskController extends HttpServlet {

    /**
     * Кейс списка задач.
     */
    public static final String LIST = "/list";

    /**
     * JSP редактирования задачи.
     */
    private static final String EDIT_TASK_FORM_JSP = "/edit-task-form.jsp";

    /**
     * Обозначение действия сервлета.
     */
    private static final String ACTION = "action";

    /**
     * Обозначение ID задачи.
     */
    private static final String TASK_ID = "taskId";

    /**
     * Обозначение ID проекта задачи.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Порядковый номер задачи в списке задач проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Сообщение при выборе неизвестной команды.
     */
    public static final String UNKNOWN_COMMAND_OF_TASK_CONTROLLER = "Unknown command of Task Controller:";

    /**
     * Хранит константу для страницы списка задач.
     */
    private static final String TASKS = "tasks";

    /**
     * Хранит константу для проекта.
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());

    /**
     * Переменная доступа к методам работы с задачами проекта.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Переменная доступа к методам работы с сущностями Task.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Переменная доступа к методам работы с сущностями Project.
     */
    private final ProjectService projectService = new ProjectService();

    private final PageDataService<Task> taskPageDataService = new TaskPageDataService();

    private final PageDataService<ProjectTemporaryData> projectPageDataService = new ProjectPageDataService();

    private final ValidationService validationService = new TaskValidation();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/save":
                    saveTask(req, resp);
                    break;
                case "/saveTaskInProject":
                    saveTaskInProject(req, resp);
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

        taskPageDataService.setDataToPage(req,  taskPageDataService.getEntity(req));

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

        Map<String, String> paramsMap = taskPageDataService.getDataFromPage(req);
        Map<String, String> errorsMap = validationService.validate(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {

            Task task = taskPageDataService.getEntity(req);

            taskPageDataService.setOutputDataToEntity(paramsMap, task);
            taskService.save(task);

            resp.sendRedirect(TASKS);
        }
        else {
            taskPageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
            req.setAttribute(PROJECT_ID, Integer.parseInt(paramsMap.get(PROJECT_ID)));
            req.getRequestDispatcher(EDIT_TASK_FORM_JSP).forward(req, resp);
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
    private void saveTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, ServiceException {

        Map<String, String> paramsMap = taskPageDataService.getDataFromPage(req);
        Map<String, String> errorsMap = validationService.validate(paramsMap);

        if (errorsMap.values().stream().allMatch(Objects::isNull)) {
            HttpSession session = req.getSession();
            ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) session.getAttribute(PROJECT_TEMPORARY_DATA);
            Integer taskIndex;
            Task task;
            if (req.getParameter(TASK_INDEX).isBlank()){
                taskIndex = null;
                task = new Task();
            }
            else {
                taskIndex = Integer.valueOf(req.getParameter(TASK_INDEX));
                task = projectTemporaryData.getTasksList().get(taskIndex);
            }
            taskPageDataService.setOutputDataToEntity(paramsMap, task);
            projectPageDataService.setDataToPage(req, projectTemporaryData);
            saveTaskInProjectData(task, projectTemporaryData, taskIndex);

            req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
        }
        else {
            taskPageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
            req.setAttribute(TASK_INDEX,req.getParameter(TASK_INDEX));
            req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
        }
    }

    /**
     * Сохраняет новую задачу в данные проекта либо обновляет уже существующую.
     *
     * @param task задача
     * @param project временные данные проекта
     * @param taskIndex индекс задачи в списке задач проекта
     */
    private void saveTaskInProjectData(Task task, ProjectTemporaryData project, Integer taskIndex) {
        if (taskIndex != null) {
            task.setId(project.getTasksList().get(taskIndex).getId());
            projectTemporaryService.updateTask(project, taskIndex, task);

        }
        else {
            projectTemporaryService.addTask(project, task);
        }
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

        HttpSession session = req.getSession();
        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("IS_ZERO_PROJECTS", projectService.findAll().isEmpty());
        req.setAttribute("TASKS_LIST",TaskView.convertTasksList(taskService.findAll()) );

        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
