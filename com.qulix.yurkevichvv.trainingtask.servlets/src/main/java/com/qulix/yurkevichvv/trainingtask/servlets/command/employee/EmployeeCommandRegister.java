package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.controllers.EmployeeController;

/**
 * Регистр команд используемых в {@link EmployeeController}.
 *
 * @author Q-YVV
 */
public enum EmployeeCommandRegister {

    /**
     * Список {@link Employee}.
     */
    EMPLOYEE_LIST(new EmployeeListCommand(), "/list"),

    /**
     * Редактирование {@link Employee}.
     */
    EDIT_EMPLOYEE(new EditEmployeeCommand(), "/edit"),

    /**
     * Сохранение {@link Employee}.
     */
    SAVE_EMPLOYEE(new SaveEmployeeCommand(), "/save"),

    /**
     * Удаление {@link Employee}.
     */
    DELETE_EMPLOYEE(new DeleteEmployeeCommand(), "/delete");

    /**
     * Команда.
     */
    private final Command command;

    /**
     * Имя команды.
     */
    private final String actionName;

    /**
     * Перечисление команд связанных с {@link EmployeeController}.
     *
     * @param command объект {@link Command}.
     * @param actionName имя команды, полученное из запроса
     */
    EmployeeCommandRegister(Command command, String actionName) {
        this.command = command;
        this.actionName = actionName;
    }

    /**
     * Находит объект {@link Command} по имени команды (по умолчанию {@link EmployeeListCommand}).
     *
     * @param name имя команды из запроса
     * @return объект {@link Command}
     */
    public static Command getCommand(String name) {
        for (EmployeeCommandRegister register : values()) {
            if (register.actionName.equalsIgnoreCase(name)) {
                return register.command;
            }
        }
        return EMPLOYEE_LIST.command;
    }
}
