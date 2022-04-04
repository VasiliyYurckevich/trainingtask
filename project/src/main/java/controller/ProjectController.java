package controller;


import dao.DAOEmployee;
import dao.DAOInterface;
import dao.DAOProject;
import dao.DAOTask;
import model.Employee;
import model.Project;
import model.Task;

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

/**
 * Controller for project
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class ProjectController extends HttpServlet {


    private static final long serialVersionUID = 1424266234L;
    private DAOInterface<Project> projectInterface;

    public static final Logger logger = Logger.getLogger(ProjectController.class.getName());//logger


    /**
     * Initialize the Employee servlet.
     *
     * @throws ServletException if an error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init();
        projectInterface = new DAOProject();
    }


    /**
     * Processes requests for HTTP POST methods.
     *
     * @param req  servlet request
     * @param resp servlet response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Processes requests for HTTP GET methods.
     *
     * @param req  servlet request
     * @param resp servlet response
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=utf-8");

        try {

            String action = req.getParameter("action");

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
                    newTaskForm(req, resp);
                    break;
                case "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
                case "/addTask":
                    newTaskForm(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for open update project form
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));
        Project existingProject = projectInterface.getById(theProjectId);
        req.setAttribute("projectId", existingProject.getId());
        req.setAttribute("title", existingProject.getTitle());
        req.setAttribute("description", existingProject.getDescription());
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
     * Method for delete project
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp){
        try {
            Integer theProjectId = Integer.valueOf(req.getParameter("projectId"));//get project id from form
            projectInterface.delete(theProjectId);//delete project
            listProjects(req, resp);
            logger.info("Project with id " + theProjectId + "delete");
        } catch (SQLException | ServletException | IOException ex) {
            logger.log(Level.SEVERE, "Error message", ex);
        }
    }

    /**
     * Method for open add task form in this project
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void newTaskForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Integer thisProjectId = Integer.valueOf(req.getParameter("projectId"));//get project id from form
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute("thisProjectId", thisProjectId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-form.jsp");
        req.setAttribute("EMPLOYEE_LIST", employees);
        req.setAttribute("PROJECT_LIST", projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add project form
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-project-form.jsp");
        dispatcher.forward(req, resp);

    }

    /**
     * Method for open list of projects
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Project> projects = projectInterface.getAll();
        req.setAttribute("PROJECT_LIST", projects);//set data to form
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for update project
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        try {
            int projectId = Integer.parseInt(req.getParameter("projectId"));//get project data from form
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            Project theProject = new Project(projectId, title, description);//create project object
            projectInterface.update(theProject);
            listProjects(req, resp);
            logger.info("Project with id " + projectId + " update");
        } catch (SQLException | ServletException | IOException ex) {
            logger.log(Level.SEVERE, "Error message", ex);
        }
    }

    /**
     * Method for add project
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String title = req.getParameter("title");//get project data from form
            String description = req.getParameter("description");
            Project theProject = new Project(title, description);
            projectInterface.add(theProject);//add project
            listProjects(req, resp);
            logger.info("New project create");
        } catch (SQLException | ServletException | IOException ex) {
            logger.log(Level.SEVERE, "Error message", ex);
        }

    }
}