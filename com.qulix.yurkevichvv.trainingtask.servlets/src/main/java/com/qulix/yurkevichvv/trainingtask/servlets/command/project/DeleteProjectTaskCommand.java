package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.IProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProjectTaskCommand extends CommandWithValidation<ProjectTemporaryData> {


    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     *
     * @param validationService сервис валидации
     * @param pageDataService   сервис взаимодействия сущностей модели и данных со страниц
     */
    public DeleteProjectTaskCommand(ValidationService validationService, PageDataService<ProjectTemporaryData> pageDataService) {
        super(validationService, pageDataService);
    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        int taskIndex = Integer.parseInt(req.getParameter("taskIndex"));
        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) req.getSession().getAttribute("projectTemporaryData");

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        pageDataService.setDataToPage(req, projectTemporaryData);

        projectTemporaryService.deleteTask(projectTemporaryData, projectTemporaryData.getTasksList().get(taskIndex));

        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }
}
