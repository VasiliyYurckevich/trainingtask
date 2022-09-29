package com.qulix.yurkevichvv.trainingtask.wicket.companents;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;

/**
 * Обобщенный ListView для сущностей.
 *
 * @param <T> класс сущностей
 * @author Q-YVV
 */
public class CustomListView<T extends Entity>  extends ListView<T> {

    /**
     * Сервис для работы с сущностями.
     */
    private final IService  service;

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
    }



    @Override
    protected void populateItem(ListItem<T> item) {

        AbstractEntityPage page = getNewPage(item);

        item.add(new EditLink("editLink", page))
            .add(new DeleteLink("deleteLink", service, item.getModelObject()));
    }

    /**
     * Определяет страницу редактирования сущности для перенаправления EditLink.
     *
     * @param item элемент списка
     * @return страница редактирования сущности
     */
    public AbstractEntityPage getNewPage(ListItem<T> item) {
        return null;
    }
}
