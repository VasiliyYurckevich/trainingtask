package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.EditProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.stream.Collectors;

public class TaskPage extends BasePage {

    public TaskPage() {
        get("pageTitle").setDefaultModelObject("Добавить задачу");
        Form<Task> taskForm = new Form<Task>("taskForm", new CompoundPropertyModel<>(new Task())) {
        @Override
            protected void onSubmit() {
                TaskDao taskDao = new TaskDao();
                taskDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(new TasksListPage());
            }
        };
        addFormComponent(taskForm);
        add(taskForm);
    }

    public TaskPage(Project project, List<Task> projectTasks) {
        get("pageTitle").setDefaultModelObject("Добавить задачу в проект " + project.getTitle());
        Form<Task> taskForm = new Form<>("taskForm", new CompoundPropertyModel<>(new Task())){
            @Override
            protected void onSubmit() {
                projectTasks.add(getModelObject());
                setResponsePage(new EditProjectPage(project, projectTasks));
            }
        };
        addFormComponent(taskForm);
        taskForm.get("projects").setDefaultModelObject(project.getId());
        taskForm.get("projects").setEnabled(false);
        add(taskForm);
    }
    public TaskPage(final Task task) {
        get("pageTitle").setDefaultModelObject("Редактировать задачу");
        Form<Task> taskForm = new Form<Task>("taskForm", new CompoundPropertyModel<>(task)){
            @Override
            protected void onSubmit() {
                System.out.println(getModelObject().toString());
                TaskDao taskDao = new TaskDao();
                taskDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(TasksListPage.class);
            }
        };
        addFormComponent(taskForm);
        add(taskForm);
    }

    private void addFormComponent(Form<Task> taskForm) {
        DropDownChoice statusesDropDownChoice = new DropDownChoice("statuses", new PropertyModel<>(taskForm.getModelObject(), "status"),
                List.of(Status.values()), new ChoiceRenderer<Status>("statusTitle"));
        statusesDropDownChoice.setRequired(true);
        taskForm.add(statusesDropDownChoice);
        RequiredTextField<String> titleField =  new RequiredTextField<String>("title");
        taskForm.add(titleField);
        RequiredTextField<Integer> workTimeField = new RequiredTextField<>("workTime");
        taskForm.add(workTimeField);
        LocalDateTextField beginDateField =  new LocalDateTextField("beginDate",
                new PropertyModel<>(taskForm.getModelObject(), "beginDate"),"yyyy-MM-dd");
        taskForm.add(beginDateField.setEnabled(true));
        LocalDateTextField endDateTextField = new LocalDateTextField("endDate", new PropertyModel<>(taskForm.getModelObject(), "endDate"), "yyyy-MM-dd");
        taskForm.add(endDateTextField);
        ProjectDao projectDao = new ProjectDao();
        DropDownChoice projectDropDownChoice = new DropDownChoice<Integer>("projects", new PropertyModel(taskForm.getModelObject(), "projectId"),
            projectDao.getAll().stream().map(Project::getId).collect(Collectors.toList()), new ChoiceRenderer<>() {
                @Override
                public String getDisplayValue(Integer id){
                    return projectDao.getAll().stream().filter(project -> id.equals(project.getId())).findFirst().orElse(null).getTitle();
                }
        });
        System.out.println(projectDropDownChoice.getChoices());
        projectDropDownChoice.setRequired(true);
        taskForm.add(projectDropDownChoice);
        EmployeeDao employeeDao = new EmployeeDao();
        DropDownChoice<Integer> employeesDropDownChoice = new DropDownChoice<Integer>("employees",
            new PropertyModel(taskForm.getModelObject(), "employeeId"),
            employeeDao.getAll().stream().map(Employee::getId).collect(Collectors.toList()), new ChoiceRenderer<Integer>() {
            @Override
            public String getDisplayValue(Integer id) {
                return employeeDao.getAll().stream().filter(employee ->
                        id.equals(employee.getId())).findFirst().orElse(null).getFullName();

            }
        });
        employeesDropDownChoice.setNullValid(true);
        taskForm.add(employeesDropDownChoice);
    }
}
