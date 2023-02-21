package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("IS_NO_PROJECTS", projectService.findAll().isEmpty());
        request.setAttribute("TASKS_LIST", taskService.findAll());

        request.getRequestDispatcher("/tasks.jsp").forward(request, response);
    }
}
