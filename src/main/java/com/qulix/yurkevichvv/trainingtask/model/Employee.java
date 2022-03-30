package com.qulix.yurkevichvv.trainingtask.model;

import static com.qulix.yurkevichvv.trainingtask.util.Util.htmlSpecialChars;

/**
 * Class Employee describes employee.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Employee {
    // Fields
    protected int id;
    protected String surname;
    protected String firstName;
    protected String lastName;
    protected String post;

    // Constructors
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
    // Getters and setters
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

    // Method toString
    @Override
    public String toString() {
        return "Employee id=" + id + ", surname=" + surname + ", firstName=" + firstName + ", lastName=" + lastName
                + ", post=" + post;
    }
}