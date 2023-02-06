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

    /**
     * Устанавливает ID проекта.
     *
     * @param id идентификатор
     */
    public void setId(Integer id) {
        project.setId(id);
    }

    public Project getProject() {
        return project;
    }

    public String getTitle() {
        return project.getTitle();
    }

    public String getDescription() {
        return project.getDescription();
    }

    /**
     * Устанавливает наименование проекта.
     *
     * @param title идентификатор
     */
    public void setTitle(String title) {
        project.setTitle(title);
    }

    /**
     * Устанавливает описание проекта.
     *
     * @param description идентификатор
     */
    public void setDescription(String description) {
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

        if (!project.equals(that.project)) {
            return false;
        }
        return Objects.equals(tasksList, that.tasksList);
    }

    @Override
    public int hashCode() {
        int result = 21 * project.hashCode();
        result = 31 * result + (tasksList != null ? tasksList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("ProjectTemporaryData{ project= '%s', tasksList= '%s'}",
                project.toString(), tasksList);
    }
}
