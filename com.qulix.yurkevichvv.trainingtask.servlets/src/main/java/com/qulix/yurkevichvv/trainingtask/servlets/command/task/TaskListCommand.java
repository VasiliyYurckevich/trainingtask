package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskWrapper;

/**
 * Команда отображающая список задач.
 *
 * @author Q-YVV
 */
public class TaskListCommand implements Command {

    /**
     * Переменная доступа к методам работы с сущностями {@link Task}.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Переменная доступа к методам работы с сущностями {@link Project}.
     */
    private final ProjectService projectService = new ProjectService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        //session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);

        req.setAttribute("IS_NO_PROJECTS", projectService.findAll().isEmpty());
        req.setAttribute("TASKS_LIST", TaskWrapper.convertTasksList(taskService.findAll()));

        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
