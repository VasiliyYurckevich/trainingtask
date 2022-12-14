package com.qulix.yurkevichvv.trainingtask.wicket.companents.models;

import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

public class ProjectListLoadableDetachableModel extends LoadableDetachableModel<List<Project>> {
    @Override
    protected List<Project> load() {
        return new ProjectService().findAll();
    }
}
