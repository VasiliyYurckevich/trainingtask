package com.qulix.yurkevichvv.trainingtask.servlets;

public class DropDownListItem {

    private Integer id;

    private String value;

    public DropDownListItem(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
