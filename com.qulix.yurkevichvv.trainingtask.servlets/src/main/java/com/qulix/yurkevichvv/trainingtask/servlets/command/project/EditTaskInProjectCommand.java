package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditTaskInProjectCommand extends CommandWithValidation<ProjectTemporaryData> {

    private static final String TASK_INDEX = "taskIndex";
    /**
     *
     */
    private final TaskPageDataService taskPageDataService = new TaskPageDataService();

    /**
     * Конструктор.
     *
     * @param validationService сервис валидации
     * @param pageDataService   сервис взаимодействия сущностей модели и данных со страниц
     */
    public EditTaskInProjectCommand(ValidationService validationService, PageDataService pageDataService) {
        super(validationService, pageDataService);
    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) req.getSession().getAttribute("projectTemporaryData");
        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        taskPageDataService.setDataToPage(req, getTaskInProjectDataByIndex(req, projectTemporaryData));
        req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.failedAction(req, resp);
    }

    private Task getTaskInProjectDataByIndex(HttpServletRequest req, ProjectTemporaryData projectTemporaryData) {

        if (!req.getParameter(TASK_INDEX).isBlank()) {
            int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
            req.setAttribute(TASK_INDEX, taskIndex);
            return projectTemporaryData.getTasksList().get(taskIndex);
        }
        else {
            Task task = new Task();
            task.setProjectId(projectTemporaryData.getId());
            return task;
        }
    }
}
