package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.command.CommandWithValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.ProjectPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.TaskPageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.TaskValidation;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     *
     * @param validationService сервис валидации
     * @param pageDataService сервис взаимодействия сущностей модели и данных со страниц
     */
    public SaveTaskCommand(ValidationService validationService, PageDataService<Task> pageDataService) {
        super(validationService, pageDataService);

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
        pageDataService.setValidatedDataToPage(req, paramsMap, errorsMap);
        req.setAttribute(PROJECT_ID, Integer.parseInt(paramsMap.get(PROJECT_ID)));
        req.getRequestDispatcher(EDIT_TASK_FORM_JSP).forward(req, resp);
    }
}
