package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

/**
 * Открывает список существующих в базе данных {@link Project}.
 *
 * @author Q-YVV
 */
public class ProjectsListCommand implements Command {

    /**
     * Сервис для работы с {@link Project}.
     */
    private final ProjectService projectService = new ProjectService();

    /**
     * Конструктор.
     */
    public ProjectsListCommand() {
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        session.getAttributeNames().asIterator().forEachRemaining(session::removeAttribute);
        req.setAttribute("PROJECT_LIST", projectService.findAll());

        req.getRequestDispatcher("projects.jsp").forward(req, resp);
    }
}
