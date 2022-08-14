package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.wicket.forms.TaskForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

public class AddTaskPage extends BasePage {

    private ProjectDao projectDao= new ProjectDao();
    private EmployeeDao employeeDao= new EmployeeDao();
    private TaskDao taskDao= new TaskDao();

    public AddTaskPage() {
        super();
        get("pageTitle").setDefaultModelObject("Добавить задачу");
        TaskForm taskForm = new TaskForm("addTaskForm"){
            @Override
            protected void onSubmit() {
                taskDao.add(getModelObject(), ConnectionController.getConnection());
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
        taskForm.add(new DropDownChoice("employees", new PropertyModel<>(taskForm.getModelObject(), "employeeId"),
            employeeDao.getAll(), new ChoiceRenderer<Employee>(null, "id" ){
            @Override
            public Object getDisplayValue(Employee employee){
                return employee.getFullName();
            }
        }));
        taskForm.add(new DropDownChoice("projects", new PropertyModel<>(taskForm.getModelObject(), "projectId"),
                projectDao.getAll(), new ChoiceRenderer<Project>("title", "id")));
        add(taskForm);
    }
}
