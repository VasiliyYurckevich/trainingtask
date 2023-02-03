package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EntityEditPageLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractListPage;
import org.apache.wicket.model.Model;

/**
 * Список проектов.
 *
 * @author Q-YVV
 */
public class ProjectsListPage extends AbstractListPage<Project> {

    /**
     * Конструктор.
     */
    public ProjectsListPage() {
        super("Проекты", new ProjectPageFactory(), new ProjectService());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ProjectCustomListView("projects", LoadableDetachableModel.of(() -> new ProjectService().findAll()),
            getService()));

        add(new EntityEditPageLink<>("addProject", getPageFactory(), Model.of(new Project())));
    }

    /**
     * Реализует CustomListView для проектов.
     *
     * @author Q-YVV
     */
    private static class ProjectCustomListView extends CustomListView<Project> {

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
}
