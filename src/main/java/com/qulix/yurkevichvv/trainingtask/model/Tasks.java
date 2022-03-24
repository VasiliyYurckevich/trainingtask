package com.qulix.yurkevichvv.trainingtask.model;

import java.time.LocalDate;

public class Tasks {
    protected int taskId;
    protected String flag;
    protected String title;
    protected int workTime;
    protected LocalDate beginDate;
    protected LocalDate endDate;
    protected int project_id;
    protected Integer employee_id;



    public Tasks() {
    }

    public Tasks(String flag, String title, int workTime, LocalDate beginDate, LocalDate endDate, int project_id, Integer employee_id) {
        this.flag = flag;
        this.title = title;
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }

    public Tasks(int taskId, String flag, String title, int workTime, LocalDate beginDate, LocalDate endDate, int project_id, Integer employee_id) {
        this.taskId = taskId;
        this.flag = flag;
        this.title = title;
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.project_id = project_id;
        this.employee_id = employee_id;
    }

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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
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

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
}