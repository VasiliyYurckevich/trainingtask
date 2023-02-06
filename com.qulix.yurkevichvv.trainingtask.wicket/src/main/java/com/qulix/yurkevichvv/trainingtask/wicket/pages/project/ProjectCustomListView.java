package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Реализует CustomListView для проектов.
 *
 * @author Q-YVV
 */
class ProjectCustomListView extends CustomListView<Project> {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param projects модель списка проектов
     * @param projectService сервис для работы с проектами
     */
    public ProjectCustomListView(String id, LoadableDetachableModel<List<Project>> projects, IService<Project> projectService) {
        super(id, projects, projectService);
    }

    @Override
    protected AbstractEntityPageFactory<Project> generatePageFactory() {
        return new ProjectPageFactory();
    }

    @Override
    protected void populateItem(ListItem<Project> item) {
        super.populateItem(item);
        final Project project = item.getModelObject();
        item.add(new Label("title", project.getTitle()))
                .add(new Label("description", project.getDescription()));
    }
}
