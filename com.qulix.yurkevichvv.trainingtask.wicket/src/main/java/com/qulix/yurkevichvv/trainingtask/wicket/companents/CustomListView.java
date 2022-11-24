package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPageFactory;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;

/**
 * Обобщенный ListView для сущностей.
 *
 * @param <T> класс сущностей
 * @author Q-YVV
 */
public class CustomListView<T extends Entity> extends ListView<T> {

    /**
     * Сервис для работы с сущностями.
     */
    private final IService service;

    private final AbstractEntityPageFactory<T> pageFactory;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param model модель списка
     * @param service сервис для удаления элементов списка
     */
    public CustomListView(String id, IModel<? extends List<T>> model, IService service) {
        super(id, model);
        this.service = service;
        pageFactory = getPageFactory();
    }

    protected AbstractEntityPageFactory<T> getPageFactory() {
        return null;
    }

    @Override
    protected void populateItem(ListItem<T> item) {
        item.add(new EditLink("editLink", pageFactory, item.getModel()))
            .add(new DeleteLink<>("deleteLink", service, item.getModel()));
    }

    /**
     * Ссылка для удаления сущности.
     *
     * @author Q-YVV
     */
    private static class DeleteLink<T extends Entity> extends Link<T> {

        /**
         * Сервис для работы с сущностями.
         */
        private final IService service;

        /**
         * Элемент ListView.
         */
        private final IModel<T> entityModel;

        /**
         * Конструктор.
         *
         * @param id идентификатор
         * @param entity элемент ListView
         */
        public DeleteLink(String id, IService service, IModel<T> entityModel) {
            super(id);
            this.entityModel = entityModel;
            this.service = service;
        }

        @Override
        public void onClick() {
            service.delete(entityModel.getObject().getId());
        }
    }
}
