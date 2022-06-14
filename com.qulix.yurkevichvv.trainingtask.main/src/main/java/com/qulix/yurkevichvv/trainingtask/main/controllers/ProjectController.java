package com.qulix.yurkevichvv.trainingtask.main.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.main.dao.EmployeeDAO;
import com.qulix.yurkevichvv.trainingtask.main.dao.IDao;
import com.qulix.yurkevichvv.trainingtask.main.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.main.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.entity.Project;
import com.qulix.yurkevichvv.trainingtask.main.entity.Task;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.PathNotValidException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.main.validation.ValidationService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Проект".
 *
 * @author Q-YVV
 * @see Project
 */
public class ProjectController extends HttpServlet {

    private static final String ADD_PROJECT_FORM_JSP = "/add-project-form.jsp";

    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    private static final String PROJECT_ID = "projectId";

    private static final String TITLE_OF_PROJECT = "titleProject";

    private static final String DESCRIPTION = "description";

    private static final String ACTION = "action";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String DELETED_LIST = "DELETED_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String THIS_PROJECT_ID = "thisProjectId";

    private static final String LIST = "/list";

    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    private static final String PROJECTS = "projects";


    /**
     * Получение интерфейса для работы с БД.
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
        catch (Exception e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new ServletException("Ошибка в сервлете " + getServletName(), e);
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
        catch (Exception e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new ServletException("Ошибка в сервлете " + getServletName(), e);
        }
    }


    /**
     * Удаляет задачу из списка задач во время редактирования проекта.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws IOException исключения ввода-вывода.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     */
    private void deleteTaskInProject(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

        ServletContext servletContext = getServletContext();
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        List<Integer> deleteTaskInProject = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
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
     * @param servletContext контекст сервлета.
     * @param deleteTaskInProject список задач, которые были удалены из проекта.
     * @param tasksListInProject список задач в проекте.
     * @param employeeListInProject список сотрудников в проекте.
     */
    private static void setParametersAboutProjectEditing(ServletContext servletContext,
        List<Integer> deleteTaskInProject, List<Task> tasksListInProject, List<Employee> employeeListInProject) {

        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        servletContext.setAttribute(DELETED_LIST, deleteTaskInProject);
    }

    /**
     * Отображает форму для редактирования задачи в проекте.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws IOException исключения ввода-вывода.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     */
    private void editTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

        ServletContext servletcontext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletcontext.getAttribute(TASKS_LIST);
        Integer thisProjectId = (Integer) servletcontext.getAttribute(THIS_PROJECT_ID);
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);

        Task existingTask = tasksListInProject.get(Integer.parseInt(numberInList));
        Utils.setTaskDataInJsp(req, existingTask);
        servletcontext.setAttribute(THIS_PROJECT_ID, thisProjectId);

        Utils.setDataToDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edi3t-task-in-project.jsp");
        servletcontext.setAttribute("task", existingTask);
        dispatcher.forward(req, resp);

    }

    /**
     * Отображает форму для редактирования проект.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

        ServletContext servletContext = getServletContext();
        Integer thisProjectId = getProjectId(req, servletContext);
        Project existingProject = projectInterface.getById(thisProjectId);
        setDataAboutProject(servletContext, existingProject);
        existingProject.setId(thisProjectId);

        List<Task> tasksListInProject = getTasksInProject(existingProject, servletContext);
        List<Employee> employeeListInProject = getEmployeesInProject(servletContext, tasksListInProject);
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
     * @param servletContext контекст сервлета.
     * @return список удаленных задач.
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
     * @param servletContext контекст сервлета.
     * @param tasksListInProject список задач в проекте.
     * @return список сотрудников.
     * @throws SQLException исключения БД.
     */
    private static List<Employee> getEmployeesInProject(ServletContext servletContext, List<Task> tasksListInProject)
            throws DaoException, PathNotValidException {

        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute("EMP_LIST");
        if (employeeListInProject == null) {
            employeeListInProject = new ArrayList<>();
            for (Task t : tasksListInProject) {
                Employee employee = new EmployeeDAO().getById(t.getEmployeeId());
                employeeListInProject.add(employee);
            }
        }
        return employeeListInProject;
    }

    /**
     * Возвращает список задач проекта.
     *
     * @param existingProject проект.
     * @param servletContext контекст сервлета.
     * @return список задач в проекте.
     * @throws SQLException исключения БД.
     */
    private static List<Task> getTasksInProject(Project existingProject, ServletContext servletContext)
            throws DaoException, PathNotValidException {

        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        if (tasksListInProject == null) {
            tasksListInProject = new TaskDao().getTasksInProject(existingProject.getId());
        } 
        return tasksListInProject;
    }

    /**
     * Возвращает номер проекта.
     *
     * @param req запрос.
     * @param servletContext контекст сервлета.
     * @return номер проекта.
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
     * @param servletContext контекст сервлета.
     * @param existingProject проект.
     */
    private static void setDataAboutProject(ServletContext servletContext, Project existingProject) {
        servletContext.setAttribute(PROJECT_ID, existingProject.getId());
        servletContext.setAttribute(TITLE_OF_PROJECT, existingProject.getTitle());
        servletContext.setAttribute(DESCRIPTION, existingProject.getDescription());
    }

    /**
     * Удаляет проект из БД.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, IOException, PathNotValidException {

        Integer projectId = Integer.parseInt(req.getParameter(PROJECT_ID));
        projectInterface.delete(projectId);
        LOGGER.log(Level.INFO, "Task with id {0} was deleted", projectId);
        resp.sendRedirect(PROJECTS);

    }

    /**
     * Создает страницу создания задачи и вносит данные о новой задаче в форму.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void newTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

        ServletContext servletcontext = getServletContext();
        Integer thisProjectId = (Integer) servletcontext.getAttribute(THIS_PROJECT_ID);
        String numberInList = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);
        servletcontext.setAttribute(PROJECT_ID, thisProjectId);
        Utils.setDataToDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
        dispatcher.forward(req, resp);

    }

    /**
     * Создает форму для создания проекта.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
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
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

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
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException, PathNotValidException {

        TaskDao taskInterface = new TaskDao();
        ServletContext servletContext = getServletContext();

        Integer projectId = (Integer) servletContext.getAttribute(PROJECT_ID);
        Map<String,String> paramsList = getDataFromForm(req);
        Map<String,String> errorsList = ValidationService.checkingProjectData(paramsList);
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
     * @param taskInterface интерфейс для работы с задачами.
     * @param servletContext контекст сервлета.
     * @param projectId идентификатор проекта.
     * @throws SQLException исключения БД.
     */
    private static void updateTasksFromProjectEditing(TaskDao taskInterface, ServletContext servletContext, Integer projectId)
            throws DaoException, PathNotValidException {

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
     * @param paramsList данные из формы.
     * @return проект.
     */
    private static Project getProject(Map<String,String> paramsList) {
        Project theProject = new Project();
        theProject.setTitle(paramsList.get(TITLE_OF_PROJECT));
        theProject.setDescription(paramsList.get(DESCRIPTION));
        return theProject;
    }

    /**
     * Получает данные из формы.
     *
     * @param req запрос.
     * @return список данных.
     */
    private Map<String,String> getDataFromForm(HttpServletRequest req) {
        Map<String,String> paramsList = new HashMap<>();
        paramsList.put(TITLE_OF_PROJECT, req.getParameter(TITLE_OF_PROJECT));
        paramsList.put(DESCRIPTION, req.getParameter(DESCRIPTION));
        return paramsList;
    }

    /**
     * Добавляет новый проект в БД.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException исключения сервлета.
     * @throws SQLException исключения БД.
     * @throws IOException исключения ввода-вывода.
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, DaoException, IOException, PathNotValidException {

        Map<String,String> paramsList = getDataFromForm(req);
        Map<String,String> errorsList = ValidationService.checkingProjectData(paramsList);

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
     * @param req запрос.
     * @param paramsList данные из формы.
     * @param errorsList сообщения об ошибках.
     */
    private void setDataToJspAfterValidation(HttpServletRequest req, Map<String, String> paramsList, Map<String,String> errorsList) {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(TITLE_OF_PROJECT, paramsList.get(TITLE_OF_PROJECT).trim());
        req.setAttribute(DESCRIPTION, paramsList.get(DESCRIPTION).trim());
    }
}
