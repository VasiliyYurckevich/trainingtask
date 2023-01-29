package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTaskCommand implements Command {

    /**
     * Переменная доступа к методам работы с сущностями Task.
     */
    private final TaskService taskService = new TaskService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String taskId = req.getParameter("taskId");
        taskService.delete(Integer.parseInt(taskId));
        resp.sendRedirect("tasks");
    }
}
