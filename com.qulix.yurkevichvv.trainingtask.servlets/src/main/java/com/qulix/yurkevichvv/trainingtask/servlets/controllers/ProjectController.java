package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.command.project.ProjectCommandRegister;


/**
 * Содержит сервлеты для выполнения действий объектов класса "Проект".
 *
 * @author Q-YVV
 * @see Project
 */
public class ProjectController extends Controller {

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = ProjectCommandRegister.getCommand(req.getParameter("action"));
            command.execute(req, resp);
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in Project Controller", e);
            throw e;
        }
    }
}
