package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

/**
 * Удаление {@link Task} из базы данных.
 *
 * @author Q-YVV
 */
public class DeleteTaskCommand implements Command {

    /**
     * Переменная доступа к методам работы с сущностями {@link Task}.
     */
    private final TaskService taskService = new TaskService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        taskService.delete(Integer.parseInt(request.getParameter("taskId")));
        response.sendRedirect("tasks");
    }
}
