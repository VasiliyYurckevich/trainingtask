package com.qulix.yurkevichvv.trainingtask.wicket.pages;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;

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

    /**
     * Добавляет поле и фидбек панель.
     *
     * @param form форма для добавления
     * @param name имя поля
     * @param maxLength максимальная длинна ввода
     */
    protected static void addStringField(Form form, String name, Integer maxLength) {
        RequiredTextField<String> field = new RequiredTextField<>(name);
        field.add(new CustomStringValidator(maxLength));
        form.add(field);

        CustomFeedbackPanel fieldFeedbackPanel = new CustomFeedbackPanel(name + "FeedbackPanel",
            new ComponentFeedbackMessageFilter(field));
        form.add(fieldFeedbackPanel);
    }

}
