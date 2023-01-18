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
     * Идентификатор.
     */
    private Integer id;

    /**
     * Наименование.
     */
    private String title;

    /**
     * Описание.
     */
    private String description;

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
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.tasksList = new ProjectTemporaryService().getProjectsTasks(id);
    }

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

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    /**
     * Возвращает проект с новыми данными.
     *
     * @return проект с новыми данными
     */
    public Project getProject() {
        Project project = new Project();
        project.setId(id);
        project.setTitle(title);
        project.setDescription(description);
        return project;
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
        return Objects.equals(id, that.id) && Objects.equals(title, that.title)
            && Objects.equals(description, that.description) && Objects.equals(tasksList, that.tasksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tasksList);
    }

    @Override
    public String toString() {
        return "ProjectTemporaryData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tasksList=" + tasksList +
                '}';
    }
}
