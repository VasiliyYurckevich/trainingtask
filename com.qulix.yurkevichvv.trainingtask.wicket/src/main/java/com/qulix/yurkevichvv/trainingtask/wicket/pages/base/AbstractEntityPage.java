package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.wicket.TokenHandler;
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
    private final CompoundPropertyModel<T> entityModel;

    /**
     * Форма страницы.
     */
    private final AbstractEntityForm<T> form;

    /**
     * Конструктор.
     *
     * @param entityModel модель сущности
     * @param form форма страницы
     */
    protected AbstractEntityPage(final String pageTitle, CompoundPropertyModel<T> entityModel, AbstractEntityForm<T> form) {
        super(pageTitle);
        this.entityModel = entityModel;
        this.form = form;
    }

    /**
     * Добавляет компоненты в форму.
     */
    protected void addFormComponents() {
        add(form);
    }

    /**
     * Добавляет поле и фидбек панель.
     *
     * @param name имя поля
     * @param maxLength максимальная длинна ввода
     */
    protected void addStringField(String name, Integer maxLength) {
        RequiredTextField<String> field = new RequiredTextField<>(name);
        field.add(new CustomStringValidator(maxLength));
        form.add(field);

        FeedbackPanel fieldFeedbackPanel = new FeedbackPanel(name + "FeedbackPanel",
            new ComponentFeedbackMessageFilter(field));
        form.add(fieldFeedbackPanel);
    }

    /**
     * Добавляет кнопки.
     */
    protected void addButtons() {
        SubmitButton button = new SubmitButton("submit");
        form.add(button);
        form.setDefaultButton(button);

        Button cancelButton = new CancelLink("cancel");
        form.add(cancelButton);
    }

    public CompoundPropertyModel<T> getEntityModel() {
        return entityModel;
    }

    public AbstractEntityForm<T> getForm() {
        return form;
    }

    /**
     * Кнопка отмены сохранения.
     *
     * @author Q-YVV
     */
    private class CancelLink extends Button {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         */
        public CancelLink(String id) {
            super(id);
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            setDefaultFormProcessing(false);
        }

        @Override
        public void onSubmit() {
            form.onChangesSubmitted();
        }
    }

    /**
     * Кнопка отправки формы, предотвращающая двойной щелчок.
     *
     * @author Q-YVV
     */
    private static class SubmitButton extends Button {

        /**
         * Конструктор.
         *
         * @param id идентификатор
         */
        public SubmitButton(String id) {
            super(id);
        }

        @Override
        public void onSubmit() {
            super.onSubmit();
            AbstractEntityForm<?> form = (AbstractEntityForm<?>) getForm();
            if (TokenHandler.isFirstSubmit(getPage().getPageParameters())) {
                form.onSubmitting();
            }
            form.onChangesSubmitted();
        }
    }
}
