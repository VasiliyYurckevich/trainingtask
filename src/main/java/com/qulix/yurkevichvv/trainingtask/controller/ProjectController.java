package com.qulix.yurkevichvv.controller;


import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOProject;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOTask;
import com.qulix.yurkevichvv.trainingtask.model.Project;
import com.qulix.yurkevichvv.trainingtask.model.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(displayName = "ProjectsServlet", urlPatterns = {"/projects"})
public class ProjectController extends HttpServlet {


    private static final long serialVersionUID = 1424266234L;
    private DAOInterface<Project> projectInterface;

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

            String theCommand = req.getParameter("command");

            if (theCommand == null) {
                theCommand = "/list";
            }

            switch (theCommand) {
                case "/list":
                    listProjects(req, resp);
                    break;
                case "/add":
                    addProject(req, resp);
                    break;
                case "/load":
                    loadProject(req, resp);
                    break;
                case "/update":
                    updateProject(req, resp);
                    break;
                case "/delete":
                    deleteProject(req, resp);
                    break;
                case "/addTask":
                    addTask(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
                case "/deleteTask":
                    deleteTask(req, resp);
                    break;
                default:
                    listProjects(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String theProjectId = req.getParameter("projectId");
        Project theProject = projectInterface.getById(Integer.parseInt(theProjectId));
        req.setAttribute("THE_ID", theProject.getId());
        req.setAttribute("THE_TITLE", theProject.getTitle());
        req.setAttribute("THE_DISCRIPTION", theProject.getDiscription());
        List<Tasks> projectTasks = new DAOTask().getTaskInProject(theProject.getId());
        req.setAttribute("PROJECT_TASKS", projectTasks);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/update-project-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        projectInterface.delete(theProjectId);
        listProjects(req, resp);
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) {
        // ??????????????????????????????
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
        String title = req.getParameter("title");
        String discription = req.getParameter("discription");

        Project theProject = new Project( title, discription);

        projectInterface.update(theProject);

        listProjects(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
//???????????????????????????????????????????????????????????????

        // new DAOTask().delete();

        loadProject(req, resp);
    }

    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
        String title = req.getParameter("title");
        String discription = req.getParameter("discription");

        Project theProject = new Project( title, discription);

        try {
            projectInterface.add(theProject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listProjects(req, resp);
    }
}