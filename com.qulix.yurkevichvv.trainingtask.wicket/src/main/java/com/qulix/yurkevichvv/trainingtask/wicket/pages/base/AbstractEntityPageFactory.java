package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import java.io.Serializable;

import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

/**
 * Отвечает за генерацию страниц сущностей.
 *
 * @param <T> сущность
 * @author Q-YVV
 */
public interface AbstractEntityPageFactory<T extends Entity> extends Serializable {

    /**
     *  Создает страницу редактирования сущностей.
     *
     * @param entityModel модель сущности
     * @return страница редактирования сущности
     */
    AbstractEntityPage<T> createPage(CompoundPropertyModel<T> entityModel);
}
