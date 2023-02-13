package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
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
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = TaskCommandRegister.getCommand(req.getParameter("action"));
            command.execute(req, resp);
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in Project Controller", e);
            throw e;
        }
    }
}
