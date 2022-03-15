package com.qulix.yurkevichvv.trainingtask.controller;


import com.qulix.yurkevichvv.trainingtask.DAO.DAOInterface;
import com.qulix.yurkevichvv.trainingtask.DAO.DAOProject;
import com.qulix.yurkevichvv.trainingtask.model.Project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


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
                    addTask(req, resp);
                    break;
                case  "/edit":
                    editProjectForm(req, resp);
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

    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        Project existingProject = projectInterface.getById(theProjectId);
        req.setAttribute("projectId",existingProject.getId());
        req.setAttribute("title", existingProject.getTitle());
        req.setAttribute("discription", existingProject.getDiscription());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        existingProject.setId(theProjectId);
        req.setAttribute("project", existingProject);
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
        int projectId = Integer.parseInt(req.getParameter("projectId"));
        String title = req.getParameter("title");
        String discription = req.getParameter("discription");
        Project theProject = new Project( projectId, title, discription);
        projectInterface.update(theProject);
        listProjects(req, resp);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
//???????????????????????????????????????????????????????????????

        // new DAOTask().delete();

        editProjectForm(req, resp);
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