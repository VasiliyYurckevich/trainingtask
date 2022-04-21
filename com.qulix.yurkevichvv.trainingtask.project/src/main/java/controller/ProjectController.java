package controller;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspApplicationContext;

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
@SuppressWarnings ("checkstyle:MultipleStringLiterals")
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
                case "/editTaskForm":
                    editTaskInProjectForm(req, resp);
                    break;
                case "/addTaskForm":
                    newTaskInProjectForm(req, resp);
                    break;
                case "/edit":
                    editProjectForm(req, resp);
                    break;
                case "/new":
                    addProjectForm(req, resp);
                    break;
                case "/deleteTaskInProject":
                    deleteTaskInProject(req, resp);
            }
        }
        catch (SQLException e) {
            LOGGER.warning(String.valueOf(e));
        }
    }
    private void deleteTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, SQLException, ServletException {
        ServletContext servletContext = getServletContext();
        String numberInList  = req.getParameter("numberInList");
        List<Integer> deleteTaskInProject = (List<Integer>) servletContext.getAttribute("DELETED_LIST");
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute("TASKS_LIST");
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute("EMP_LIST");
        Integer id = tasksListInProject.get(Integer.parseInt(numberInList)).getId();
        tasksListInProject.remove(Integer.parseInt(numberInList));
        employeeListInProject.remove(Integer.parseInt(numberInList));
        if (id != null) {
            deleteTaskInProject.add(id);
        }
        servletContext.setAttribute("numberInList", numberInList);
        servletContext.setAttribute("TASKS_LIST", tasksListInProject);
        servletContext.setAttribute("EMP_LIST", employeeListInProject);
        servletContext.setAttribute("DELETED_LIST", deleteTaskInProject);
        editProjectForm(req, resp);
    }

    private void editTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException, NumberFormatException {
        ServletContext servletcontext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletcontext.getAttribute("TASKS_LIST");
        Integer thisProjectId = (Integer) servletcontext.getAttribute("thisProjectId");
        DAOTask tasksInterface = new DAOTask();
        String numberInList  = req.getParameter("numberInList");
        servletcontext.setAttribute("numberInList", numberInList);
        Task existingTask = tasksListInProject.get(Integer.parseInt(numberInList));
        servletcontext.setAttribute("thisProjectId", thisProjectId) ;
        req.setAttribute("taskId", existingTask.getId());
        req.setAttribute("status", existingTask.getStatus());
        req.setAttribute("title", Util.htmlSpecialChars(existingTask.getTitle()));
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("thisProjectId", existingTask.getProjectId());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
        List<Employee> employees = new DAOEmployee().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
        servletcontext.setAttribute("task", existingTask);
        servletcontext.setAttribute("EMPLOYEE_LIST", employees);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open update project form.
     *
     * @param req   servlet request
     * @param resp  servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        Integer thisProjectId;
        List<Task> tasksListInProject;
        List<Employee> employeeListInProject;
        try {
            thisProjectId = Integer.valueOf(req.getParameter("projectId"));
        }
        catch (NumberFormatException e) {
            thisProjectId = (Integer) servletContext.getAttribute("projectId");

        }
        Project existingProject = projectInterface.getById(thisProjectId);
        servletContext.setAttribute("projectId", existingProject.getId());
        servletContext.setAttribute("title", existingProject.getTitle());
        servletContext.setAttribute("description", existingProject.getDescription());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        existingProject.setId(thisProjectId);
        tasksListInProject = (List<Task>) servletContext.getAttribute("TASKS_LIST");
        employeeListInProject = (List<Employee>) servletContext.getAttribute("EMP_LIST");
        if (tasksListInProject == null) {
            tasksListInProject = new DAOTask().getTasksInProject(existingProject.getId());
        }
        if (employeeListInProject == null) {
            employeeListInProject = new ArrayList<>();
            for (Task t : tasksListInProject) {
                System.out.println("ferwegrew" + t.getEmployeeId());
                Employee employee = new DAOEmployee().getById(t.getEmployeeId());
                employeeListInProject.add(employee);
            }
        }
        employeeListInProject.stream().forEach(System.out::println);
        List<Integer> deletedTasks = (List<Integer>) servletContext.getAttribute("DELETED_LIST");
        if (deletedTasks == null) {
            deletedTasks = new ArrayList<>();
        }
        servletContext.setAttribute("thisProjectId", thisProjectId);
        servletContext.setAttribute("DELETED_LIST", deletedTasks);
        servletContext.setAttribute("project", existingProject);
        servletContext.setAttribute("TASKS_LIST", tasksListInProject);
        servletContext.setAttribute("EMP_LIST", employeeListInProject);
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
    private void newTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletcontext = getServletContext();
        Integer thisProjectId = (Integer) servletcontext.getAttribute("thisProjectId");
        String numberInList  = req.getParameter("numberInList");
        servletcontext.setAttribute("numberInList", numberInList);
        servletcontext.setAttribute("thisProjectId", thisProjectId);
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute("thisProjectId", thisProjectId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
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
        ServletContext servletContext = getServletContext();
        servletContext.removeAttribute("TASKS_LIST");
        servletContext.removeAttribute("EMP_LIST");
        servletContext.removeAttribute("DELETED_LIST");
        servletContext.removeAttribute("projectId");
        servletContext.removeAttribute("title");
        servletContext.removeAttribute("description");
        servletContext.removeAttribute("numberInList");
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
        DAOTask taskInterface = new DAOTask();
        ServletContext servletContext = getServletContext();
        Integer projectId = (Integer) servletContext.getAttribute("projectId");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Project theProject = new Project(projectId, title, description);
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute("TASKS_LIST");
        List<Integer> deleteTaskIdProject = (List<Integer>) servletContext.getAttribute("DELETED_LIST");
        for (Task task : tasksListInProject) {
            task.setProjectId(projectId);
            if (task.getId() != null) {
                taskInterface.update(task);
            }
            else {
                task.setId(null);
                taskInterface.add(task);
            }
        }
        for (Integer id : deleteTaskIdProject) {
            if (id != null) {
                taskInterface.delete(id);
            }
        }
        projectInterface.update(theProject);
        LOGGER.info("Project with id " + projectId + " update");
        listProjects(req, resp);
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
