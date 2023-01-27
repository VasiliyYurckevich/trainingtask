package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.beans.JavaBean;
import java.util.Objects;

/**
 * Сущность "Сотрудник".
 *
 * @author Q-YVV
 */
@JavaBean
public class Employee implements Entity {

    /**
     * Идентификатор сотрудника.
     */
    private Integer id;

    /**
     * Имя сотрудника.
     */
    private String surname;

    /**
     * Фамилия сотрудника.
     */
    private String firstName;

    /**
     * Отчество сотрудника.
     */
    private String patronymic;

    /**
     * Должность сотрудника.
     */
    private String post;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFullName() {
        return String.format("%s %s %s", surname, firstName, patronymic);
    }

    @Override
    public String toString() {
        return String.format("Employee { id= '%s', surname='%s', firstName= '%s', patronymic= '%s' , post= '%s'}",
                id, surname, firstName, patronymic, post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, firstName, patronymic, post);
    }
}
