package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.command.task.TaskCommandRegister;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Задача".
 *
 * @author Q-YVV
 * @see Task
 */
public class TaskController extends Controller {

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Command command = TaskCommandRegister.getCommand(request.getParameter("action"));
            command.execute(request, response);
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in Project Controller", e);
            throw e;
        }
    }
}
