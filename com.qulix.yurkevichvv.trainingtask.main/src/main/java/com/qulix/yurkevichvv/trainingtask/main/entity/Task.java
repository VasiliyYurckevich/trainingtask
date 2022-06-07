package com.qulix.yurkevichvv.trainingtask.main.entity;

import java.time.LocalDate;

/**
 * Реализует сущность задачи в приложении.
 *
 * @author Q-YVV
 */
public class Task {

    private Integer id;

    private String status;

    private String title;

    private int workTime;

    private LocalDate beginDate;

    private LocalDate endDate;

    private Integer projectId;

    private Integer employeeId;


    /**
     * Конструктор класса Задача.
     */
    public Task() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Task " +
            "id=" + id +
            ", status='" + status +
            ", title='" + title +
            ", workTime=" + workTime +
            ", beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", project_id=" + projectId +
            ", employee_id=" + employeeId;
    }
}
