package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.Header;


/**
 * Шаблоная страница приложения.
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
        add(new Label("pageTitle", ""));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Header header = new Header("header");
        add(header);
    }

}
