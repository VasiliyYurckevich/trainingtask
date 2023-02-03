package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPageFactory;

/**
 * Обобщенный {@link ListView} для сущностей.
 *
 * @param <T> класс сущностей
 * @author Q-YVV
 */
public class CustomListView<T extends Entity> extends ListView<T> {

    /**
     * Сервис для работы с сущностями.
     */
    private final IService<T> service;

    /**
     * Фабрика для генерации страниц сущностей.
     */
    private final AbstractEntityPageFactory<T> pageFactory;

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param model {@link LoadableDetachableModel} списка сущностей
     * @param service сервис для удаления элементов списка
     */
    public CustomListView(String id, LoadableDetachableModel<? extends List<T>> model, IService<T> service) {
        super(id, model);
        this.service = service;
        this.pageFactory = getPageFactory();
    }

    /**
     * Создает фабрику страниц сущностей.
     *
     * @return фабрика страниц.
     */
    protected AbstractEntityPageFactory<T> getPageFactory() {
        return null;
    }

    @Override
    protected void populateItem(ListItem<T> item) {
        item.add(new EditLink<>("editLink", pageFactory, item.getModel()))
            .add(new DeleteLink<>("deleteLink", service, item.getModel()));
    }

    /**
     * Ссылка для удаления сущности.
     *
     * @author Q-YVV
     */
    private class DeleteLink<E extends Entity> extends Link<E> {

        /**
         * Сервис для работы с сущностями.
         */
        private final IService<E> service;

        /**
         * Элемент ListView.
         */
        private final IModel<E> entityModel;

        /**
         * Конструктор.
         *
         * @param id идентификатор
         * @param entityModel элемент {@link ListView}
         */
        public DeleteLink(String id, IService<E> service, IModel<E> entityModel) {
            super(id);
            this.entityModel = entityModel;
            this.service = service;
        }

        @Override
        public void onClick() {
            service.delete(entityModel.getObject().getId());
            CustomListView.this.getModel().detach();
        }
    }

    /**
     * Ссылка для редактирования сущности.
     *
     * @author Q-YVV
     */
    public static class EditLink<T extends Entity> extends Link<T> {

        /**
         * Модель сущности.
         */
        private final IModel<T> entityModel;

        /**
         * Фабрика для генерации страниц сущностей.
         */
        private final AbstractEntityPageFactory<T> pageFactory;

        /**
         * Конструктор.
         *
         * @param id идентификатор
         * @param pageFactory фабрика страниц
         * @param entityModel модель
         */
        public EditLink(String id, AbstractEntityPageFactory<T> pageFactory, IModel<T> entityModel) {
            super(id);
            this.entityModel = entityModel;
            this.pageFactory = pageFactory;
        }

        @Override
        public void onClick() {
            setResponsePage(pageFactory.createPage(CompoundPropertyModel.of(entityModel)));
        }
    }
}
