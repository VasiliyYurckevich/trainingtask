package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
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
    private final TaskService taskService = new TaskService();

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, Task task) {
        task.setId(paramsMap.get(TASK_ID).isEmpty() ? null : Integer.parseInt(paramsMap.get(TASK_ID)));
        task.setStatus(Status.getStatusById(Integer.parseInt(paramsMap.get(STATUS))));
        task.setTitle(paramsMap.get(TITLE));
        task.setWorkTime(Integer.valueOf(paramsMap.get(WORK_TIME)));
        task.setBeginDate(LocalDate.parse(paramsMap.get(BEGIN_DATE)));
        task.setEndDate(LocalDate.parse(paramsMap.get(END_DATE)));
        if (paramsMap.get(PROJECT_ID) != null) {
            task.setProject(new ProjectService().getById(Integer.valueOf(paramsMap.get(PROJECT_ID))));
        }
        else {
            task.setProject(null);
        }
        if (!paramsMap.get(EMPLOYEE_ID).isEmpty()) {
            task.setEmployee(new EmployeeService().getById(Integer.valueOf(paramsMap.get(EMPLOYEE_ID))));
        }
        else {
            task.setEmployee(null);
        }
    }

    @Override
    public void setFailedDataToPage(HttpServletRequest request, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        setDropDownLists(request);
        request.setAttribute("ERRORS", errorsMap);
        request.setAttribute(TASK_ID, paramsMap.get(TASK_ID));
        request.setAttribute(STATUS, paramsMap.get(STATUS));
        request.setAttribute(TITLE, paramsMap.get(TITLE));
        request.setAttribute(WORK_TIME, paramsMap.get(WORK_TIME));
        request.setAttribute(BEGIN_DATE, paramsMap.get(BEGIN_DATE).trim());
        request.setAttribute(END_DATE, paramsMap.get(END_DATE).trim());
        request.setAttribute(EMPLOYEE_ID,
            paramsMap.get(EMPLOYEE_ID).isEmpty() ? "" : Integer.valueOf(paramsMap.get(EMPLOYEE_ID)));
    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>();
        request.getParameterMap().forEach((x, y) -> System.out.println(x + " : " + Arrays.toString(y)));
        paramsMap.put(TASK_ID, request.getParameter(TASK_ID));
        paramsMap.put(STATUS, request.getParameter(STATUS));
        paramsMap.put(TITLE , request.getParameter(TITLE).trim());
        paramsMap.put(WORK_TIME, request.getParameter(WORK_TIME).trim());
        paramsMap.put(BEGIN_DATE, request.getParameter(BEGIN_DATE).trim());
        paramsMap.put(END_DATE, request.getParameter(END_DATE).trim());
        paramsMap.put(PROJECT_ID, request.getParameter(PROJECT_ID));
        paramsMap.put(EMPLOYEE_ID, request.getParameter(EMPLOYEE_ID));

        return paramsMap;
    }

    @Override
    public void setDataToPage(HttpServletRequest request, Task entity) {
        setDropDownLists(request);
        System.out.println(entity);
        request.setAttribute(TASK_ID, entity.getId());
        request.setAttribute(STATUS, entity.getStatus().getId());
        request.setAttribute(TITLE, entity.getTitle());
        request.setAttribute(WORK_TIME, entity.getWorkTime());
        request.setAttribute(BEGIN_DATE, entity.getBeginDate());
        request.setAttribute(END_DATE, entity.getEndDate());
        request.setAttribute(PROJECT_ID, entity.getProject().getId());
        request.setAttribute(EMPLOYEE_ID, entity.getEmployee().getId());
    }

    @Override
    public Task getEntity(HttpServletRequest request) {
        String id = request.getParameter(TASK_ID);
        if (!id.isBlank()) {
            return taskService.getById(Integer.valueOf(id));

        }
        return new Task();
    }

    /**
     * Вносит данные в выпадающие списки.
     *
     * @param request запрос
     */
    public static void setDropDownLists(HttpServletRequest request) {
        request.setAttribute("ProjectList", new ProjectDropDownItemConverter().convertList(new ProjectService().findAll()));
        request.setAttribute("StatusList", new StatusDropDownItemConverter().convertList(List.of(Status.values())));
        request.setAttribute("EmployeeList", new EmployeeDropDownItemConverter().convertList(new EmployeeService().findAll()));
    }
}
