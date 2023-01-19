package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;

/**
 * Абстрактный класс страниц списков сущностей.
 *
 * @param <T> класс сущности
 * @author Q-YVV
 */
public abstract class AbstractListPage<T extends Entity> extends BasePage {

    /**
     * Фабрика для создания страниц.
     */
    protected AbstractEntityPageFactory<T> pageFactory;

    /**
     * Сервис для работы с сущностями.
     */
    protected IService<T> service;

    /**
     * Конструктор.
     *
     * @param pageFactory фабрика для создания страниц
     * @param service     сервис для работы с сущностями
     */
    public AbstractListPage(final String pageTitle, AbstractEntityPageFactory<T> pageFactory, IService service) {
        super(pageTitle);
        this.pageFactory = pageFactory;
        this.service = service;
    }
}
