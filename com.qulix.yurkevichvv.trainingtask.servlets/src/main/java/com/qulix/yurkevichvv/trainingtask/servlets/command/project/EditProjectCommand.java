package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProjectCommand implements Command {

    private final ProjectPageDataService projectPageDataService = new ProjectPageDataService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        ProjectTemporaryData projectTemporaryData = projectPageDataService.getEntity(req);
        projectPageDataService.setDataToPage(req,projectTemporaryData);
        req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
    }
}
