package com.qulix.yurkevichvv.trainingtask.servlets.view_items;

import java.beans.JavaBean;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

/**
 * Визуализация для данных задачи.
 *
 * @author Q-YVV
 */
@JavaBean
public class TaskView implements Serializable {

    /**
     * Задача.
     */
    private final Task task;

    /**
     * Наименование проекта задачи.
     */
    private final String projectTitle;

    /**
     * Полное имя сотрудника задачи.
     */
    private final String employeeFullName;

    /**
     * Конструктор.
     *
     * @param task задача.
     */
    public TaskView(Task task) {
        this.task = task;
        this.projectTitle = (task.getProjectId() != null) ?
            new ProjectService().getById(task.getProjectId()).getTitle() : "";
        this.employeeFullName = (task.getEmployeeId() != null) ?
            new EmployeeService().getById(task.getEmployeeId()).getFullName() : "";
    }

    /**
     * Конвертирует список задач в список для визуализации.
     *
     * @param taskList список задач
     * @return список объектов для визуализации задач
     */
    public static List<TaskView> convertTasksList(List<Task> taskList) {
        return taskList.stream()
                .map(TaskView::new)
                .collect(Collectors.toList());
    }


    public Integer getId() {
        return task.getId();
    }

    public String getStatusTitle() {
        return task.getStatus().getStatusTitle();
    }

    public String getTitle() {
        return task.getTitle();
    }

    public String getWorkTime() {
        return task.getWorkTime().toString();
    }

    public String getBeginDate() {
        return this.task.getBeginDate().toString();
    }

    public String getEndDate() {
        return this.task.getEndDate().toString();
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public Task getTask() {
        return task;
    }
}
