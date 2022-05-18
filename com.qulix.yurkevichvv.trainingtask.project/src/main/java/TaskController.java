import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for tasks.
 *
 *<p> {@link TaskController} using to interact with  Tasks in application.</p>
 *
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class TaskController extends HttpServlet {

    private static final String LIST = "/list";

    private static final String ACTION = "action";

    private static final  String TASK_ID = "taskId";

    private static final  String STATUS = "status";

    private static final  String TITLE = "title";

    private static final  String PROJECT_ID = "project_id";

    private static final  String WORK_TIME = "work_time";

    private static final  String BEGIN_DATE = "begin_date";

    private static final  String END_DATE = "end_date";

    private static final  String EMPLOYEE_ID = "employee_id";

    private static final String EMPLOYEE_LIST = "EMPLOYEE_LIST";

    private static final String PROJECT_LIST = "PROJECT_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String EDIT_PROJECT_JSP = "/edit-project-form.jsp";
    

    private DAOInterface<Task> tasksInterface;

    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());


    /**
     * Initialize TaskController.
     */
    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        tasksInterface = new DAOTask();
    }

    /**
     * Processes requests for  HTTP POST methods.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }
    }

    /**
     * Processes requests for  HTTP GET methods.
     */
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
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }
    }


    /**
     * Method for open update task form.
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String theTaskId = req.getParameter(TASK_ID);
        Task existingTask = tasksInterface.getById(Integer.valueOf(theTaskId));
        req.setAttribute(TASK_ID, existingTask.getId());
        req.setAttribute(STATUS, existingTask.getStatus());
        req.setAttribute(TASK_ID, (existingTask.getTitle()));
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("projectId", existingTask.getProjectId());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        servletContext.setAttribute("task", existingTask);
        servletContext.setAttribute(EMPLOYEE_LIST, employees);
        servletContext.setAttribute(PROJECT_LIST, projects);
        dispatcher.forward(req, resp);

    }

    /**
     * Method for update task.
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter(TASK_ID));
        Task task = getDataFromForm(req, taskId);
        tasksInterface.update(task);
        listTasks(req, resp);
        LOGGER.info("Update task with id: " + taskId);
    }

    /**
     * Method for add task in project.
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Task task = getDataFromForm(req, null);
        tasksListInProject.add(task);
        try {
            employeeListInProject.add(new DAOEmployee().getById(task.getEmployeeId()));
        }
        catch (NullPointerException e) {
            employeeListInProject.add(null);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_JSP);
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for update task in project.
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Integer taskId;
        try {
            taskId = Integer.parseInt(req.getParameter(TASK_ID));
        }
        catch (NumberFormatException e) {
            taskId = null;
        }
        String numberInList = (String) servletContext.getAttribute(NUMBER_IN_LIST);
        Task task = getDataFromForm(req, taskId);
        tasksListInProject.set(Integer.parseInt(numberInList), task);
        try {
            employeeListInProject.set(Integer.parseInt(numberInList), new DAOEmployee().getById(task.getEmployeeId()));
        }
        catch (NullPointerException e) {
            employeeListInProject.set(Integer.parseInt(numberInList), null);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_JSP);
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        servletContext.setAttribute(NUMBER_IN_LIST, numberInList);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open new task form.
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute(EMPLOYEE_LIST, employees);
        req.setAttribute(PROJECT_LIST, projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete task.
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter(TASK_ID);
        tasksInterface.delete(Integer.valueOf(theTaskId));
        listTasks(req, resp);
        LOGGER.info("Task with id " + theTaskId + " delete");
    }

    /**
     * Method for add new task.
     */
    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Task task = getDataFromForm(req, null);

        tasksInterface.add(task);
        listTasks(req, resp);
        LOGGER.info("New task created");
    }

    /**
     * Method for list tasks.
     */
    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Task> tasks =  tasksInterface.getAll();
        List<Project> projects = new DAOProject().getAll();
        List<Employee> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Task t: tasks) {
            Employee employee = new DAOEmployee().getById(t.getEmployeeId());
            employeeOfTask.add(employee);
            Project project = new DAOProject().getById(t.getProjectId());
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
     * Method for get data from form.
     */
    private Task getDataFromForm(HttpServletRequest req, Integer taskId) {
        String status = req.getParameter(STATUS);
        String title = req.getParameter(TITLE);
        long workTime = Long.parseLong(req.getParameter(WORK_TIME));
        LocalDate beginDate = LocalDate.parse(req.getParameter(BEGIN_DATE));
        LocalDate endDate = LocalDate.parse(req.getParameter(END_DATE));
        Integer projectId;
        Integer employeeId;
        try {
            projectId = Integer.parseInt(req.getParameter(PROJECT_ID));
        }
        catch (NumberFormatException e) {
            projectId = null;
        }
        try {
            employeeId = Integer.parseInt(req.getParameter(EMPLOYEE_ID));
        }
        catch (NumberFormatException e) {
            employeeId = null;
        }
        Task task;
        if (taskId == null) {
            task = new Task(status, title, workTime, beginDate, endDate, projectId,  employeeId);
        }
        else {
            task = new Task(taskId, status, title, workTime, beginDate, endDate, projectId,  employeeId);
        }
        return task;
    }
}


