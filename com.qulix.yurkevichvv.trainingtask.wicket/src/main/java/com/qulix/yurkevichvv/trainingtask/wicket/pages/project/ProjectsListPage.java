package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.ArrayList;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.Header;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;


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
        List<Project> projects = new ProjectDao().getAll();
        ListView<Project> projectListView = new ListView<>("projects", projects) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                final Project project = item.getModelObject();
                item.add(new Label("title", project.getTitle()));
                item.add(new Label("description", project.getDescription()));
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(projectListView);
        add(new Link<WebPage>("addProject") {
            @Override
            public void onClick() {
                setResponsePage(new ProjectPage(new ArrayList<Task>()));
            }
        });
    }
}
