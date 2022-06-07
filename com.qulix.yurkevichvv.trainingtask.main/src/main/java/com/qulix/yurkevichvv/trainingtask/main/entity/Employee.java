package com.qulix.yurkevichvv.trainingtask.main.entity;

/**
 * Описывает сущность "Сотрудник".
 *
 * @author Q-YVV
 */
public class Employee {

    private Integer id;

    private String surname;

    private String firstName;

    private String patronymic;

    private String post;


    /**
     * Конструктор.
     */
    public Employee() {

    }

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

    @Override
    public String toString() {
        return "huiznaet.entity.Employee [id=" + id + ", surname=" + surname + ", firstName=" + firstName + ", patronymic=" + patronymic
            + ", post=" + post + "]";
    }
}
