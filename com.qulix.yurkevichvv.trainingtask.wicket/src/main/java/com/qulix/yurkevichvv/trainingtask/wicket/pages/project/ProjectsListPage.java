package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomListView;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import org.apache.wicket.model.LoadableDetachableModel;


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
        CustomListView<Project> projectListView = new CustomListView<Project>("projects",projects, ProjectPage.class) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                super.populateItem(item);
                final Project project = item.getModelObject();
                item.add(new Label("title", project.getTitle()));
                item.add(new Label("description", project.getDescription()));
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

