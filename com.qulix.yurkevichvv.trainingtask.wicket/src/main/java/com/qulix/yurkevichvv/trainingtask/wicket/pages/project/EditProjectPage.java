package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.links.DeleteLink;
import com.qulix.yurkevichvv.trainingtask.wicket.links.EditLink;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.List;

import static com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage.addContent;

public class EditProjectPage extends BasePage {
    public EditProjectPage(Project project, List<Task> tasks) {
        get("pageTitle").setDefaultModelObject("Добавить проект");
        Form<Project> projectForm = new Form<Project>("projectForm", new CompoundPropertyModel<>(project)){
            @Override
            protected void onSubmit() {
                ProjectDao projectDao = new ProjectDao();
                projectDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(ProjectsListPage.class);
            }
        };
        Link<Void> addTaskLink = new Link<Void>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage(project, tasks));
            }
        };
        add(addTaskLink);
        addContent(projectForm);
        addTaskList(tasks);
        add(projectForm);
    }

    private void addTaskList(List<Task> tasks) {
        ListView<Task> taskListView = new ListView<>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                final Task task = item.getModelObject();
                final ProjectDao projectDao = new ProjectDao();
                final EmployeeDao employeeDao = new EmployeeDao();
                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", projectDao.getById(task.getProjectId()).getTitle()));
                if (task.getEmployeeId() != 0) {
                    item.add(new Label("employeeName", employeeDao.getById(task.getEmployeeId()).getFullName()));
                } else {
                    item.add(new Label("employeeName", " "));

                }
                item.add(new DeleteLink("deleteLink", item));
                item.add(new EditLink("editLink", item));
            }
        };
        add(taskListView);
    }
}

