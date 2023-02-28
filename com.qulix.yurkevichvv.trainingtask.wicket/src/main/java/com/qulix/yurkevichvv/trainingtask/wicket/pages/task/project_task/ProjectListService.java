package com.qulix.yurkevichvv.trainingtask.wicket.pages.task.project_task;

import java.util.List;
import java.util.Optional;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

/**
 * Сервис для загрузки корректного списка существующих проектов.
 *
 * @author Q-YVV
 */
public class ProjectListService {

    /**
     * Возвращает список с текущими данными, заменяя невалидные данные из БД.
     *
     * @param currentProject нынешние данные проекта, к которому привязана задача
     * @return {@link List} объектов {@link Project} с корректными текущими данными
     */
    public static List<Project> getProjectListWithNew(Project currentProject) {
        List<Project> projects = new ProjectService().findAll();

        Optional<Project> projectInDataBase = projects.stream()
            .filter(project -> project.getId().equals(currentProject.getId())).findAny();

        if (projectInDataBase.isEmpty()) {
            projects.add(currentProject);
            return projects;
        }

        projects.set(projects.indexOf(projectInDataBase.get()), currentProject);
        return projects;
    }
}
