package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ProjectValidation;

/**
 * Удаляет {@link Task} из списка задач {@link ProjectTemporaryData}.
 *
 * @author Q-YVV
 */
public class DeleteProjectTaskCommand extends CommandWithValidation<ProjectTemporaryData> {

    /**
     * Данные проекта в сессии.
     */
    public static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Путь на страницу редактирования проекта.
     */
    private static final String EDIT_PROJECT_FORM_JSP = "/edit-project-form.jsp";

    /**
     * Сервис для работы с {@link ProjectTemporaryData}.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     */
    public DeleteProjectTaskCommand() {
        super(new ProjectValidation(), new ProjectPageDataService());
    }

    @Override
    protected void successesAction(HttpServletRequest request) {
        int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute(PROJECT_TEMPORARY_DATA);

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);


        projectTemporaryService.deleteTask(projectTemporaryData, projectTemporaryData.getTasksList().get(taskIndex));
    }

    @Override
    protected void redirectAfterSuccessesAction(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        pageDataService.setDataToPage(request, projectTemporaryData);
        request.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(request, response);
    }

    @Override
    protected void failedAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(request, paramsMap, errorsMap);
        request.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(request, response);
    }
}
