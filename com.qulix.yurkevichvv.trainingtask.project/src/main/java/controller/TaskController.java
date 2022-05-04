package controller;

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

import dao.DAOEmployee;
import dao.DAOInterface;
import dao.DAOProject;
import dao.DAOTask;
import model.Employee;
import model.Project;
import model.Task;
import utilits.Util;

/**
 * Controller for tasks.
 *
 *<p> {@link TaskController} using for control Tasks in application.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code   TaskController taskController = new TaskController();}
 * {@code   taskController.doGet(request, response);}
 * {@code   taskController.doPost(request, response);}
 * {@code   taskController.addTask(request, response);}
 * {@code   taskController.deleteTask(request, response);}
 * {@code   taskController.updateTask(request, response);}
 * {@code   taskController.listTasks(request, response);}
 * {@code   taskController.editTaskForm(request, response);}
 * {@code   taskController.newTaskForm(request, response);}
 * </pre>
 *
 * <h2>Synchronization</h2>
 * <p>
 * This class is not guaranteed to be thread-safe so it should be synchronized externally.
 * </p>
 *
 * <h2>Known bugs</h2>
 * {@link TaskController} does not handle overflows.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 * @see Task
 * @see Project
 * @see Employee
 * @see DAOTask
 * @see DAOProject
 * @see DAOEmployee
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 12345L;
    private DAOInterface<Task> tasksInterface;
    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());


    /**
     * Initialize TaskController.
     *
     * @throws NullPointerException if DAO is null
     * @throws ServletException if an servlet-specific error occurs
     * @see TaskController#doPost(HttpServletRequest, HttpServletResponse)
     * @see TaskController#doGet(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        tasksInterface = new DAOTask();
    }

    /**
     * Processes requests for  HTTP POST methods.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException    if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        try {
            String action = req.getParameter("action");
            
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
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException  if a servlet-specific error occurs
     * @throws IOException   if an I/O error occurs
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        try {

            String action = req.getParameter("action");

            if (action == null) {
                action = "/list";
            }

            switch (action) {
                case "/list":
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
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException   if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String theTaskId = req.getParameter("taskId");
        Task existingTask = tasksInterface.getById(Integer.valueOf(theTaskId));
        req.setAttribute("taskId", existingTask.getId());
        req.setAttribute("status", existingTask.getStatus());
        req.setAttribute("title", Util.htmlSpecialChars(existingTask.getTitle()));
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("projectId", existingTask.getProjectId());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        servletContext.setAttribute("task", existingTask);
        servletContext.setAttribute("EMPLOYEE_LIST", employees);
        servletContext.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);

    }

    /**
     * Method for update task.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if an SQL error occurs
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        Task task = getDataFromForm(req, taskId);
        tasksInterface.update(task);
        listTasks(req, resp);
        LOGGER.info("Update task with id: " + taskId);
    }

    /**
     * Method for add task in project.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if an SQL error occurs
     */
    private void newTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute("TASKS_LIST");
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute("EMPLOYEE_IN_TASKS_LIST");
        Task task = getDataFromForm(req, null);
        tasksListInProject.add(task);
        try {
            employeeListInProject.add(new DAOEmployee().getById(task.getEmployeeId()));
        }
        catch (NullPointerException e) {
            employeeListInProject.add(null);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        servletContext.setAttribute("TASKS_LIST", tasksListInProject);
        servletContext.setAttribute("EMPLOYEE_IN_TASKS_LIST", employeeListInProject);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for update task in project.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if an SQL error occurs
     */
    private void updateTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute("TASKS_LIST");
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute("EMPLOYEE_IN_TASKS_LIST");
        Integer taskId;
        try {
            taskId = Integer.parseInt(req.getParameter("taskId"));
        }
        catch (NumberFormatException e) {
            taskId = null;
        }
        String numberInList = (String) servletContext.getAttribute("numberInList");
        Task task = getDataFromForm(req, taskId);
        tasksListInProject.set(Integer.parseInt(numberInList), task);
        try {
            employeeListInProject.set(Integer.parseInt(numberInList), new DAOEmployee().getById(task.getEmployeeId()));
        }
        catch (NullPointerException e) {
            employeeListInProject.set(Integer.parseInt(numberInList), null);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        servletContext.setAttribute("TASKS_LIST", tasksListInProject);
        servletContext.setAttribute("EMPLOYEE_IN_TASKS_LIST", employeeListInProject);
        servletContext.setAttribute("numberInList", numberInList);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open new task form.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException, SQLException {
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete task.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter("taskId");
        tasksInterface.delete(Integer.valueOf(theTaskId));
        listTasks(req, resp);
        LOGGER.info("Task with id " + theTaskId + " delete");
    }

    /**
     * Method for add new task.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Task task = getDataFromForm(req, null);
        tasksInterface.add(task);
        listTasks(req, resp);
        LOGGER.info("New task created");
    }
    /**
     * Method for list tasks.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
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
        req.setAttribute("PROJECT_LIST", projects);
        req.setAttribute("TASKS_LIST", tasks);
        req.setAttribute("EMPLOYEE_IN_TASKS_LIST", employeeOfTask);
        req.setAttribute("PROJ_LIST", projectsOfTask);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for get data from form.
     *
     * @param req servlet request
     * @param taskId task id
     * @return task
     * @throws SQLException if a database access error occurs
     */
    private Task getDataFromForm(HttpServletRequest req, Integer taskId) {
        String status = req.getParameter("status");
        String title = Util.htmlSpecialChars(req.getParameter("title"));
        long workTime = Long.parseLong(req.getParameter("workTime"));
        LocalDate beginDate = LocalDate.parse(req.getParameter("beginDate"));
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));
        Integer projectId;
        Integer employeeId;
        try {
            projectId = Integer.parseInt(req.getParameter("projectId"));
        }
        catch (NumberFormatException e) {
            projectId = null;
        }
        try {
            employeeId = Integer.parseInt(req.getParameter("employeeId"));
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


