package com.qulix.yurkevichvv.trainingtask.servlets.dropdown;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

public class ProjectConverter implements DropDownItemConverter<Project> {

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
