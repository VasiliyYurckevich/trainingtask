package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;

/**
 * Открывает форму редактирования {@link Project}.
 *
 * @author Q-YVV
 */
public class EditProjectCommand implements Command {

    /**
     * Сервис для взаимодействия данных {@link ProjectTemporaryData} и их визуализации на странице.
     */
    private final ProjectPageDataService projectPageDataService = new ProjectPageDataService();

    /**
     * Конструктор.
     */
    public EditProjectCommand() {
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        projectPageDataService.setDataToPage(req, projectPageDataService.getEntity(req));
        req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
    }
}
