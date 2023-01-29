package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProjectCommand implements Command {

    /**
     * Сервис для работы с {@link Project}.
     */
    private final ProjectService projectService = new ProjectService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        projectService.delete(Integer.valueOf(req.getParameter("projectId")));
        resp.sendRedirect("projects");
    }
}
