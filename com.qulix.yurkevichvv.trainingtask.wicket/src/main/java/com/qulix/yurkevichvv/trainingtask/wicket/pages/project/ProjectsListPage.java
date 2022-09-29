package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;



/**
 * Список проектов.
 *
 * @author Q-YVV
 */
public class ProjectsListPage extends BasePage {

    private final ProjectService service = new ProjectService();
    /**
     * Конструктор.
     */
    public ProjectsListPage() {
        super();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject("Проекты");

        LoadableDetachableModel<List<Project>> projects = new LoadableDetachableModel<>() {
            @Override
            protected List<Project> load() {
                return service.findAll();
            }
        };

        CustomListView<Project> projectListView = new ProjectCustomListView(projects, service);
        add(projectListView);

        add(new Link<>("addProject", new Model<>(new Project())) {
            @Override
            public void onClick() {
                setResponsePage(new ProjectPage(this.getModel()));
            }
        });
    }

    private class ProjectCustomListView extends CustomListView<Project> {
        public ProjectCustomListView(IModel<List<Project>> projects, IService projectService) {
            super("projects", projects, projectService);
        }

        @Override
        protected void populateItem(ListItem<Project> item) {
            super.populateItem(item);
            final Project project = item.getModelObject();
            item.add(new Label("title", project.getTitle()));
            item.add(new Label("description", project.getDescription()));
        }

        @Override
        public AbstractEntityPage getNewPage(ListItem<Project> item) {
            return new ProjectPage(item.getModel()) {

                @Override
                protected void onChangesSubmitted() {
                    setResponsePage(ProjectsListPage.class);
                }
            };
        }
    }
}

