package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ProjectValidation;

/**
 * Открывает форму редактирования {@link Task} из списка задач {@link ProjectTemporaryData}.
 *
 * @author Q-YVV
 */
public class EditProjectTaskCommand extends CommandWithValidation<ProjectTemporaryData> {

    /**
     * Индекс задачи в списке {@link ProjectTemporaryData}.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Сервис для взаимодействия данных {@link Task} из списка задач {@link ProjectTemporaryData}
     * и их визуализации на странице.
     */
    private final TaskPageDataService taskPageDataService = new TaskPageDataService();

    /**
     * Конструктор.
     */
    public EditProjectTaskCommand() {
        super(new ProjectValidation(), new ProjectPageDataService());
    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) req.getSession().getAttribute("projectTemporaryData");

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        req.setAttribute(TASK_INDEX, req.getParameter(TASK_INDEX));

        taskPageDataService.setDataToPage(req, getTaskInProjectDataByIndex(req, projectTemporaryData));
        req.getRequestDispatcher("/edit-task-in-project.jsp").forward(req, resp);
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(req, paramsMap, errorsMap);
        req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
    }

    /**
     * Получает {@link Task} из списка задач {@link ProjectTemporaryData} либо создает новую.
     *
     * @param req {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param projectTemporaryData текущий объект {@link ProjectTemporaryData}, содержащий данные проекта
     * @return {@link Task} с данным индексом либо новая задача.
     */
    private Task getTaskInProjectDataByIndex(HttpServletRequest req, ProjectTemporaryData projectTemporaryData) {
        if (!req.getParameter(TASK_INDEX).isBlank()) {
            int taskIndex = Integer.parseInt(req.getParameter(TASK_INDEX));
            return projectTemporaryData.getTasksList().get(taskIndex);
        }

        Task task = new Task();
        task.setProjectId(projectTemporaryData.getId());
        return task;
    }
}
