package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.EmployeeDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.ProjectDropDownItemConverter;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.StatusDropDownItemConverter;

/**
 * Отвечает за взаимодействие данных {@link Task} и визуализации на странице.
 *
 * @author Q-YVV
 */
public class TaskPageDataService implements PageDataService<Task> {

    /**
     * ID задачи.
     */
    private static final String TASK_ID = "taskId";

    /**
     * Статус задачи.
     */
    private static final String STATUS = "status";

    /**
     * Название задачи.
     */
    private static final String TITLE = "title";

    /**
     * ID проекта задачи.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Время на выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Дата начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Дата окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    /**
     * ID сотрудника, ответственного за задачу.
     */
    private static final String EMPLOYEE_ID = "employeeId";

    /**
     * Переменная доступа к методам работы с сущностями {@link Task}.
     */
    private final TaskService taskService;

    /**
     * Конструктор.
     */
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
        if (paramsMap.get(PROJECT_ID) != null) {
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
    public void setFailedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
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
        return paramsMap;
    }

    @Override
    public void setDataToPage(HttpServletRequest req, Task entity) {
        setDropDownLists(req);
        req.setAttribute(TASK_ID, entity.getId());
        req.setAttribute(STATUS, entity.getStatus().getId());
        req.setAttribute(TITLE, entity.getTitle());
        req.setAttribute(WORK_TIME, entity.getWorkTime());
        req.setAttribute(BEGIN_DATE, entity.getBeginDate());
        req.setAttribute(END_DATE, entity.getEndDate());
        req.setAttribute(PROJECT_ID, entity.getProjectId());
        req.setAttribute(EMPLOYEE_ID, entity.getEmployeeId());
    }

    @Override
    public Task getEntity(HttpServletRequest req) {
        String id = req.getParameter(TASK_ID);
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
