package com.qulix.yurkevichvv.trainingtask.servlets.command.project;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.ProjectController;

/**
 * Регистр команд используемых в {@link ProjectController}.
 *
 * @author Q-YVV
 */
public enum ProjectCommandRegister {

    /**
     * Список {@link Project}.
     */
    PROJECT_LIST(new ProjectsListCommand(), "/list"),

    /**
     * Редактирование {@link Project}.
     */
    EDIT_PROJECT(new EditProjectCommand(), "/edit"),

    /**
     * Сохранение {@link Project}.
     */
    SAVE_PROJECT(new SaveProjectCommand(), "/save"),

    /**
     * Удаление {@link Project}.
     */
    DELETE_PROJECT(new DeleteProjectCommand(), "/delete"),

    /**
     * Редактирование {@link Task} из списка задач.
     */
    EDIT_PROJECT_TASK(new EditProjectTaskCommand(), "/editTask"),

    /**
     * Удаление {@link Task} из списка задач.
     */
    DELETE_PROJECT_TASK(new DeleteProjectTaskCommand(), "/deleteTask");

    /**
     * Команда.
     */
    private final Command command;

    /**
     * Имя команды.
     */
    private final String actionName;

    /**
     * Перечисление команд связанных с {@link ProjectController}.
     *
     * @param command объект {@link Command}.
     * @param actionName имя команды, полученное из запроса
     */
    ProjectCommandRegister(Command command, String actionName) {
        this.command = command;
        this.actionName = actionName;
    }

    /**
     * Находит объект {@link Command} по имени команды (по умолчанию {@link ProjectsListCommand}).
     *
     * @param name имя команды из запроса
     * @return объект {@link Command}
     */
    public static Command getCommand(String name) {
        for (ProjectCommandRegister registerItem : values()) {
            if (registerItem.actionName.equalsIgnoreCase(name)) {
                return registerItem.command;
            }
        }
        return PROJECT_LIST.command;
    }
}
