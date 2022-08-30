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
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
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
                setResponsePage(TasksListPage.class);
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
    public TaskPage(Task task) {
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

    private void addFormComponent(Form<Task> form) {
        Link<Void> cancelButton = new Link<>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(TasksListPage.class);
            }
        };
        form.add(cancelButton);
        addStatusesDropDownChoice(form);
        addTitleField(form);
        addWorkTimeField(form);
        addBeginDateField(form);
        addEndDateTextField(form);
        addProjectDropDownChoice(form);
        addEmployeesDropDownChoice(form);
    }

    private static void addStatusesDropDownChoice(Form<Task> form) {
        DropDownChoice statusesDropDownChoice = new DropDownChoice("statuses", new PropertyModel<>(form.getModelObject(), "status"),
                List.of(Status.values()), new ChoiceRenderer<Status>("statusTitle"));
        statusesDropDownChoice.setRequired(true);
        CustomFeedbackPanel employeesFeedbackPanel = new CustomFeedbackPanel("statusesFeedbackPanel",
                new ComponentFeedbackMessageFilter(statusesDropDownChoice));
        form.add(employeesFeedbackPanel);
        form.add(statusesDropDownChoice);
    }

    private static void addTitleField(Form<Task> form) {
        RequiredTextField<String> titleField =  new RequiredTextField<String>("title");
        titleField.add(new CustomStringValidator(50));
        form.add(titleField);
        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel",
                new ComponentFeedbackMessageFilter(titleField));
        form.add(titleFeedbackPanel);
    }

    private static void addWorkTimeField(Form<Task> form) {
        RequiredTextField<Integer> workTimeField = new RequiredTextField<>("workTime");
        form.add(workTimeField);
        CustomFeedbackPanel workTimeFeedbackPanel = new CustomFeedbackPanel("workTimeFeedbackPanel",
                new ComponentFeedbackMessageFilter(workTimeField));
        form.add(workTimeFeedbackPanel);
    }

    private static void addBeginDateField(Form<Task> form) {
        LocalDateTextField beginDateField =  new LocalDateTextField("beginDate",
            new PropertyModel<>(form.getModelObject(), "beginDate"),"yyyy-MM-dd");
        form.add(beginDateField);
        beginDateField.setRequired(true);
        CustomFeedbackPanel beginDateFeedbackPanel = new CustomFeedbackPanel("beginDateFeedbackPanel",
                new ComponentFeedbackMessageFilter(beginDateField));
        form.add(beginDateFeedbackPanel);
    }

    private static void addEndDateTextField(Form<Task> form) {
        LocalDateTextField endDateTextField = new LocalDateTextField("endDate",
            new PropertyModel<>(form.getModelObject(), "endDate"), "yyyy-MM-dd");
        endDateTextField.setRequired(true);
        form.add(endDateTextField);
        CustomFeedbackPanel endDateFeedbackPanel = new CustomFeedbackPanel("endDateFeedbackPanel",
                new ComponentFeedbackMessageFilter(endDateTextField));
        form.add(endDateFeedbackPanel);
    }

    private static void addProjectDropDownChoice(Form<Task> form) {
        ProjectDao projectDao = new ProjectDao();
        DropDownChoice projectDropDownChoice = new DropDownChoice<Integer>("projects",
            new PropertyModel(form.getModelObject(), "projectId"),
            projectDao.getAll().stream().map(Project::getId).collect(Collectors.toList()), new ChoiceRenderer<>() {
                @Override
                public String getDisplayValue(Integer id){
                    return projectDao.getAll().stream().filter(project -> id.equals(project.getId())).findFirst().orElse(null).getTitle();
                }
        });
        projectDropDownChoice.setRequired(true);
        CustomFeedbackPanel workTimeFeedbackPanel = new CustomFeedbackPanel("projectFeedbackPanel",
                new ComponentFeedbackMessageFilter(projectDropDownChoice));
        form.add(workTimeFeedbackPanel);
        form.add(projectDropDownChoice);
    }

    private static void addEmployeesDropDownChoice(Form<Task> form) {
        EmployeeDao employeeDao = new EmployeeDao();
        DropDownChoice<Integer> employeesDropDownChoice = new DropDownChoice<Integer>("employees",
            new PropertyModel(form.getModelObject(), "employeeId"),
            employeeDao.getAll().stream().map(Employee::getId).collect(Collectors.toList()), new ChoiceRenderer<Integer>() {
                @Override
                public String getDisplayValue(Integer id) {
                    return employeeDao.getAll().stream().filter(employee ->
                            id.equals(employee.getId())).findFirst().orElse(null).getFullName();

                }
        });
        employeesDropDownChoice.setNullValid(true);
        CustomFeedbackPanel employeesFeedbackPanel = new CustomFeedbackPanel("employeesFeedbackPanel",
                new ComponentFeedbackMessageFilter(employeesDropDownChoice));
        form.add(employeesFeedbackPanel);
        form.add(employeesDropDownChoice);
    }
}
