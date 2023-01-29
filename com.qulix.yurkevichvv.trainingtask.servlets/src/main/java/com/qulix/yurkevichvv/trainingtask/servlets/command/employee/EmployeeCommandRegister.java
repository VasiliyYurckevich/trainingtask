package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.EmployeeValidation;

public enum EmployeeCommandRegister {

    EMPLOYEE_LIST(new EmployeeListCommand(), "/list"),
    EDIT_EMPLOYEE(new EditEmployeeCommand(), "/edit"),
    SAVE_EMPLOYEE(new SaveEmployeeCommand(new EmployeeValidation(), new EmployeePageDataService()), "/save"),
    DELETE_EMPLOYEE(new EditEmployeeCommand(), "/delete");

    /**
     * Команда.
     */
    private final Command command;

    /**
     * Имя команды.
     */
    private final String actionName;

    EmployeeCommandRegister(Command command, String actionName) {
        this.command = command;
        this.actionName = actionName;
    }

    public static Command getCommand(String name) {
        for (EmployeeCommandRegister register  : values()) {
            if (register.actionName.equalsIgnoreCase(name)) {
                return register.command;
            }
        }
        return EMPLOYEE_LIST.command;
    }
}
