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
package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;

/**
 * Сущность "Проект".
 *
 * @author Q-YVV
 */
public class Project implements Serializable, Entity {

    private static final long  serialVersionUID = 87654L;

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

    /**
     * Задачи проекта.
     */
    private List<Task> tasksList;

    private List<Task> deletedTasksList = new ArrayList<>();

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

    /**
     * Возвращает список задач проекта. При первом вызове получает данные из БД
     * либо, если проекта нет в БД, пустой список.
     *
     * @return список задач проекта
     */
    public List<Task> getTasksList() {
        if (tasksList == null) {
            if (id == null) {
                tasksList = new ArrayList<>();
            }
            else {
                tasksList = new TaskDao().getTasksInProject(id);
            }
        }
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public List<Task> getDeletedTasksList() {
        return deletedTasksList;
    }

    public void setDeletedTasksList(List<Task> deletedTasksList) {
        this.deletedTasksList = deletedTasksList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
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
        return id == project.id && Objects.equals(title, project.title) && Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
