package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.ProjectsListPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.time.LocalDate;

public class AddTaskForm extends Form<AddTaskForm> {

    private Status status;
    private String title;
    private Integer workTime;
        private LocalDate beginDate;
    private LocalDate endDate;
    private Integer projectId;
    private Integer employeeId;

    private TaskDao taskDao = new TaskDao();

    public AddTaskForm(String id) {
        super(id);
        setModel(new CompoundPropertyModel<>(this));
        add(new DropDownChoice<>("status"));
        add(new RequiredTextField<Integer>("title"));
        add(new RequiredTextField<String>("workTime"));
        add(new RequiredTextField<String>("beginDate"));
        add(new RequiredTextField<String>("endDate"));
        add(new DropDownChoice<Integer>("projectId"));
        add(new DropDownChoice<String>("employeeId"));
    }

    @Override
    protected void onSubmit() {
        Task task = new Task();
        task.setStatus(status);
        task.setTitle(title);
        task.setWorkTime(workTime);
        task.setBeginDate(beginDate);
        task.setEndDate(endDate);
        task.setProjectId(projectId);
        task.setEmployeeId(employeeId);
        taskDao.add(task, ConnectionController.getConnection());
        setResponsePage(ProjectsListPage.class);
    }

}
