package com.qulix.yurkevichvv.trainingtask.main.entity;

public enum Status {

    N0BEGIN (1, "Не начата"),

    INTHEPROCESS (2, "В процессе"),

    COMPLETED(3, "Завершена"),

    POSTPONED (4, "Отложена");

    Status(int id, String status) {
        this.id = id;
        this.statusTitle = status;
    }

    private Integer id;
    private String statusTitle;

    public Integer getId() {
        return id;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public static Status getStatusById(Integer id) {
        for(Status e : values()) {
            if(e.id.equals(id)) return e;
        }
        return N0BEGIN;
    }

}
