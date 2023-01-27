package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.beans.JavaBean;
import java.util.Objects;


/**
 * Сущность "Проект".
 *
 * @author Q-YVV
 */
@JavaBean
public class Project implements Entity {

    /**
     * Идентификатор проекта.
     */
    private Integer id;

    /**
     * Название проекта.
     */
    private String title;

    /**
     * Описание проекта.
     */
    private String description;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Project { id= '%s', title='%s', description= '%s'}",
                id, title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
