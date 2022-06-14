package com.qulix.yurkevichvv.trainingtask.main.entity;

public enum Status {
    N0BEGIN ("Не начата"),
    INTHEPROCESS ("В процессе"),
    COMPLETED("Завершена"),
    POSTPONED ("Отложена");


    private String statusTitle;

    Status(String status) {
        this.statusTitle = status;
    }
    public String getStatus() {
        return statusTitle;
    }

}
