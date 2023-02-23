package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность "Задачи".
 *
 * @author Q-YVV
 */
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
    private Project project = new Project();

    /**
     * Идентификатор сотрудника.
     */
    private Employee employee = new Employee();

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return String.format("Task { id= '%s', status='%s', title= '%s', workTime= '%s'," +
                " beginDate= '%s', endDate= '%s', project= '%s', employee= '%s'}",
                id, status, title, workTime, beginDate, endDate, project, employee);
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

        if (!Objects.equals(id, task.id)) {
            return false;
        }
        if (status != task.status) {
            return false;
        }
        if (!Objects.equals(title, task.title)) {
            return false;
        }
        if (!Objects.equals(project.getId(), task.project.getId())) {
            return false;
        }
        return Objects.equals(employee.getId(), task.employee.getId());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 21 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        return result;
    }
}
