package com.qulix.yurkevichvv.trainingtask.servlets.lists.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

/**
 * Конвертер в элемент выпадающего списка для проекта.
 *
 * @author Q-YVV
 */
public class ProjectConverter implements DropDownItemConverter<Project> {

    @Override
    public DropDownListItem convert(Project project) {
        return new DropDownListItem(project.getId(), project.getTitle());
    }
}
