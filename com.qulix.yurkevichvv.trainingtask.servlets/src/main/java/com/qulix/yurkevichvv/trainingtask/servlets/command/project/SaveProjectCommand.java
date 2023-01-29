package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveProjectCommand extends CommandWithValidation<ProjectTemporaryData> {

    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     *
     * @param validationService сервис валидации
     * @param pageDataService   сервис взаимодействия сущностей модели и данных со страниц
     */
    public SaveProjectCommand(ValidationService validationService, PageDataService<ProjectTemporaryData> pageDataService) {
        super(validationService, pageDataService);
    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProjectTemporaryData projectTemporaryData =
                (ProjectTemporaryData) req.getSession().getAttribute("projectTemporaryData");

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        projectTemporaryService.save(projectTemporaryData);

        resp.sendRedirect("projects");
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
        req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
    }
}
