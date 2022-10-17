package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import org.apache.wicket.markup.html.form.Form;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;

/**
 * Обобщает страницу редактирования сущностей.
 *
 * @author Q-YVV
 */
public abstract class AbstractEntityPage extends BasePage {

    /**
     * Конструктор.
     */
    public AbstractEntityPage() {
        super();
    }

    /**
     * Выполнят отправку формы.
     */
    protected abstract void onSubmitting();

    /**
     * Выполнят переадресацию страницу после отправки формы или отмены редактирования.
     */
    protected abstract void onChangesSubmitted();

    /**
     * Добавляет компоненты в форму.
     *
     * @param form форма для добавления
     */
    protected abstract void addFormComponents(Form form);
}
