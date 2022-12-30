package com.qulix.yurkevichvv.trainingtask.model.temporary;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Данные связанные с проектом при его редактировании.
 *
 * @author Q-YVV
 */
public class ProjectTemporaryData {

    private Integer id;

    private String title;

    private String description;

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
}
