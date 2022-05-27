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

    private static final String ADD_PROJECT_FORM_JSP = "/add-project-form.jsp";
    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    private static final String PROJECT_ID = "projectId";

    private static final String TITLE_OF_PROJECT = "titleProject";

    private static final String DESCRIPTION = "description";

    private static final String ACTION = "action";

    private static final String NUMBER_IN_LIST = "numberInList";

    private static final String DELETED_LIST = "DELETED_LIST";

    private static final String TASKS_LIST = "TASKS_LIST";

    private static final String EMPLOYEE_IN_TASKS_LIST = "EMPLOYEE_IN_TASKS_LIST";

    private static final String THIS_PROJECT_ID = "thisProjectId";

    private static final String LIST = "/list";


    private DAOInterface<Project> projectInterface;

    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());


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
        setParametersAboutProjectEditing(servletContext, deleteTaskInProject, tasksListInProject, employeeListInProject);
        servletContext.setAttribute(NUMBER_IN_LIST, numberInList);
        editProjectForm(req, resp);
    }

    private static void setParametersAboutProjectEditing(ServletContext servletContext,
        List<Integer> deleteTaskInProject, List<Task> tasksListInProject, List<Employee> employeeListInProject) {
        servletContext.setAttribute(TASKS_LIST, tasksListInProject);
        servletContext.setAttribute(EMPLOYEE_IN_TASKS_LIST, employeeListInProject);
        servletContext.setAttribute(DELETED_LIST, deleteTaskInProject);

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
        Utils.setTaskDataInJsp(req, existingTask);
        servletcontext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        Utils.setDataOfDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/edit-task-in-project.jsp");
        servletcontext.setAttribute("task", existingTask);
        dispatcher.forward(req, resp);
    }

    /**
     *
     * Method for open update project form.
     */
    private void editProjectForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        ServletContext servletContext = getServletContext();
        Integer thisProjectId = getProjectId(req, servletContext);
        Project existingProject = projectInterface.getById(thisProjectId);
        setDataAboutProject(servletContext, existingProject);
        existingProject.setId(thisProjectId);
        List<Task> tasksListInProject = getTasksInProject(existingProject, servletContext);
        List<Employee> employeeListInProject = getEmployeesInProject(servletContext, tasksListInProject);
        List<Integer> deletedTasks = getDeletedTasks(servletContext);
        servletContext.setAttribute(THIS_PROJECT_ID, thisProjectId);
        servletContext.setAttribute("project", existingProject);
        setParametersAboutProjectEditing(servletContext, deletedTasks, tasksListInProject, employeeListInProject);
        RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);
    }

    private static List<Integer> getDeletedTasks(ServletContext servletContext) {
        List<Integer> deletedTasks = (List<Integer>) servletContext.getAttribute(DELETED_LIST);
        if (deletedTasks == null) {
            deletedTasks = new ArrayList<>();
        }
        return deletedTasks;
    }

    private static List<Employee> getEmployeesInProject(ServletContext servletContext, List<Task> tasksListInProject) throws SQLException {
        List<Employee> employeeListInProject = (List<Employee>) servletContext.getAttribute("EMP_LIST");
        if (employeeListInProject == null) {
            employeeListInProject = new ArrayList<>();
            for (Task t : tasksListInProject) {
                Employee employee = new DAOEmployee().getById(t.getEmployeeId());
                employeeListInProject.add(employee);
            }
        }
        return employeeListInProject;
    }

    private static List<Task> getTasksInProject(Project existingProject, ServletContext servletContext) throws SQLException {
        List<Task> tasksListInProject = (List<Task>) servletContext.getAttribute(TASKS_LIST);
        if (tasksListInProject == null) {
            tasksListInProject = new DAOTask().getTasksInProject(existingProject.getId());
        } 
        return tasksListInProject;
    }

    private static Integer getProjectId(HttpServletRequest req, ServletContext servletContext) {
        Integer thisProjectId;
        try {
            thisProjectId = Integer.valueOf(req.getParameter(PROJECT_ID));
        }
        catch (NumberFormatException e) {
            thisProjectId = (Integer) servletContext.getAttribute(PROJECT_ID);

        }
        return thisProjectId;
    }

    private static void setDataAboutProject(ServletContext servletContext, Project existingProject) {
        servletContext.setAttribute(PROJECT_ID, existingProject.getId());
        servletContext.setAttribute(TITLE_OF_PROJECT, existingProject.getTitle());
        servletContext.setAttribute(DESCRIPTION, existingProject.getDescription());
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
        servletcontext.setAttribute(PROJECT_ID, thisProjectId);
        Utils.setDataOfDropDownList(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/add-task-in-project.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Method for open add project form.
     */
    private void addProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_PROJECT_FORM_JSP);
        dispatcher.forward(req, resp);

    }

    /**
     * Method for open list of projects.
     */
    private void listProjects(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        Utils.setDataOfDropDownList(req);
        removeServletAttributes();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/projects.jsp");
        dispatcher.forward(req, resp);
    }

    private void removeServletAttributes() {
        ServletContext servletContext = getServletContext();
        servletContext.removeAttribute(TASKS_LIST);
        servletContext.removeAttribute(EMPLOYEE_IN_TASKS_LIST);
        servletContext.removeAttribute(DELETED_LIST);
        servletContext.removeAttribute(PROJECT_ID);
        servletContext.removeAttribute(TITLE_OF_PROJECT);
        servletContext.removeAttribute(DESCRIPTION);
        servletContext.removeAttribute(NUMBER_IN_LIST);
    }

    /**
     * Method for update project.
     */
    private void updateProject(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, ServletException, IOException {
        DAOTask taskInterface = new DAOTask();
        ServletContext servletContext = getServletContext();
        Integer projectId = (Integer) servletContext.getAttribute(PROJECT_ID);
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.projectValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            Project theProject = getProject(paramsList);
            theProject.setId(projectId);
            updateTasksFromProjectEditing(taskInterface, servletContext, projectId);
            projectInterface.update(theProject);
            LOGGER.info("Updated project with id " + projectId);
            listProjects(req, resp);
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsList, errorsList);
            servletContext.setAttribute(PROJECT_ID, projectId);
            dispatcher.forward(req, resp);
        }
    }

    private static void updateTasksFromProjectEditing(DAOTask taskInterface, ServletContext servletContext, Integer projectId) throws SQLException {
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
    }

    private static Project getProject(List<String> paramsList) {
        Project theProject = new Project();
        theProject.setTitle(paramsList.get(0));
        theProject.setDescription(paramsList.get(1));
        return theProject;
    }

    private List<String> getDataFromForm(HttpServletRequest req) {
        List<String> paramsList =  new ArrayList<>(Nums.TWO.getValue());
        paramsList.add(req.getParameter(TITLE_OF_PROJECT));
        paramsList.add(req.getParameter(DESCRIPTION));
        return paramsList;
    }

    /**
     * Method for add project.
     * 
     */
    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
        List<String> paramsList = getDataFromForm(req);
        List<String> errorsList = ValidationService.projectValidator(paramsList);
        if (Utils.isBlankList(errorsList)) {
            Project theProject = getProject(paramsList);
            projectInterface.add(theProject);
            listProjects(req, resp);
            LOGGER.info("New project create");
        }
        else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(ADD_PROJECT_FORM_JSP);
            setDataToJspAfterValidation(req, paramsList, errorsList);
            dispatcher.forward(req, resp);
        }
    }
    private void setDataToJspAfterValidation(HttpServletRequest req, List<String> paramsList, List<String> errorsList) {
        req.setAttribute("ERRORS", errorsList);
        req.setAttribute(TITLE_OF_PROJECT, paramsList.get(Nums.ZERO.getValue()).trim());
        req.setAttribute(DESCRIPTION, paramsList.get(Nums.ONE.getValue()).trim());
    }
}
