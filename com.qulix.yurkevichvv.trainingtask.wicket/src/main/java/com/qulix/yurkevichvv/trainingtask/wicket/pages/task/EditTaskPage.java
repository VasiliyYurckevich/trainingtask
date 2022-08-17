package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.TaskForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.stream.Collectors;

public class EditTaskPage extends BasePage {

    private ProjectDao projectDao = new ProjectDao();
    private EmployeeDao employeeDao = new EmployeeDao();
    private TaskDao taskDao = new TaskDao();

    public EditTaskPage(final Task task) {
        get("pageTitle").setDefaultModelObject("Редактировать задачу");
        TaskForm taskForm = new TaskForm("addTaskForm", task){
            @Override
            protected void onSubmit() {
                taskDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(TasksListPage.class);
            }
        };
        DropDownChoice statuses = new DropDownChoice("statuses", new PropertyModel<>(taskForm.getModelObject(), "status"),
                List.of(Status.values()), new ChoiceRenderer<Status>("statusTitle"));
        taskForm.add(statuses);
        RequiredTextField<String> titleField =  new RequiredTextField<String>("title");
        taskForm.add(titleField);
        taskForm.add(new RequiredTextField<Integer>("workTime"));
        taskForm.add(new LocalDateTextField("beginDate",
                new PropertyModel<>(taskForm.getModelObject(), "beginDate"),"yyyy-MM-dd"));
        taskForm.add(new LocalDateTextField("endDate", new PropertyModel<>(taskForm.getModelObject(), "endDate"),"yyyy-MM-dd"));
        DropDownChoice projectDropDownChoice = new DropDownChoice<Integer>("projects", new PropertyModel(taskForm.getModelObject(), "projectId"),
                projectDao.getAll().stream().map(Project::getId).collect(Collectors.toList()), new ChoiceRenderer<Integer>(){
            @Override
            public String getDisplayValue(Integer id){
                return projectDao.getAll().stream().filter(project -> id.equals(project.getId())).findFirst().orElse(null).getTitle();
            }
        });
        taskForm.add(projectDropDownChoice);
        taskForm.add(new DropDownChoice<Integer>("employees", new PropertyModel(taskForm.getModelObject(), "employeeId"),
                employeeDao.getAll().stream().map(Employee::getId).collect(Collectors.toList()), new ChoiceRenderer<Integer>() {
            @Override
            public String getDisplayValue(Integer id)
            {
                return employeeDao.getAll().stream().filter(employee -> id.equals(employee.getId())).findFirst().orElse(null).getFullName();

            }
        }));
        add(taskForm);
    }
}
