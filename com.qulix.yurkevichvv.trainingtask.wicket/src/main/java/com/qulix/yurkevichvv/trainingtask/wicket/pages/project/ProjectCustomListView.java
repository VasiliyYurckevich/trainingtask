package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 * Реализует CustomListView для проектов.
 *
 * @author Q-YVV
 */
class ProjectCustomListView extends CustomListView<Project> {

    /**
     * Конструктор.
     *
     * @param projects       модель списка проектов
     * @param projectService сервис для работы с проектами
     * @param id
     */
    public ProjectCustomListView(String id, LoadableDetachableModel<List<Project>> projects, IService<Project> projectService) {
        super(id, projects, projectService);
    }

    @Override
    protected AbstractEntityPageFactory<Project> getPageFactory() {
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
