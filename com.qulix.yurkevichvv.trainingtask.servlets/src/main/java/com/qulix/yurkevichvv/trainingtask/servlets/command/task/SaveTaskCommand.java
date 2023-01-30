package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.TaskValidation;

/**
 * Сохраняет изменения {@link Task} в базе данных.
 *
 * @author Q-YVV
 */
public class SaveTaskCommand extends CommandWithValidation<Task> {

    /**
     * Страницы списка задач.
     */
    private static final String TASKS = "tasks";

    /**
     * Обозначение ID проекта задачи.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * JSP редактирования задачи.
     */
    private static final String EDIT_TASK_FORM_JSP = "/edit-task-form.jsp";

    /**
     * Переменная для работы с бизнес-логикой Task.
     */
    private final TaskService taskService = new TaskService();

    /**
     * Конструктор.
     */
    public SaveTaskCommand() {
        super(new TaskValidation(), new TaskPageDataService());

    }

    @Override
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = pageDataService.getEntity(req);

        pageDataService.setOutputDataToEntity(paramsMap, task);
        taskService.save(task);

        resp.sendRedirect(TASKS);
    }

    @Override
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        pageDataService.setFailedDataToPage(req, paramsMap, errorsMap);
        req.setAttribute(PROJECT_ID, Integer.parseInt(paramsMap.get(PROJECT_ID)));
        req.getRequestDispatcher(EDIT_TASK_FORM_JSP).forward(req, resp);
    }
}
