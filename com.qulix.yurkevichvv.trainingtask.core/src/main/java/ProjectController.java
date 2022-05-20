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

/**
 * Controller for project.
 *
 *<p> {@link ProjectController} using to interact with  Projects in application.</p>
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class ProjectController extends HttpServlet {

    private static final String PROJECT_ID = "projectId";

    private static final String TITLE = "title";

    private static final String DESCRIPTION = "description";

    private static final String ACTION = "action";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String DELETED_LIST = "DELETED_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String PROJECT_LIST = "PROJECT_LIST";

    private static final String THIS_PROJECT_ID = "thisProjectId";

    private static final String EMPLOYEE_LIST = "EMPLOYEE_LIST";

    private static final String LIST = "/list";


    private DAOInterface<Project> projectInterface;

    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());



    /**
     * Initialize the Employee servlet.
     */
    @Override
    public void init() throws ServletException, NullPointerException {
        super.init();
        projectInterface = new DAOProject();
    }

    /**
     * Processes requests for HTTP POST methods.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter(ACTION);

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
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String action = req.getParameter(ACTION);

            if (action == null) {
                action = LIST;
            }

            switch (action) {
                case LIST:
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

    /**
     *  Method for delete  task in project .
     */
    private void deleteTaskInProject(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, SQLException, ServletException {
        ServletContext servletContext = getServletContext();
        String numberInList  = req.getParameter(NUMBER_IN_LIST);
        List<Integer> deleteTaskInProject = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute(EMPLOYEE_IN_TASKS_LIST);
        Integer id = tasksListInProject.get(Integer.parseInt(numberInList)).getId();
        tasksListInProject.remove(Integer.parseInt(numberInList));
        employeeListInProject.remove(Integer.parseInt(numberInList));
        if (id != null) {
            deleteTaskInProject.add(id);
        }
        servletContext.setAttribute(NUMBER_IN_LIST, numberInList);
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        servletContext.setAttribute(DELETED_LIST, deleteTaskInProject);
        editProjectForm(req, resp);
    }

    /**
     *  Method for open update task in project form.
     */
    private void editTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletcontext = getServletContext();
        List<Task> tasksListInProject = (List<Task>) servletcontext.getAttribute(TASKS_LIST);
        Integer thisProjectId = (Integer) servletcontext.getAttribute(THIS_PROJECT_ID);
        String numberInList  = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);
        Task existingTask = tasksListInProject.get(Integer.parseInt(numberInList));
        servletcontext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        req.setAttribute("taskId", existingTask.getId());
        req.setAttribute("status", existingTask.getStatus());
        req.setAttribute(TITLE, existingTask.getTitle());
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute(THIS_PROJECT_ID, existingTask.getProjectId());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
        List<Employee> employees = new DAOEmployee().getAll();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
        servletcontext.setAttribute("task", existingTask);
        servletcontext.setAttribute(EMPLOYEE_LIST, employees);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open update project form.
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        Integer thisProjectId;
        List<Task> tasksListInProject;
        List<Employee> employeeListInProject;
        try {
            thisProjectId = Integer.valueOf(req.getParameter(PROJECT_ID));
        }
        catch (NumberFormatException e) {
            thisProjectId = (Integer) servletContext.getAttribute(PROJECT_ID);

        }
        Project existingProject = projectInterface.getById(thisProjectId);
        servletContext.setAttribute(PROJECT_ID, existingProject.getId());
        servletContext.setAttribute(TITLE, existingProject.getTitle());
        servletContext.setAttribute(DESCRIPTION, existingProject.getDescription());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-project-form.jsp");
        existingProject.setId(thisProjectId);
        tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        employeeListInProject = (List<Employee>) servletContext.getAttribute("EMP_LIST");
        if (tasksListInProject == null) {
            tasksListInProject = new DAOTask().getTasksInProject(existingProject.getId());
        }
        if (employeeListInProject == null) {
            employeeListInProject = new ArrayList<>();
            for (Task t : tasksListInProject) {
                Employee employee = new DAOEmployee().getById(t.getEmployeeId());
                employeeListInProject.add(employee);
            }
        }
        List<Integer> deletedTasks = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        if (deletedTasks == null) {
            deletedTasks = new ArrayList<>();
        }
        servletContext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        servletContext.setAttribute(DELETED_LIST, deletedTasks);
        servletContext.setAttribute("project", existingProject);
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for delete project.
     */
    private void deleteProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Integer theProjectId = Integer.valueOf(req.getParameter(PROJECT_ID));
        projectInterface.delete(theProjectId);
        listProjects(req, resp);
        LOGGER.info("Project with   id " + theProjectId + " deleted");

    }

    /**
     * Method for open add task form in this project.
     */
    private void newTaskInProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletcontext = getServletContext();
        Integer thisProjectId = (Integer) servletcontext.getAttribute(THIS_PROJECT_ID);
        String numberInList  = req.getParameter(NUMBER_IN_LIST);
        servletcontext.setAttribute(NUMBER_IN_LIST, numberInList);
        servletcontext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.setAttribute(THIS_PROJECT_ID, thisProjectId);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
        req.setAttribute(EMPLOYEE_LIST, employees);
        req.setAttribute(PROJECT_LIST, projects);
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add project form.
     */
    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-project-form.jsp");
        dispatcher.forward(req, resp);

    }

    /**
     * Method for open list of projects.
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        List<Project> projects = projectInterface.getAll();
        req.setAttribute(PROJECT_LIST, projects);
        ServletContext servletContext = getServletContext();
        servletContext.removeAttribute(TASKS_LIST);
        servletContext.removeAttribute(EMPLOYEE_IN_TASKS_LIST);
        servletContext.removeAttribute(DELETED_LIST);
        servletContext.removeAttribute(PROJECT_ID);
        servletContext.removeAttribute(TITLE);
        servletContext.removeAttribute(DESCRIPTION);
        servletContext.removeAttribute(NUMBER_IN_LIST);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for update project.
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        DAOTask taskInterface = new DAOTask();
        ServletContext servletContext = getServletContext();
        Integer projectId = (Integer) servletContext.getAttribute(PROJECT_ID);
        String title = req.getParameter(TITLE);
        String description = req.getParameter(DESCRIPTION);
        Project theProject = new Project(projectId, title, description);
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        List<Integer> deleteTaskIdProject = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
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
        LOGGER.info("Updated project with id " + projectId);
        listProjects(req, resp);
    }

    /**
     * Method for add project.
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
        String title = req.getParameter(TITLE);
        String description = req.getParameter(DESCRIPTION);
        Project theProject = new Project(title, description);
        projectInterface.add(theProject);
        listProjects(req, resp);
        LOGGER.info("New project create");
    }
}
