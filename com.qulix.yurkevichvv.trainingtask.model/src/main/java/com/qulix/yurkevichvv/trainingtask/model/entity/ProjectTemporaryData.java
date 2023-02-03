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
    private final Project project;

    /**
     * Список связанных задач.
     */
    private final List<Task> tasksList;

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

    public void setId(Integer id){
        project.setId(id);
    }

    public Project getProject() {
        return project;
    }

    public String getTitle(){
        return project.getTitle();
    }

    public String getDescription(){
        return project.getDescription();
    }

    public void setTitle(String title){
        project.setTitle(title);
    }

    public void setDescription(String description){
        project.setDescription(description);
    }
    public List<Task> getTasksList() {
        return tasksList;
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
