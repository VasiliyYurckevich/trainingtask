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
        pageDataService.setFailedDataToPage(req, paramsMap, errorsMap);
        req.getRequestDispatcher(EDIT_PROJECT_FORM_JSP).forward(req, resp);
    }
}
