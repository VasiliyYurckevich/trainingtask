package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import com.qulix.yurkevichvv.trainingtask.api.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.api.exceptions.DaoException;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.api.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.api.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.api.entity.Project;
import com.qulix.yurkevichvv.trainingtask.api.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TasksListPage;

import java.sql.Connection;


/**
 * Ссылка для удаления сущности.
 *
 * @author Q-YVV
 */
public class DeleteLink<T extends Entity> extends Link<T> {

    /**
     * Элемент ListView.
     */
    private T item;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param item элемент ListView
     */
    public DeleteLink(String id, T item) {
        super(id);
        this.item = item;
    }

    @Override
    public void onClick() {
        Connection connection = ConnectionController.getConnection();
        try {
            item.getDao().delete(item.getId(), ConnectionController.getConnection());
        }
        finally {
            ConnectionController.closeConnection(connection);
        }
    }
}
