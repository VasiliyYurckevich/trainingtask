package com.qulix.yurkevichvv.trainingtask.model;

import java.time.LocalDate;

public class Tasks {
    protected int id;
    protected String flag;
    protected String title;
    protected int project_id;
    protected int workTime;
    protected LocalDate beginDate;
    protected LocalDate endDate;
    protected int employee_id;



    public Tasks() {
    }

    public Tasks(String flag, String title, int project_id,int workTime ,LocalDate beginDate, LocalDate endDate, int employee_id) {
        this.flag = flag;
        this.title = title;
        this.project_id = project_id;
        this.beginDate = beginDate;
        this.workTime = workTime;
        this.endDate = endDate;
        this.employee_id = employee_id;
    }

    public Tasks(int id, String flag, String title, int project_id, LocalDate beginDate, LocalDate endDate, int employee_id) {
        this.id = id;
        this.flag = flag;
        this.title = title;
        this.project_id = project_id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.employee_id = employee_id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}