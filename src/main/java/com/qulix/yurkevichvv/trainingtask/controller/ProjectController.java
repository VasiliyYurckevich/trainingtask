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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProjectController extends HttpServlet {


    private static final long serialVersionUID = 1424266234L;
    private DAOInterface<Project> projectInterface;

    private DAOTask tasksInterface;
    public static final Logger logger = Logger.getLogger(ProjectController.class.getName());



    @Override
    public void init() throws ServletException {
        super.init();

        try {
            projectInterface = new DAOProject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String action = req.getParameter("action");

            if (action == null) {
                action = "/list";
            }

            switch (action) {
                case "/list":
                    listProjects(req, resp);
                    break;
                case "/add":
                    addProject(req, resp);
                    break;
                case "/update":
                    updateProject(req, resp);
                    break;
                case "/delete":
                    deleteProject(req, resp);
                    break;
                case "/addTask":
                    newTaskForm(req, resp);
                    break;
                case "/updateTask":
                    newTaskForm(req, resp);
                    break;
                case  "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
                default:
                    listProjects(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        Project existingProject = projectInterface.getById(theProjectId);
        req.setAttribute("projectId",existingProject.getId());
        req.setAttribute("title", existingProject.getTitle());
        req.setAttribute("discription", existingProject.getDiscription());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        existingProject.setId(theProjectId);
        List<Tasks> projectTasks = new DAOTask().getTaskInProject(existingProject.getId());
        List<Employee> employeeOfTask = new ArrayList<>();
        for (Tasks t:projectTasks){
            Employee employee = new DAOEmployee().getById(t.getEmployee_id());
            employeeOfTask.add(employee);
        }
        System.out.println(employeeOfTask.size());
        System.out.println(projectTasks.size());
        req.setAttribute("project", existingProject);
        req.setAttribute("TASKS_LIST", projectTasks);
        req.setAttribute("EMP_LIST", employeeOfTask);
        dispatcher.forward(req, resp);
    }


    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        try {
            Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
            projectInterface.delete(theProjectId);
            listProjects(req, resp);
            logger.info("Project with id "+theProjectId+"delete");
        }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);
        }
    }

    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer thisProjectId = Integer.valueOf(req.getParameter("projectId"));
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute("thisProjectId",thisProjectId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-project-form.jsp");
        dispatcher.forward(req, resp);

    }

    private void listProjects(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Project> projects =  projectInterface.getAll();
        req.setAttribute("PROJECT_LIST", projects);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        try{
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            String title = req.getParameter("title");
            String discription = req.getParameter("discription");
            Project theProject = new Project( projectId, title, discription);
            projectInterface.update(theProject);
            listProjects(req, resp);
            logger.info("Project with id "+projectId+" update");
        }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);
        }
    }



    private void addProject(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            String title = req.getParameter("title");
            String discription = req.getParameter("discription");
            Project theProject = new Project( title, discription);
            projectInterface.add(theProject);
            listProjects(req, resp);
            logger.info("New project create");
        }catch ( SQLException| ServletException | IOException ex){
            logger.log(Level.SEVERE, "Error message", ex);
        }

    }
}