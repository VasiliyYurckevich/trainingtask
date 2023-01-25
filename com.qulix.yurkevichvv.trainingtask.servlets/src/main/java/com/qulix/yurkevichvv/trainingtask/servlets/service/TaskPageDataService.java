package com.qulix.yurkevichvv.trainingtask.servlets.service;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.EmployeeDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.ProjectDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown.StatusDropDownItemConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class TaskPageDataService implements PageDataService<Task> {

    /**
     * Обозначение ID задачи.
     */
    private static final String TASK_ID = "taskId";

    /**
     * Обозначение статуса задачи.
     */
    private static final String STATUS = "status";

    /**
     * Обозначение названия задачи.
     */
    private static final String TITLE = "title";

    /**
     * Обозначение ID проекта задачи.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Обозначение времени на выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Обозначение даты начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Обозначение даты окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    /**
     * Обозначение ID сотрудника, ответственного за задачу.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    private final TaskService taskService;

    public TaskPageDataService() {
        this.taskService = new TaskService();
    }

    
    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, Task entity) {

    }

    @Override
    public void setValidatedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        setDropDownLists(req);

        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(TASK_ID, paramsMap.get(TASK_ID));
        req.setAttribute(STATUS, paramsMap.get(STATUS));
        req.setAttribute(TITLE, paramsMap.get(TITLE));
        req.setAttribute(WORK_TIME, paramsMap.get(WORK_TIME));
        req.setAttribute(BEGIN_DATE, paramsMap.get(BEGIN_DATE).trim());
        req.setAttribute(END_DATE, paramsMap.get(END_DATE).trim());
        req.setAttribute(EMPLOYEE_ID, paramsMap.get(EMPLOYEE_ID).isEmpty() ? "" : Integer.valueOf(paramsMap.get(EMPLOYEE_ID)));

    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest req) {
        return null;
    }

    @Override
    public void setDataToPage(HttpServletRequest req, Task entity) {
        setDropDownLists(req);
        req.setAttribute("taskId", entity.getId());
        req.setAttribute("status", entity.getStatus().getId());
        req.setAttribute("title", entity.getTitle());
        req.setAttribute("workTime", entity.getWorkTime());
        req.setAttribute("beginDate", entity.getBeginDate());
        req.setAttribute("endDate", entity.getEndDate());
        req.setAttribute("projectId", entity.getProjectId());
        req.setAttribute("employeeId", entity.getEmployeeId());
    }

    @Override
    public Task getEntity(HttpServletRequest req) {
        String id = req.getParameter("taskId");
        if (!id.isBlank()) {
            return taskService.getById(Integer.valueOf(id));

        }
        return new Task();
    }

    /**
     * Вносит данные в выпадающие списки.
     *
     * @param req запрос
     */
    public static void setDropDownLists(HttpServletRequest req) {
        req.setAttribute("ProjectList", new ProjectDropDownItemConverter().convertList(new ProjectService().findAll()));
        req.setAttribute("StatusList", new StatusDropDownItemConverter().convertList(List.of(Status.values())));
        req.setAttribute("EmployeeList", new EmployeeDropDownItemConverter().convertList(new EmployeeService().findAll()));
    }
}
