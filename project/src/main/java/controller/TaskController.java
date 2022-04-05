package controller;

import dao.DAOEmployee;
import dao.DAOInterface;
import dao.DAOProject;
import dao.DAOTask;
import model.Employee;
import model.Project;
import model.Task;
import utilits.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for tasks
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 12345L;
    public static final Logger logger = Logger.getLogger(TaskController.class.getName());// logger
    private DAOInterface<Task> tasksInterface;

    /**
     * Initialize TaskController
     */
    @Override
    public void init() throws ServletException,NullPointerException {
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
            }
        } catch (SQLException e) {
            logger.warning(String.valueOf(e));
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
        } catch (SQLException e) {
            logger.warning(String.valueOf(e));
        }

    }

    /**
     * Method for open update task form
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter("taskId");
        Task existingTask = tasksInterface.getById(Integer.valueOf(theTaskId));
        req.setAttribute("taskId",existingTask.getId());
        req.setAttribute("flag", existingTask.getStatus());
        req.setAttribute("title",Util.htmlSpecialChars(existingTask.getTitle()));
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate",existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("projectId",existingTask.getProjectId());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        req.setAttribute("task", existingTask);
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);

    }

    /**
     * Method for update task
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        Task task = getDataFromForm(req,taskId);
        tasksInterface.update(task);
        listTasks(req,resp);
        logger.info("Update task with id: " + taskId);

    }

    /**
     * Method for open new task form
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete task
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter("taskId");
        tasksInterface.delete(Integer.valueOf(theTaskId));
        listTasks(req, resp);
        logger.info("Task with id "+theTaskId+" delete");
    }

    /**
     * Method for add new task
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
       Task task = getDataFromForm(req,null);
       tasksInterface.add(task);
       listTasks(req,resp);
       logger.info("New task created");


    }
    /**
     * Method for list tasks
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Task> tasks =  tasksInterface.getAll();
        List<Employee> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Task t: tasks){
            Employee employee = new DAOEmployee().getById(t.getEmployeeId());
            employeeOfTask.add(employee);
            Project project = new DAOProject().getById(t.getProjectId());
            projectsOfTask.add(project);
        }
        req.setAttribute("TASKS_LIST", tasks);
        req.setAttribute("EMP_LIST", employeeOfTask);
        req.setAttribute("PROJ_LIST", projectsOfTask);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);
    }

    private Task getDataFromForm(HttpServletRequest req,Integer taskId){
        String status = req.getParameter("status");
        String title = Util.htmlSpecialChars(req.getParameter("title"));
        long workTime = Long.parseLong(req.getParameter("workTime"));
        LocalDate beginDate = LocalDate.parse(Util.dataValidationFromForm(req.getParameter("beginDate")));
        LocalDate endDate = LocalDate.parse(Util.dataValidationFromForm(req.getParameter("endDate")));
        Integer projectId = null ;
        Integer employeeId = null;
        try {
            projectId = Integer.parseInt(req.getParameter("projectId"));
        }catch (NumberFormatException e){
        }
        try {
            employeeId = Integer.parseInt(req.getParameter("employeeId"));
        }catch (NumberFormatException e){
        }
        Task task ;
        if (taskId == null){
            task = new Task(status, title, workTime, beginDate, endDate, projectId,  employeeId);}
        else {
            task = new Task(taskId, status, title, workTime, beginDate, endDate, projectId,  employeeId);
          }
        return task;
    }
}