package com.qulix.yurkevichvv.trainingtask.wicket;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import org.apache.wicket.model.CompoundPropertyModel;

public  class EntityPageFactory {

    public static AbstractEntityPage<Task> getPage(Task task) { return new TaskPage(new CompoundPropertyModel<>(task)); }

    public static AbstractEntityPage<Employee> getPage(Employee employee) { return new EmployeePage(new CompoundPropertyModel<>(employee)); }

    public static AbstractEntityPage<Project> getPage(Project project) { return new ProjectPage(new CompoundPropertyModel<>(project)); }

}
