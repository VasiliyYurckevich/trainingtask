package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Команда отображающая список задач
 *
 * @author Q-YVV
 */
public class TaskListCommand implements Command {

    /**
     * Переменная доступа к методам работы с сущностями Task.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Переменная доступа к методам работы с сущностями Project.
     */
    private final ProjectService projectService = new ProjectService();

    /**
     * Конструктор.
     */
    public TaskListCommand() {
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession();
        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("IS_ZERO_PROJECTS", projectService.findAll().isEmpty());
        req.setAttribute("TASKS_LIST", TaskView.convertTasksList(taskService.findAll()) );

        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
