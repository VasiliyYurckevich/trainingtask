package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.TaskValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveTaskInProjectCommand extends CommandWithValidation<Task> {

    /**
     * Порядковый номер задачи в списке задач проекта.
     */
    private static final String TASK_INDEX = "taskIndex";

    /**
     * Хранит константу для проекта.
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    private final PageDataService<ProjectTemporaryData> projectPageDataService = new ProjectPageDataService();

    /**
     * Переменная доступа к методам работы с задачами проекта.
     */
    private final ProjectTemporaryService projectTemporaryService = new ProjectTemporaryService();

    /**
     * Конструктор.
     */
    public SaveTaskInProjectCommand() {
        super(new TaskValidation(), new TaskPageDataService());

    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        Integer taskIndex;
        Task task;
        if (req.getParameter(TASK_INDEX).isBlank()){
            taskIndex = null;
            task = new Task();
        }
        else {
            taskIndex = Integer.valueOf(req.getParameter(TASK_INDEX));
            task = projectTemporaryData.getTasksList().get(taskIndex);
        }
        pageDataService.setOutputDataToEntity(paramsMap, task);
        projectPageDataService.setDataToPage(req, projectTemporaryData);
        saveTaskInProjectData(task, projectTemporaryData, taskIndex);

        req.getRequestDispatcher("/edit-project-form.jsp").forward(req, resp);
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.failedAction(req, resp);
    }

    /**
     * Сохраняет новую задачу в данные проекта либо обновляет уже существующую.
     *
     * @param task задача
     * @param project временные данные проекта
     * @param taskIndex индекс задачи в списке задач проекта
     */
    private void saveTaskInProjectData(Task task, ProjectTemporaryData project, Integer taskIndex) {
        if (taskIndex != null) {
            task.setId(project.getTasksList().get(taskIndex).getId());
            projectTemporaryService.updateTask(project, taskIndex, task);

        }
        else {
            projectTemporaryService.addTask(project, task);
        }
    }
}
