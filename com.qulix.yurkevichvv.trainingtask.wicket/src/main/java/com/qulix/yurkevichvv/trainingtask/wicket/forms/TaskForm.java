package com.qulix.yurkevichvv.trainingtask.wicket.forms;

import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class TaskForm extends Form<Task> {

    public TaskForm(String id) {
        super(id, new CompoundPropertyModel<>(new Task()));
    }

    public TaskForm(String id, Task task) {
        super(id, new CompoundPropertyModel<>(task));
    }

    @Override
    protected void onSubmit() {
    }

}
