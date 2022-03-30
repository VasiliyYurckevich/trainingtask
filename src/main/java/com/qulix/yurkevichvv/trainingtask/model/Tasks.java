package com.qulix.yurkevichvv.trainingtask.model;

import java.time.LocalDate;

import static com.qulix.yurkevichvv.trainingtask.util.Util.htmlSpecialChars;

/**
 * Class Tasks represents a task.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Tasks  {
    // Fields
    protected int taskId;
    protected String flag;
    protected String title;
    protected long workTime;
    protected LocalDate beginDate;
    protected LocalDate endDate;
    protected Integer project_id;
    protected Integer employee_id;

    // Constructors
    public Tasks() {
    }

    public Tasks(String flag, String title, long workTime, LocalDate beginDate, LocalDate endDate, Integer project_id, Integer employee_id) {
        this.flag = flag;
        this.title = htmlSpecialChars(title);
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }

    public Tasks(int taskId, String flag, String title, long workTime, LocalDate beginDate, LocalDate endDate, Integer project_id, Integer employee_id) {
        this.taskId = taskId;
        this.flag = flag;
        this.title = htmlSpecialChars(title);
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }
    //setters and getters
    public int getId() {
        return taskId;
    }

    public void setId(int id) {
        this.taskId = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
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

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
    // Method toString()
    @Override
    public String toString() {
        return "Task " +
                "taskId=" + taskId +
                ", flag='" + flag + '\'' +
                ", title='" + title + '\'' +
                ", workTime=" + workTime +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", project_id=" + project_id +
                ", employee_id=" + employee_id;
    }

}