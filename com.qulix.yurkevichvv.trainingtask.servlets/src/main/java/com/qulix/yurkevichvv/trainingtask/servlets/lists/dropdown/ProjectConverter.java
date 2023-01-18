package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

/**
 * Конвертер в элемент выпадающего списка для проекта.
 *
 * @author Q-YVV
 */
public class ProjectConverter implements DropDownItemConverter<Project> {

    /**
     * Сервис для взаимодействия с сотрудниками.
     */
    private ProjectService projectService = new ProjectService();

    @Override
    public DropDownListItem convert(Project project) {
        return new DropDownListItem(project.getId(), project.getTitle());
    }

    @Override
    public Project convert(DropDownListItem dropDownListItem) {
        return projectService.getById(dropDownListItem.getId());
    }
}
