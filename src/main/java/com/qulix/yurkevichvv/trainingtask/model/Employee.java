package com.qulix.yurkevichvv.trainingtask.model;


import com.qulix.yurkevichvv.trainingtask.util.Util;

import static com.qulix.yurkevichvv.trainingtask.util.Util.htmlSpecialChars;

public class Employee {

    protected int id;
    protected String surname;
    protected String firstName;
    protected String lastName;
    protected String post;

    public Employee() {
    }

    public Employee(int id, String surname, String firstName, String lastName, String post) {
        super();
        this.id = id;
        this.surname = htmlSpecialChars(surname);
        this.firstName = htmlSpecialChars(firstName);
        this.lastName = htmlSpecialChars(lastName);
        this.post = htmlSpecialChars(post);
    }

    public Employee(String surname, String firstName, String lastName, String post) {
        super();
        this.surname = htmlSpecialChars(surname);
        this.firstName = htmlSpecialChars(firstName);
        this.lastName = htmlSpecialChars(lastName);
        this.post = htmlSpecialChars(post);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }


}