package com.qulix.yurkevichvv.trainingtask.servlets.command.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.command.Command;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.EmployeePageDataService;

/**
 * Открывает форму редактирования {@link Employee}.
 *
 * @author Q-YVV
 */
public class EditEmployeeCommand implements Command {

    /**
     * Сервис для взаимодействия данных {@link Employee} и их визуализации на странице.
     */
    private final EmployeePageDataService pageDataService = new EmployeePageDataService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        pageDataService.setDataToPage(req, pageDataService.getEntity(req));

        req.getRequestDispatcher("/edit-employee-form.jsp").forward(req, resp);
    }
}
