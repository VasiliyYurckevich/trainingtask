package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ProjectValidation;

/**
 * Сохраняет изменения {@link Employee} в базе данных.
 *
 * @author Q-YVV
 */
public class SaveProjectCommand extends CommandWithValidation<ProjectTemporaryData> {

    /**
     * Сервис для взаимодействия данных {@link ProjectTemporaryData} и их визуализации на странице.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     */
    public SaveProjectCommand() {
        super(new ProjectValidation(), new ProjectPageDataService());
    }

    @Override
    protected void successesAction(HttpServletRequest request, HttpServletResponse response) {
        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute("projectTemporaryData");

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        projectTemporaryService.save(projectTemporaryData);
    }


    @Override
    protected void redirectAfterSuccessesAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("projects");
    }

    @Override
    protected void failedAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(request, paramsMap, errorsMap);
        request.getRequestDispatcher("/edit-project-form.jsp").forward(request, response);
    }
}
