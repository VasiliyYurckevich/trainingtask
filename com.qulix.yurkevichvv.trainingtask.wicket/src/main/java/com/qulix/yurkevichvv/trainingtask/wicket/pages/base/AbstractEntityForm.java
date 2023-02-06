package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

/**
 * Форма редактирования сущности.
 *
 * @param <T> сущность {@link Entity}
 * @author Q-YVV
 */
public abstract class AbstractEntityForm<T extends Entity> extends Form<T> {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     * @param entityModel модель сущности {@link Entity}
     */
    public AbstractEntityForm(String id, CompoundPropertyModel<T> entityModel) {
        super(id, entityModel);
    }

    /**
     * Выполняет действия при валидной отправке формы.
     */
    public abstract void onSubmitting();

    /**
     * Выполнят переадресацию страницу после отправки формы или отмены редактирования.
     */
    public abstract void onChangesSubmitted();
}
