package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditTaskCommand implements Command {

    private final TaskPageDataService taskPageDataService = new TaskPageDataService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        taskPageDataService.setDataToPage(req, taskPageDataService.getEntity(req));
        req.getRequestDispatcher("/edit-task-form.jsp").forward(req, resp);
    }
}
