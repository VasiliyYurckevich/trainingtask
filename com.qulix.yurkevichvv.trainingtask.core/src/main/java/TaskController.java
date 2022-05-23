import java.io.*;
import java.sql.SQLException;
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

    private static final String EDIT_TASK_FORM_JSP = "/edit-task-form.jsp";
    private static final String LIST = "/list";

    private static final String ACTION = "action";

    private static final  String TASK_ID = "taskId";

    private static final  String STATUS = "status";

    private static final  String TITLE = "title";

    private static final  String PROJECT_ID = "projectId";

    private static final  String WORK_TIME = "workTime";

    private static final  String BEGIN_DATE = "beginDate";

    private static final  String END_DATE = "endDate";

    private static final  String EMPLOYEE_ID = "employeeId";

    private static final String EMPLOYEE_LIST = "EMPLOYEE_LIST";

    private static final String PROJECT_LIST = "PROJECT_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String EDIT_PROJECT_JSP = "/edit-project-form.jsp";
    private static final String ADD_TASK_FORM_JSP = "/add-task-form.jsp";


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
        req.setAttribute(TITLE, existingTask.getTitle());
        req.setAttribute(WORK_TIME, existingTask.getWorkTime());
        req.setAttribute(BEGIN_DATE, existingTask.getBeginDate());
        req.setAttribute(END_DATE, existingTask.getEndDate());
        req.setAttribute(PROJECT_ID, existingTask.getProjectId());
        req.setAttribute(EMPLOYEE_ID, existingTask.getEmployeeId());
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
        servletContext.setAttribute("task", existingTask);
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute(EMPLOYEE_LIST, employees);
        servletContext.setAttribute(PROJECT_LIST, projects);
        dispatcher.forward(req, resp);

    }

    /**
     * Method for update task.
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter(TASK_ID));
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            Task task =  new Task(paramsList);
            task.taskId = taskId;
            tasksInterface.update(task);
            listTasks(req, resp);
            LOGGER.info("Update task with id: " + taskId);
        }
        else {
            setDataToJSP(req, resp, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Utils.stringToInteger(paramsList.get(Nums.FIVE.getValue()).trim()));
            req.setAttribute(TASK_ID, taskId);
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Method for add task in project.
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            Task task =  new Task(paramsList);
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
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
            setDataToJSP(req, resp, paramsList, errorsList);
            dispatcher.forward(req, resp);
        }
    }

    /**
     * Method for update task in project.
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Integer taskId = Integer.parseInt(req.getParameter(TASK_ID));
        String numberInList = (String) servletContext.getAttribute(NUMBER_IN_LIST);
        List<String> paramsList = getDataFromForm(req);
        Task task = new Task(paramsList);
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
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
        getServletContext().setAttribute(EMPLOYEE_LIST, employees);
        getServletContext().setAttribute(PROJECT_LIST, projects);
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
        List<String> paramsList =  getDataFromForm(req);
        List<String> errorsList = ValidationService.taskValidator(paramsList);

        if (Utils.isBlankList(errorsList)) {
            Task task =  new Task(paramsList);
            tasksInterface.add(task);
            listTasks(req, resp);
            LOGGER.info("New task created");
        }
        else {
            setDataToJSP(req, resp, paramsList, errorsList);
            req.setAttribute(PROJECT_ID, Utils.stringToInteger(paramsList.get(Nums.FIVE.getValue()).trim()));
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_TASK_FORM_JSP);
            dispatcher.forward(req, resp);
        }
    }

    private void setDataToJSP(HttpServletRequest req, HttpServletResponse resp,
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


