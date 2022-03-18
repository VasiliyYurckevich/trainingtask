package com.qulix.yurkevichvv.trainingtask.model;

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
        this.surname = surname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
    }

    public Employee(String surname, String firstName, String lastName, String post) {
        super();
        this.surname = surname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
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