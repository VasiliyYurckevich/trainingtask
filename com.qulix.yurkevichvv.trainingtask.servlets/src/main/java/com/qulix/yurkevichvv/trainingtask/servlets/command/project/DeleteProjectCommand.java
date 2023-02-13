package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

/**
 * Удаляет {@link Project} из базы данных.
 *
 * @author Q-YVV
 */
public class DeleteProjectCommand implements Command {

    /**
     * Сервис для работы с {@link Project}.
     */
    private final ProjectService projectService = new ProjectService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        projectService.delete(Integer.valueOf(req.getParameter("projectId")));
        generateToken(req);
        resp.sendRedirect("projects");
    }
}
