package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;


/**
 * Список проектов.
 *
 * @author Q-YVV
 */
public class ProjectsListPage extends BasePage {

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
                return new ProjectDao().getAll();
            }
        };
        ListView<Project> projectListView = new ListView<>("projects", projects) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                final Project project = item.getModelObject();
                item.add(new Label("title", project.getTitle()));
                item.add(new Label("description", project.getDescription()));
                item.add(new DeleteLink("deleteLink", project));
                item.add(new Link<>("editLink", item.getModel()) {
                    @Override
                    public void onClick() {
                        setResponsePage(new ProjectPage(project));
                    }
                });
            }
        };
        add(projectListView);
        add(new Link<WebPage>("addProject") {
            @Override
            public void onClick() {
                Project project = new Project();
                setResponsePage(new ProjectPage(project));
            }
        });
    }
}

