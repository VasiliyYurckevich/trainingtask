package com.qulix.yurkevichvv.trainingtask.controller;

import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOTask;
import com.qulix.yurkevichvv.trainingtask.model.Project;
import com.qulix.yurkevichvv.trainingtask.model.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            String action = req.getParameter("action");

            if (action == null) {
                action = "/list";
            }

            switch (action) {
                case "/list":
                    listTasks(req, resp);
                    break;
                case "/add":
                    addTask(req, resp);
                    break;
                case "/edit":
                    editTaskForm(req, resp);
                    break;
                case "/update":
                    updateTask(req, resp);
                    break;
                case "/delete":
                    deleteTask(req, resp);
                    break;
                case "/new":
                    newTaskForm(req, resp);
                    break;
                default:
                    listTasks(req, resp);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void editTaskForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer theTaskId = Integer.valueOf(req.getParameter("taskId"));
        Tasks existingProject = tasksInterface.getById(theTaskId);
        req.setAttribute("taskId",existingProject.getId());
        req.setAttribute("flag", existingProject.getFlag());
        req.setAttribute("title", existingProject.getTitle());
        req.setAttribute("work_time", existingProject.getWorkTime());
        req.setAttribute("begin_date", existingProject.getBeginDate());
        req.setAttribute("end_date", existingProject.getEndDate());
        req.setAttribute("project_id", existingProject.getProject_id());
        req.setAttribute("employee_id", existingProject.getEmployee_id());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-form.jsp");
        existingProject.setId(theTaskId);
        req.setAttribute("project", existingProject);
        dispatcher.forward(req, resp);
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String flag= req.getParameter("flag");
        Integer work_time = Integer.valueOf(req.getParameter("work_time"));
        LocalDate begin_date = LocalDate.parse(req.getParameter("begin_date"));
        LocalDate end_date = LocalDate.parse(req.getParameter("end_date"));
        Integer projectId = Integer.valueOf(req.getParameter("project_id"));
        Integer employeeId = Integer.valueOf(req.getParameter("employee_id"));
        Tasks task = new Tasks( taskId, flag,title, work_time, begin_date,end_date,projectId,employeeId);
        tasksInterface.update(task);
        listTasks(req, resp);

    }

    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/task-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theTaskId = req.getParameter("taskId");
        tasksInterface.delete(Integer.valueOf(theTaskId));
        listTasks(req, resp);
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String flag = req.getParameter("flag");
        String title = req.getParameter("title");
        int workTime = Integer.parseInt(req.getParameter("work_time"));
        LocalDate beginDate = LocalDate.parse(req.getParameter("begin_date"));
        LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));
        int projectId = Integer.parseInt(req.getParameter("project_id"));
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));

        Tasks task = new Tasks( flag, title, workTime, beginDate, endDate, projectId,  employeeId);
        tasksInterface.add(task);

        listTasks(req, resp);
    }

    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Tasks> tasks =  tasksInterface.getAll();
        req.setAttribute("TASKS_LIST", tasks);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/tasks.jsp");
        dispatcher.forward(req, resp);

    }
}