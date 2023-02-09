package com.qulix.yurkevichvv.trainingtask.servlets.command.task;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.TaskController;

/**
 * Регистр команд используемых в {@link TaskController}.
 *
 * @author Q-YVV
 */
public enum TaskCommandRegister {

    /**
     * Список {@link Project}.
     */
    TASK_LIST(new TaskListCommand(), "/list"),

    /**
     * Редактирование {@link Project}.
     */
    EDIT_TASK(new EditTaskCommand(), "/edit"),

    /**
     * Сохранение {@link Project}.
     */
    SAVE_TASK(new SaveTaskCommand(), "/save"),

    /**
     * Удаление {@link Project}.
     */
    DELETE_TASK(new DeleteTaskCommand(), "/delete"),

    /**
     * Сохранение {@link Task} из списка задач {@link ProjectTemporaryData}.
     */
    SAVE_PROJECT_TASK(new SaveProjectTaskCommand(), "/saveTaskInProject");

    /**
     * Команда.
     */
    private final Command command;

    /**
     * Имя команды.
     */
    private final String actionName;

    /**
     * Конструктор.
     *
     * @param command команда
     * @param actionName имя команды
     */
    TaskCommandRegister(Command command, String actionName) {
        this.command = command;
        this.actionName = actionName;
    }

    /**
     * Находит объект {@link Command} по имени команды (по умолчанию {@link TaskCommandRegister}).
     *
     * @param name имя команды из запроса
     * @return объект {@link Command}
     */
    public static Command getCommand(String name) {
        for (TaskCommandRegister register : values()) {
            if (register.actionName.equalsIgnoreCase(name)) {
                return register.command;
            }
        }
        return TASK_LIST.command;
    }
}
