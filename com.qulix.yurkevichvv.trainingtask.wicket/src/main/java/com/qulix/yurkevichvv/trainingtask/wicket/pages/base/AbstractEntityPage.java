package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

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
}
