package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;

/**
 * Открывает форму редактирования {@link Task}.
 *
 * @author Q-YVV
 */
public class EditTaskCommand implements Command {

    /**
     * Сервис для взаимодействия данных {@link Task} и их визуализации на странице.
     */
    private final TaskPageDataService taskPageDataService = new TaskPageDataService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        taskPageDataService.setDataToPage(req, taskPageDataService.getEntity(req));
        req.getRequestDispatcher("/edit-task-form.jsp").forward(req, resp);
    }
}
