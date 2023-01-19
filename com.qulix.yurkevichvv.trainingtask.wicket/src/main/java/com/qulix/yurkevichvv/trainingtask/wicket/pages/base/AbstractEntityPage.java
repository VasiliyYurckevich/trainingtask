package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;


import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;

/**
 * Обобщает страницу редактирования сущностей.
 *
 * @author Q-YVV
 */
public abstract class AbstractEntityPage<T extends Entity> extends BasePage {

    /**
     * Модель сущности.
     */
    protected CompoundPropertyModel<T> entityModel;

    /**
     * Конструктор.
     *
     * @param entityModel модель сущности.
     */
    protected AbstractEntityPage(final String pageTitle, CompoundPropertyModel<T> entityModel) {
        super(pageTitle);
        this.entityModel = entityModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    /**
     * Выполняет отправку формы.
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
    protected abstract void addFormComponents(Form<T> form);

    /**
     * Добавляет поле и фидбек панель.
     *
     * @param form      форма для добавления
     * @param name      имя поля
     * @param maxLength максимальная длинна ввода
     */
    protected void addStringField(Form<T> form, String name, Integer maxLength) {
        RequiredTextField<String> field = new RequiredTextField<>(name);
        field.add(new CustomStringValidator(maxLength));
        form.add(field);

        FeedbackPanel fieldFeedbackPanel = new FeedbackPanel(name + "FeedbackPanel",
            new ComponentFeedbackMessageFilter(field));
        form.add(fieldFeedbackPanel);
    }

    /**
     * Добавляет кнопки.
     *
     * @param form форма для добавления
     */
    protected void addButtons(Form<T> form) {
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button)
                .add(new PreventSubmitOnEnterBehavior());
        form.setDefaultButton(button);

        Link<Void> cancelButton = new CancelLink("cancel");
        form.add(cancelButton);
    }

    /**
     * Кнопка отмены сохранения.
     *
     * @author Q-YVV
     */
    private class CancelLink extends Link<Void> {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         */
        public CancelLink(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            onChangesSubmitted();
        }
    }
}
