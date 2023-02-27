package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.io.Serializable;
import java.util.Optional;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Добавляет соответствующие колонки в ListView.
 *
 * @author Q-YVV
 */
public interface ITaskTableColumns extends Serializable {

    /**
     * Добавляет соответствующие колонки в ListView.
     *
     * @param item элемент ListView.
     */
    static void addColumns(ListItem<Task> item) {
        final Task task = item.getModelObject();

        item.add(new Label("status", task.getStatus().getStatusTitle()))
            .add(new Label("taskTitle", task.getTitle()))
            .add(new Label("workTime", task.getWorkTime()))
            .add(new Label("beginDate", task.getBeginDate().toString()))
            .add(new Label("endDate", task.getEndDate().toString()))
            .add(new Label("employeeName", Optional.ofNullable(task.getEmployee()).map(Employee::getFullName).orElse("")));
    }
}
