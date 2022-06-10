package com.qulix.yurkevichvv.trainingtask.main.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;
import com.qulix.yurkevichvv.trainingtask.main.utils.Utils;
import com.qulix.yurkevichvv.trainingtask.main.validation.ValidationService;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Задача".
 *
 * @author Q-YVV
 * @see Task
 */
public class TaskController extends HttpServlet {

    private static final String EDIT_TASK_FORM_JSP = "/edit-task-form.jsp";

    private static final String LIST = "/list";

    private static final String ACTION = "action";

    private static final String TASK_ID = "taskId";

    private static final String STATUS = "status";

    private static final String TITLE = "title";

    private static final String PROJECT_ID = "projectId";

    private static final String WORK_TIME = "workTime";

    private static final String BEGIN_DATE = "beginDate";

    private static final String END_DATE = "endDate";

    private static final String EMPLOYEE_ID = "employeeId";

    private static final String EMPLOYEE_LIST = "EMPLOYEE_LIST";

    private static final String PROJECT_LIST = "PROJECT_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String EDIT_PROJECT_JSP = "/edit-project-form.jsp";

    private static final String ADD_TASK_FORM_JSP = "/add-task-form.jsp";

    private static final String TASKS = "tasks";


    private IDao<Task> tasksInterface;

    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());


    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        tasksInterface = new TaskDao();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String action = req.getParameter(ACTION);

            switch (action) {
                case "/add":
                    addTask(req, resp);
                    break;
                case "/update":
                    updateTask(req, resp);
                    break;
                case "/updateTaskInProject":
                    updateTaskInProject(req, resp);
                    break;
                case "/newTaskInProject":
                    newTaskInProject(req, resp);
                    break;
            }
        }
        catch (IOException | DaoException | ServletException e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
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
                case LIST:
                    listTasks(req, resp);
                    break;
                case "/edit":
                    editTaskForm(req, resp);
                    break;
                case "/delete":
                    deleteTask(req, resp);
                    break;
                case "/new":
                    newTaskForm(req, resp);
                    break;
            }
        }
        catch (IOException | DaoException | ServletException e) {
            LOGGER.severe(getServletName() + ": " + e.getMessage());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }


    /**
     * Открывает и заполняет форму редактирования задачи.
     *
     * @param req запрос.
     * @param resp ответ.
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String taskId = req.getParameter(TASK_ID);
        Task existingTask = tasksInterface.getById(Integer.parseInt(taskId));
        Utils.setTaskDataInJsp(req, existingTask);
        req.setAttribute(PROJECT_ID, existingTask.getProjectId());
        servletContext.setAttribute("task", existingTask);
        Utils.setDataOfDropDownList(req);

        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
        dispatcher.forward(req, resp);

    }

    /**
     * Сохраняет изменения в задаче.
     *
     * @param req запрос.
     * @param resp ответ.
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {
        int taskId = Integer.parseInt(req.getParameter(TASK_ID));
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
            Task task = getTask(paramsList);
            task.setId(taskId);
            tasksInterface.update(task);
            resp.sendRedirect(TASKS);
            LOGGER.log(Level.INFO, "Task with id {0} was updated", taskId);
        }
        else {
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Utils.stringToInteger(paramsList.get(Nums.FIVE.getValue()).trim()));
            req.setAttribute(TASK_ID, taskId);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Заполняет поля задачи.
     *
     * @param paramsList поля задачи.
     */
    private static Task getTask(List<String> paramsList) {
        Task task = new Task();
        task.setStatus(paramsList.get(Nums.ZERO.getValue()));
        task.setTitle(paramsList.get(Nums.ONE.getValue()));
        task.setWorkTime(Utils.stringToInteger(paramsList.get(Nums.TWO.getValue())));
        task.setBeginDate(LocalDate.parse(paramsList.get(Nums.THREE.getValue())));
        task.setEndDate(LocalDate.parse(paramsList.get(Nums.FOUR.getValue())));
        task.setProjectId(Utils.stringToInteger(paramsList.get(Nums.FIVE.getValue())));
        task.setEmployeeId(Utils.stringToInteger(paramsList.get(Nums.SIX.getValue())));
        return task;
    }

    /**
     * Добавляет новую задачу во время редактирования проекта.
     *
     * @param req запрос.
     * @param resp ответ.
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
            Task task = getTask(paramsList);
            tasksListInProject.add(task);
            if (task.getEmployeeId() == null) {
                employeeListInProject.add(null);
            }
            else {
                employeeListInProject.add(new EmployeeDAO().getById(task.getEmployeeId()));
            }
            setListOfTasksInProject(servletContext, tasksListInProject, employeeListInProject);

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
     * @param req запрос.
     * @param resp ответ.
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);

        Integer taskId = Utils.stringToInteger(req.getParameter(TASK_ID));
        String numberInList = (String) servletContext.getAttribute(NUMBER_IN_LIST);
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
            Task task = getTask(paramsList);
            task.setId(taskId);
            tasksListInProject.set(Integer.parseInt(numberInList), task);
            List<Employee> employeeListInProject = getEmployeesInProject(servletContext, numberInList, task);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_JSP);
            setListOfTasksInProject(servletContext, tasksListInProject, employeeListInProject);
            servletContext.setAttribute(NUMBER_IN_LIST, numberInList);
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
     * @param servletContext контекст сервлета.
     * @param tasksListInProject список задач.
     * @param employeeListInProject список сотрудников привязанных к задаче.
     */
    private static void setListOfTasksInProject(ServletContext servletContext,
        List<Task> tasksListInProject, List<Employee> employeeListInProject) {
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
    }

    /**
     * Вносит данные о сотруднике связанном с задачей в список задач проекта.
     *
     * @param servletContext контекст сервлета.
     * @param numberInList номер задачи в списке проекта
     * @param task задача
     * @return список сотрудников привязанных к проекту.
     * @throws SQLException исключения БД.
     */
    private static List<Employee> getEmployeesInProject(ServletContext servletContext, String numberInList, Task task)
        throws DaoException {
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        if (task.getEmployeeId() == null) {
            employeeListInProject.set(Integer.parseInt(numberInList), null);
        }
        else {
            employeeListInProject.set(Integer.parseInt(numberInList), new EmployeeDAO().getById(task.getEmployeeId()));
        }
        return employeeListInProject;
    }

    /**
     * Создает форму добавления задачи.
     *
     * @param req запрос.
     * @param resp ответ.
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, DaoException {
        List<Employee> employees = new EmployeeDAO().getAll();
        List<Project> projects = new ProjectDao().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
        getServletContext().setAttribute(EMPLOYEE_LIST, employees);
        getServletContext().setAttribute(PROJECT_LIST, projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Удаляет задачу из БД.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, IOException {
        String taskId = req.getParameter(TASK_ID);
        tasksInterface.delete(Integer.parseInt(taskId));
        resp.sendRedirect(TASKS);
        LOGGER.log(Level.INFO, "Task with id {0} was deleted", taskId);
    }

    /**
     * Создает новую задачу.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void addTask(HttpServletRequest req, HttpServletResponse resp)
        throws DaoException, ServletException, IOException {
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
            Task task = getTask(paramsList);
            tasksInterface.add(task);
            resp.sendRedirect(TASKS);
            LOGGER.log(Level.INFO, "Created new task");
        }
        else {
            setDataAboutTaskInJsp(req, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Utils.stringToInteger(paramsList.get(Nums.FIVE.getValue()).trim()));
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Вносит данные о задаче в форму.
     *
     * @param paramsList список данных из формы.
     */
    private void setDataAboutTaskInJsp(HttpServletRequest req,
        List<String> paramsList, List<String> errorsList) {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(STATUS, paramsList.get(Nums.ZERO.getValue()).trim());
        req.setAttribute(TITLE, paramsList.get(Nums.ONE.getValue()).trim());
        req.setAttribute(WORK_TIME, paramsList.get(Nums.TWO.getValue()).trim());
        req.setAttribute(BEGIN_DATE, paramsList.get(Nums.THREE.getValue()).trim());
        req.setAttribute(END_DATE, paramsList.get(Nums.FOUR.getValue()).trim());
        req.setAttribute(EMPLOYEE_ID, Utils.stringToInteger(paramsList.get(Nums.SIX.getValue()).trim()));
    }

    /**
     * Выводит список задач.
     *
     * @param req запрос.
     * @param resp ответ.
     * @throws SQLException исключения БД.
     * @throws ServletException исключения сервлета.
     * @throws IOException исключения ввода-вывода.
     */
    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        List<Task> tasks = tasksInterface.getAll();
        List<Project> projects = new ProjectDao().getAll();
        List<Employee> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Task t: tasks) {
            Employee employee = new EmployeeDAO().getById(t.getEmployeeId());
            employeeOfTask.add(employee);
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
     * Вносит задачу из формы в список.
     *
     * @param req запрос.
     * @return список данных из формы.
     */
    private List<String> getDataFromForm(HttpServletRequest req) {
        List<String> paramsList = new ArrayList<>();
        paramsList.add(req.getParameter(STATUS));
        paramsList.add(req.getParameter(TITLE));
        paramsList.add(req.getParameter(WORK_TIME));
        paramsList.add(req.getParameter(BEGIN_DATE));
        paramsList.add(req.getParameter(END_DATE));
        paramsList.add(req.getParameter(PROJECT_ID));
        paramsList.add(req.getParameter(EMPLOYEE_ID));
        return paramsList;
    }
}


