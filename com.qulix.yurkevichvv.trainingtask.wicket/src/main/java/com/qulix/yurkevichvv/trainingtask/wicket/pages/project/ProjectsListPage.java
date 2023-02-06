package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EntityEditPageLink;
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
        add(new EntityEditPageLink<>("addProject", getPageFactory(), Model.of(new Project())));
        add(new ProjectCustomListView("projects", LoadableDetachableModel.of(() -> new ProjectService().findAll()),
            getService()));
    }
}
