package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.command.employee.EmployeeCommandRegister;

/**
 * Содержит сервлеты для выполнения действий объектов класса "Сотрудник".
 *
 * @author Q-YVV
 */
public class EmployeeController extends Controller {

    /**
     * Логгер для записи событий.
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = EmployeeCommandRegister.getCommand(req.getParameter("action"));
            command.execute(req, resp);
        }
        catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Problem in Employee Controller", e);
            throw e;
        }
    }
}
