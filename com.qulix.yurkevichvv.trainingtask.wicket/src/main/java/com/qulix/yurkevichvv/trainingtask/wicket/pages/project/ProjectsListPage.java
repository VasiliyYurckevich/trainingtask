package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractListPage;

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

        CustomListView<Project> projectListView =
            new ProjectCustomListView(LoadableDetachableModel.of(() -> new ProjectService().findAll()), service);
        add(projectListView);

        add(new Link<WebPage>("addProject",
            new Model<>(pageFactory.createPage(CompoundPropertyModel.of(new Project())))) {
            @Override
            public void onClick() {
                setResponsePage(getModelObject());
            }
        });
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
         * @param projects модель списка проектов
         * @param projectService сервис для работы с проектами
         */
        public ProjectCustomListView(LoadableDetachableModel<List<Project>> projects, IService projectService) {
            super("projects", projects, projectService);
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
