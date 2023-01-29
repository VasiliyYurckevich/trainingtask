package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.EmployeeDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.ProjectDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.StatusDropDownItemConverter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
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
    public void setOutputDataToEntity(Map<String, String> paramsMap, Task task) {
        task.setStatus(Status.getStatusById(Integer.parseInt(paramsMap.get(STATUS))));
        task.setTitle(paramsMap.get(TITLE));
        task.setWorkTime(Integer.valueOf(paramsMap.get(WORK_TIME)));
        task.setBeginDate(LocalDate.parse(paramsMap.get(BEGIN_DATE)));
        task.setEndDate(LocalDate.parse(paramsMap.get(END_DATE)));
        if (paramsMap.get(PROJECT_ID) != null ){
            task.setProjectId(Integer.valueOf(paramsMap.get(PROJECT_ID)));
        }
        else {
            task.setProjectId(null);
        }
        if (!paramsMap.get(EMPLOYEE_ID).isEmpty()) {
            task.setEmployeeId(Integer.valueOf(paramsMap.get(EMPLOYEE_ID)));
        }
        else {
            task.setEmployeeId(null);
        }

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
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(TASK_ID, req.getParameter(TASK_ID));
        paramsMap.put(STATUS, req.getParameter(STATUS));
        paramsMap.put(TITLE , req.getParameter(TITLE).trim());
        paramsMap.put(WORK_TIME, req.getParameter(WORK_TIME).trim());
        paramsMap.put(BEGIN_DATE, req.getParameter(BEGIN_DATE).trim());
        paramsMap.put(END_DATE, req.getParameter(END_DATE).trim());
        paramsMap.put(PROJECT_ID, req.getParameter(PROJECT_ID));
        paramsMap.put(EMPLOYEE_ID, req.getParameter(EMPLOYEE_ID));
        paramsMap.forEach((k, v) -> System.out.println(k+" : "+ v));
        return paramsMap;    }

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
