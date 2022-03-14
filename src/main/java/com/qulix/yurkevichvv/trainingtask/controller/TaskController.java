package com.qulix.yurkevichvv.trainingtask.controller;

import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOTask;
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
                case "/load":
                    loadTask(req, resp);
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

    private void loadTask(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int taskId = Integer.parseInt(req.getParameter("id"));
        Tasks existingTask = tasksInterface.getById(taskId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/problem-form.jsp");
        req.setAttribute("task", existingTask);
        dispatcher.forward(req, resp);

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