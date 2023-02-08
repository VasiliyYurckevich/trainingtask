package com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

/**
 * Конвертер в элемент выпадающего списка для проекта.
 *
 * @author Q-YVV
 */
public class ProjectDropDownItemConverter implements DropDownItemConverter<Project> {

    @Override
    public DropDownListItem convert(Project project) {
        return new DropDownListItem(project.getId(), project.getTitle());
    }
}
