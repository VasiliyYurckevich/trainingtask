package com.qulix.yurkevichvv.trainingtask.model.entity;

import java.util.List;
import java.util.Objects;

import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;

/**
 * Данные связанные с проектом при его редактировании.
 *
 * @author Q-YVV
 */
public class ProjectTemporaryData implements Entity {

    /**
     * Проект.
     */
    private Project project;

    /**
     * Список связанных задач.
     */
    private List<Task> tasksList;

    /**
     * Конструктор.
     *
     * @param project проект
     */
    public ProjectTemporaryData(Project project) {
        this.project = project;
        this.tasksList = new ProjectTemporaryService().getProjectsTasks(project.getId());
    }

    @Override
    public Integer getId() {
        return project.getId();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectTemporaryData that = (ProjectTemporaryData) o;
        return Objects.equals(project, that.project) && Objects.equals(tasksList, that.tasksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, tasksList);
    }

    @Override
    public String toString() {
        return "ProjectTemporaryData{" +
                "project = " + project.toString() +
                ", tasksList=" + tasksList +
                '}';
    }
}
