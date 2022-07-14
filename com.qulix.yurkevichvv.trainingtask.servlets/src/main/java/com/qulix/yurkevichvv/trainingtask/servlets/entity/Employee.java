/*
 * Copyright 2007 Qulix Systems, Inc. All rights reserved.
 * QULIX SYSTEMS PROPRIETARY/CONFIDENTIAL. Use is subject to license
 * terms.
 * Copyright (c) 2003-2007 Qulix Systems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Qulix Systems. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * QULIX MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.qulix.yurkevichvv.trainingtask.servlets.entity;

import java.util.Objects;

/**
 * Сущность "Сотрудник".
 *
 * @author Q-YVV
 */
public class Employee {

    /**
     * Пробел.
     */
    public static final String SPACE = " ";

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
        return "Employee id=" + id +
            ", surname=" + surname +
            ", firstName=" + firstName +
            ", patronymic=" + patronymic +
            ", post=" + post;
    }

    public String getFullName() {
        return String.format("%s %s %s", surname, firstName, patronymic);
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
        return Objects.equals(id, employee.id) &&
            Objects.equals(surname, employee.surname) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(patronymic, employee.patronymic) &&
            Objects.equals(post, employee.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, firstName, patronymic, post);
    }
}
