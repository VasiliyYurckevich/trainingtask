package com.qulix.yurkevichvv.trainingtask.model;

import static com.qulix.yurkevichvv.trainingtask.util.Util.htmlSpecialChars;

public class Project {
    protected int id;
    protected String title;
    protected String discription;

    public Project() {
    }

    public Project(String title, String discription) {
        this.title = htmlSpecialChars(title);
        this.discription = htmlSpecialChars(discription);
    }

    public Project(int id, String title, String discription) {
        this.id = id;
        this.title = htmlSpecialChars(title);
        this.discription = htmlSpecialChars(discription);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }


}