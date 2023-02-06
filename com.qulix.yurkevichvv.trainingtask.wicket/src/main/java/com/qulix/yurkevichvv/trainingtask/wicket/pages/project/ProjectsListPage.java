package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.EntityEditPageLink;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
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
}
