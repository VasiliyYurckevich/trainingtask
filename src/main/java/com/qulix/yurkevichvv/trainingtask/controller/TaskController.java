package com.qulix.yurkevichvv.trainingtask.controller;

import com.qulix.yurkevichvv.trainingtask.DAO.DAOEmployee;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOProject;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOTask;
import com.qulix.yurkevichvv.trainingtask.model.Employee;
import com.qulix.yurkevichvv.trainingtask.model.Project;
import com.qulix.yurkevichvv.trainingtask.model.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    private DAOInterface<Tasks> tasksInterface;

    /**
     * Initialize TaskController
     */
    @Override
    public void init() throws ServletException {
        super.init();

        try {
            tasksInterface = new DAOTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    addTask(req, resp);// add task
                    break;
                case "/update":
                    updateTask(req, resp);  // update task
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            String action = req.getParameter("action");// get action

            if (action == null) {
                action = "/list";// default action
            }

            switch (action) {
                case "/list":
                    listTasks(req, resp);// list tasks
                    break;
                case "/edit":
                    editTaskForm(req, resp);// open edit task form
                    break;
                case "/delete":
                    deleteTask(req, resp);// delete task
                    break;
                case "/new":
                    newTaskForm(req, resp);// open new task form
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        String theTaskId = req.getParameter("taskId");// get task id
        Tasks existingTask = tasksInterface.getById(Integer.valueOf(theTaskId));// get task by id
        req.setAttribute("taskId",existingTask.getId());// set task data
        req.setAttribute("flag", existingTask.getFlag());
        req.setAttribute("title",existingTask.getTitle());
        req.setAttribute("work_time", existingTask.getWorkTime());
        req.setAttribute("begin_date",existingTask.getBeginDate());
        req.setAttribute("end_date", existingTask.getEndDate());
        req.setAttribute("project_id",existingTask.getProject_id());
        req.setAttribute("employee_id", existingTask.getEmployee_id());
        List<Employee> employees = new DAOEmployee().getAll();// get list of employees
        List<Project> projects = new DAOProject().getAll();// get list of projects
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        req.setAttribute("task", existingTask);// set task data
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
        try{
            int taskId = Integer.parseInt(req.getParameter("taskId"));// get task data
            String flag = req.getParameter("flag");
            String title = req.getParameter("title");
            int workTime = Integer.parseInt(req.getParameter("work_time"));
            LocalDate beginDate = LocalDate.parse(req.getParameter("begin_date"));
            LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            Integer employeeId = null;
            try {
                 employeeId = Integer.parseInt(req.getParameter("employee_id"));// get employee id if it is not null
            }catch (NumberFormatException e){
            }
            Tasks task = new Tasks( taskId,flag, title, workTime, beginDate, endDate, projectId,  employeeId);// create task
            tasksInterface.update(task);// update task in database
            listTasks(req,resp);
            logger.info("Update task with id: " + taskId);
        }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);
        }
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
        try {
            String theTaskId = req.getParameter("taskId");// get task id
            tasksInterface.delete(Integer.valueOf(theTaskId));// delete task from database
            listTasks(req, resp);
            logger.info("Task with id "+theTaskId+" delete");
        }catch ( SQLException| ServletException | IOException ex){
           logger.log(Level.SEVERE, "Error message", ex);
        }
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
       try {
            String flag = req.getParameter("flag");// get task data
            String title = req.getParameter("title");
            int workTime = Integer.parseInt(req.getParameter("work_time"));
            LocalDate beginDate = LocalDate.parse(req.getParameter("begin_date"));
            LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            Integer employeeId = null;
            try {
                employeeId = Integer.parseInt(req.getParameter("employee_id"));// get employee id if it is not null
            }catch (NumberFormatException e){
            }
            Tasks task = new Tasks( flag, title, workTime, beginDate, endDate, projectId,  employeeId);// create task
            tasksInterface.add(task);// add task to database
            listTasks(req,resp);
            logger.info("New task created");

       }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message :", ex);
       }
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
        List<Tasks> tasks =  tasksInterface.getAll();// get all tasks from database
        List<Employee> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Tasks t: tasks){
            Employee employee = new DAOEmployee().getById(t.getEmployee_id());
            employeeOfTask.add(employee);
            Project project = new DAOProject().getById(t.getProject_id());
            projectsOfTask.add(project);
        }
        req.setAttribute("TASKS_LIST", tasks);// set tasks list to request
        req.setAttribute("EMP_LIST", employeeOfTask);// set employee list to request
        req.setAttribute("PROJ_LIST", projectsOfTask);// set project list to request
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);
    }
}