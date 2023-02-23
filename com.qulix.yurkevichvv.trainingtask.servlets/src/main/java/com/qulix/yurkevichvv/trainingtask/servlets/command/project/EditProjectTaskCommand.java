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
    protected void successesAction(HttpServletRequest request) {
    }

    @Override
    protected void redirectAfterSuccessesAction(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute("projectTemporaryData");

        pageDataService.setOutputDataToEntity(paramsMap, projectTemporaryData);
        request.setAttribute(TASK_INDEX, request.getParameter(TASK_INDEX));

        taskPageDataService.setDataToPage(request, getTaskInProjectDataByIndex(request, projectTemporaryData));
        request.getRequestDispatcher("/edit-task-in-project.jsp").forward(request, response);
    }

    @Override
    protected void failedAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(request, paramsMap, errorsMap);
        request.getRequestDispatcher("/edit-project-form.jsp").forward(request, response);
    }

    /**
     * Получает {@link Task} из списка задач {@link ProjectTemporaryData} либо создает новую.
     *
     * @param request {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param projectTemporaryData текущий объект {@link ProjectTemporaryData}, содержащий данные проекта
     * @return {@link Task} с данным индексом либо новая задача.
     */
    private Task getTaskInProjectDataByIndex(HttpServletRequest request, ProjectTemporaryData projectTemporaryData) {
        if (!request.getParameter(TASK_INDEX).isBlank()) {
            int taskIndex = Integer.parseInt(request.getParameter(TASK_INDEX));
            return projectTemporaryData.getTasksList().get(taskIndex);
        }

        Task task = new Task();
        task.setProject(projectTemporaryData.getProject());
        return task;
    }
}
