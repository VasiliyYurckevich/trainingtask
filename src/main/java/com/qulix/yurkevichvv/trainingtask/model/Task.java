package com.qulix.yurkevichvv.trainingtask.model;

import java.time.LocalDate;

import static com.qulix.yurkevichvv.trainingtask.util.Util.htmlSpecialChars;

/**
 * Class Task represents a task.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Task {
    // Fields
    protected int taskId;
    protected String status;
    protected String title;
    protected long workTime;
    protected LocalDate beginDate;
    protected LocalDate endDate;
    protected Integer projectId;
    protected Integer employeeId;

    // Constructors
    public Task() {
    }

    public Task(String status, String title, long workTime, LocalDate beginDate, LocalDate endDate, Integer projectId, Integer employeeId) {
        this.status = status;
        this.title = htmlSpecialChars(title);
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    public Task(int taskId, String status, String title, long workTime, LocalDate beginDate, LocalDate endDate, Integer projectId, Integer employeeId) {
        this.taskId = taskId;
        this.status = status;
        this.title = htmlSpecialChars(title);
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }
    //setters and getters
    public int getId() {
        return taskId;
    }

    public void setId(int id) {
        this.taskId = id;
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

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employee_id) {
        this.employeeId = employee_id;
    }
    // Method toString()
    @Override
    public String toString() {
        return "Task " +
                "taskId=" + taskId +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", workTime=" + workTime +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", project_id=" + projectId +
                ", employee_id=" + employeeId;
    }

}