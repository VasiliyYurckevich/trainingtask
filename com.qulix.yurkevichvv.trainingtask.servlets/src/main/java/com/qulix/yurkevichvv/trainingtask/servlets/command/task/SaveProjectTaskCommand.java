package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.TaskValidation;

/** Сохраняет изменения {@link Task} в списке задач {@link ProjectTemporaryData}.
*
* @author Q-YVV
*/
public class SaveProjectTaskCommand extends CommandWithValidation<Task> {

    /**
     * Порядковый номер задачи в списке задач проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Данные проекта({@link ProjectTemporaryData}).
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Сервис для работы с данными проекта при передаче между страницей и сервером.
     */
    private final PageDataService<ProjectTemporaryData> projectPageDataService = new ProjectPageDataService();

    /**
     * Переменная доступа к методам работы с задачами проекта.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     */
    public SaveProjectTaskCommand() {
        super(new TaskValidation(), new TaskPageDataService());
    }

    @Override
    protected void successesAction(HttpServletRequest request) {

        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute(PROJECT_TEMPORARY_DATA);

        Integer taskIndex;
        Task task;
        if (request.getParameter(TASK_INDEX).isBlank()) {
            taskIndex = null;
            task = new Task();
        }
        else {
            taskIndex = Integer.valueOf(request.getParameter(TASK_INDEX));
            task = projectTemporaryData.getTasksList().get(taskIndex);
        }

        pageDataService.setOutputDataToEntity(paramsMap, task);
        saveTaskInProjectData(task, projectTemporaryData, taskIndex);
    }

    @Override
    protected void redirectAfterSuccessesAction(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        ProjectTemporaryData projectTemporaryData =
            (ProjectTemporaryData) request.getSession().getAttribute(PROJECT_TEMPORARY_DATA);

        projectPageDataService.setDataToPage(request, projectTemporaryData);
        request.getRequestDispatcher("/edit-project-form.jsp").forward(request, response);
    }

    @Override
    protected void failedAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(request, paramsMap, errorsMap);
        request.setAttribute(TASK_INDEX, request.getParameter(TASK_INDEX));
        request.getRequestDispatcher("/edit-task-in-project.jsp").forward(request, response);
    }

    /**
     * Сохраняет новую задачу в данные проекта либо обновляет уже существующую.
     *
     * @param task задача
     * @param project временные данные проекта
     * @param taskIndex индекс задачи в списке задач проекта
     */
    private void saveTaskInProjectData(Task task, ProjectTemporaryData project, Integer taskIndex) {
        if (Optional.ofNullable(taskIndex).isPresent()) {
            projectTemporaryService.updateTask(project, taskIndex, task);
            return;
        }
        projectTemporaryService.addTask(project, task);
    }
}
