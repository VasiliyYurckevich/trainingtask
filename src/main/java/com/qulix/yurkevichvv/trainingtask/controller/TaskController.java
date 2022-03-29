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


public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getLogger(TaskController.class.getName());
    private DAOInterface<Tasks> tasksInterface;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            tasksInterface = new DAOTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter("taskId");
        Tasks existingTask = tasksInterface.getById(Integer.valueOf(theTaskId));
        req.setAttribute("taskId",existingTask.getId());
        req.setAttribute("flag", existingTask.getFlag());
        req.setAttribute("title",existingTask.getTitle());
        req.setAttribute("work_time", existingTask.getWorkTime());
        req.setAttribute("begin_date",existingTask.getBeginDate());
        req.setAttribute("end_date", existingTask.getEndDate());
        req.setAttribute("project_id",existingTask.getProject_id());
        req.setAttribute("employee_id", existingTask.getEmployee_id());
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        req.setAttribute("task", existingTask);
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);

    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        try{
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            String flag = req.getParameter("flag");
            String title = req.getParameter("title");
            int workTime = Integer.parseInt(req.getParameter("work_time"));
            LocalDate beginDate = LocalDate.parse(req.getParameter("begin_date"));
            LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            Integer employeeId = null;
            try {
                 employeeId = Integer.parseInt(req.getParameter("employee_id"));
            }catch (NumberFormatException e){
            }
            if (endDate.isAfter(beginDate) || endDate.isEqual(beginDate)){
                Tasks task = new Tasks( taskId,flag, title, workTime, beginDate, endDate, projectId,  employeeId);
                tasksInterface.update(task);
                listTasks(req,resp);
                logger.info("New task created");}
            else{
                req.setAttribute("taskId", taskId);
                editTaskForm(req,resp);
            }
        }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);
        }
    }

    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        try {
            String theTaskId = req.getParameter("taskId");
            tasksInterface.delete(Integer.valueOf(theTaskId));
            listTasks(req, resp);
            logger.info("Task with id "+theTaskId+" delete");
        }catch ( SQLException| ServletException | IOException ex){
           logger.log(Level.SEVERE, "Error message", ex);
        }
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
       try {
            String flag = req.getParameter("flag");
            String title = req.getParameter("title");
            int workTime = Integer.parseInt(req.getParameter("work_time"));
            LocalDate beginDate = LocalDate.parse(req.getParameter("begin_date"));
            LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            Integer employeeId = null;
            try {
                employeeId = Integer.parseInt(req.getParameter("employee_id"));
            }catch (NumberFormatException e){
            }
            Tasks task = new Tasks( flag, title, workTime, beginDate, endDate, projectId,  employeeId);
            tasksInterface.add(task);
            listTasks(req,resp);
            logger.info("New task created");

       }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message :", ex);
       }
    }

    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Tasks> tasks =  tasksInterface.getAll();
        List<Employee> employeeOfTask = new ArrayList<>();
        List<Project> projectsOfTask = new ArrayList<>();
        for (Tasks t: tasks){
            Employee employee = new DAOEmployee().getById(t.getEmployee_id());
            employeeOfTask.add(employee);
            Project project = new DAOProject().getById(t.getProject_id());
            projectsOfTask.add(project);
        }
        req.setAttribute("TASKS_LIST", tasks);
        req.setAttribute("EMP_LIST", employeeOfTask);
        req.setAttribute("PROJ_LIST", projectsOfTask);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);
    }
}