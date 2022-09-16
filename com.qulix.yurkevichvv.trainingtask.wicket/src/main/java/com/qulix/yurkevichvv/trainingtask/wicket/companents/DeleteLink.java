package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.sql.Connection;

import org.apache.wicket.markup.html.link.Link;

import com.qulix.yurkevichvv.trainingtask.api.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.api.entity.Entity;



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
