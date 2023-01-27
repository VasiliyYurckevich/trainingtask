package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.beans.JavaBean;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность "Задачи".
 *
 * @author Q-YVV
 */
@JavaBean
public class Task implements Entity {

    /**
     * Идентификатор.
     */
    private Integer id;

    /**
     * Статус.
     */

    private Status status = Status.NOTSTARTED;

    /**
     * Название.
     */
    private String title;

    /**
     * Время работы.
     */
    private Integer workTime;

    /**
     * Дата начала.
     */
    private LocalDate beginDate;

    /**
     * Дата окончания.
     */
    private LocalDate endDate;

    /**
     * Идентификатор проекта.
     */
    private Integer projectId;

    /**
     * Идентификатор сотрудника.
     */
    private Integer employeeId;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public void setWorkTime(Integer workTime) {
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
        return String.format("Task { id= '%s', status='%s', title= '%s', workTime= '%s'," +
                " beginDate= '%s', endDate= '%s', projectId= '%s', employeeId= '%s'}",
                id, status, title, workTime, beginDate, endDate, projectId, employeeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, title, workTime, beginDate, endDate);
    }
}
