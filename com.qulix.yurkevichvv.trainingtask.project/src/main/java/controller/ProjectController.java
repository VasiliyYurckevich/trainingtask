package controller;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
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
 * Controller for project.
 *
 *<p> {@link ProjectController} using for control Projects in application.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code ProjectController pc = new ProjectController();}
 * {@code pc.doGet(request, response);}
 * {@code pc.doPost(request, response);}
 * {@code pc.addProject(request, response);}
 * {@code pc.updateProject(request, response);}
 * {@code pc.deleteProject(request, response);}
 * {@code pc.newProjectForm(request, response);}
 * {@code pc.editProjectForm(request, response);}
 * {@code pc.listProjects(request, response);}
 * </pre>
 *
 * <h2>Synchronization</h2>
 * <p>
 * This class is not guaranteed to be thread-safe so it should be synchronized externally.
 * </p>
 *
 * <h2>Known bugs</h2>
 * {@link ProjectController} does not handle overflows.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 * @see Project
 * @see DAOProject
 * @see DAOInterface
 * @see DAOEmployee
 * @see DAOTask
 * @see Employee
 * @see Task
 */
public class ProjectController extends HttpServlet {


    private static final long serialVersionUID = 1424266234L;
    private DAOInterface<Project> projectInterface;
    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());


    /**
     * Initialize the Employee servlet.
     *
     * @throws NullPointerException if the servlet cannot be initialized
     * @throws ServletException if an error occurs
     *
     */
    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        projectInterface = new DAOProject();
    }


    /**
     * Processes requests for HTTP POST methods.
     *
     * @param req  servlet request
     * @param resp servlet response
     */
    @SuppressWarnings ("checkstyle:MultipleStringLiterals")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");

        try {
            String action = req.getParameter("action");

            switch (action) {
                case "/add":
                    addProject(req, resp);
                    break;
                case "/update":
                    updateProject(req, resp);
                    break;

            }
        }
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }

    }

    /**
     * Processes requests for HTTP GET methods.
     *
     * @param req  servlet request
     * @param resp servlet response
     */
    @SuppressWarnings ("checkstyle:MultipleStringLiterals")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        try {
            String action =      req.getParameter("action");

            if (action == null) {
                action = "/list";
            }

            switch (action) {
                case "/list":
                    listProjects(req, resp);
                    break;
                case "/delete":
                    deleteProject(req, resp);
                    break;
                case "/updateTask":
                case "/addTask":
                    newTaskForm(req, resp);
                    break;
                case "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
            }
        }
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }
    }

    /**
     * Method for open update project form.
     *
     * @param req   servlet request
     * @param resp  servlet request
     * @throws ServletException if an servlet-specific error occurs
     * @throws IOException     if an I/O error occurs
     */
    @SuppressWarnings ("checkstyle:MultipleStringLiterals")
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        Project existingProject = projectInterface.getById(theProjectId);
        req.setAttribute("projectId", existingProject.getId());
        req.setAttribute("title", Util.htmlSpecialChars(existingProject.getTitle()));
        req.setAttribute("description", Util.htmlSpecialChars(existingProject.getDescription()));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        existingProject.setId(theProjectId);
        List<Task> projectTasks = new DAOTask().getTaskInProject(existingProject.getId());
        List<Employee> employeeOfTask = new ArrayList<>();
        for (Task t : projectTasks) {
            Employee employee = new DAOEmployee().getById(t.getEmployeeId());
            employeeOfTask.add(employee);
        }
        req.setAttribute("project", existingProject);
        req.setAttribute("TASKS_LIST", projectTasks);
        req.setAttribute("EMP_LIST", employeeOfTask);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete project.
     *
     * @param req  servlet request
     * @param resp  servlet response
     * @throws ServletException if an error occurs
     * @throws IOException if an error occurs
     * @throws SQLException if an error occurs
     */
    @SuppressWarnings ("checkstyle:MultipleStringLiterals")
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        projectInterface.delete(theProjectId);
        listProjects(req, resp);
        LOGGER.info("Project with id " + theProjectId + " deleted");

    }

    /**
     * Method for open add task form in this project.
     *
     * @param req  servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException iif an I/O error occurs
     * @throws SQLException if an SQL error occurs
     */
    @SuppressWarnings ("checkstyle:MultipleStringLiterals")
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Integer thisProjectId = Integer.valueOf(req.getParameter("projectId"));
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute("thisProjectId", thisProjectId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add project form.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if an error occurs
     * @throws IOException if an error occurs
     */
    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-project-form.jsp");
        dispatcher.forward(req, resp);

    }

    /**
     * Method for open list of projects.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if servlet error occurs
     * @throws IOException if an error occurs
     * @throws SQLException if an SQL error occurs
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        List<Project> projects = projectInterface.getAll();
        req.setAttribute("PROJECT_LIST", projects);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for update project.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if on servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        int projectId = Integer.parseInt(req.getParameter("projectId"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Project theProject = new Project(projectId, title, description);
        projectInterface.update(theProject);
        listProjects(req, resp);
        LOGGER.info("Project with id " + projectId + " update");
    }

    /**
     * Method for add project.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if on servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Project theProject = new Project(title, description);
        projectInterface.add(theProject);
        listProjects(req, resp);
        LOGGER.info("New project create");
    }
}
