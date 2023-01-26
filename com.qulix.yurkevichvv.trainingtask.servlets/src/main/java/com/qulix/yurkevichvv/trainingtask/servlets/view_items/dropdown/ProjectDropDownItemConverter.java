package com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<DropDownListItem> convertList(List<Project> list) {
        return  list.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }
}
