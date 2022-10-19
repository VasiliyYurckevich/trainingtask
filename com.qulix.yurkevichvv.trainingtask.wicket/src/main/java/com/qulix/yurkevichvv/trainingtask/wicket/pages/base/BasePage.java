package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;

/**
 * Шаблонная страница приложения.
 *
 * @author Q-YVV
 */
public class BasePage extends WebPage {

    /**
     * Конструктор.
     */
    public BasePage() {
        this(new PageParameters());
    }

    /**
     * Конструктор.
     *
     * @param parameters Обернутые параметры строки запроса
     */
    public BasePage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        WebMarkupContainer css = new WebMarkupContainer("style");
        add(css);

        Header header = new Header("header");
        add(header);

        add(new Label("pageTitle", ""));
    }

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
