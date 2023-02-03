package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

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
     * Выполняет отправку формы.
     */
    protected abstract void onSubmitting();

    /**
     * Выполнят переадресацию страницу после отправки формы или отмены редактирования.
     */
    protected abstract void onChangesSubmitted();
}
