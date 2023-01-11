package com.qulix.yurkevichvv.trainingtask.servlets.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskView {

    /**
     * Идентификатор задачи.
     */
    private Integer id;

    /**
     * Статус задачи.
     */

    private String statusTitle;

    /**
     * Название задачи.
     */
    private String title;

    /**
     * Время на задачу.
     */
    private String workTime;

    /**
     * Дата начала задачи.
     */
    private String beginDate;

    /**
     * Дата окончания задачи.
     */
    private String endDate;

    /**
     * Идентификатор проекта задачи.
     */
    private String projectTitle;

    /**
     * Идентификатор сотрудника задачи.
     */
    private String employeeFullName;

    public TaskView(Task task) {
        this.id = task.getId();
        this.statusTitle = task.getStatus().getStatusTitle();
        this.title = task.getTitle();
        this.workTime = task.getWorkTime().toString();
        this.beginDate = task.getBeginDate().toString();
        this.endDate = task.getEndDate().toString();
        this.projectTitle = (task.getEmployeeId() != null) ?
            new ProjectService().getById(task.getProjectId()).getTitle() : "";
        this.employeeFullName = (task.getEmployeeId() != null) ?
            new EmployeeService().getById(task.getEmployeeId()).getFullName() : "";
    }

    public static List<TaskView> convertTasksList(List<Task> taskList){
        return taskList.stream()
                .map(task -> new TaskView(task))
                .collect(Collectors.toList());
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }
}
