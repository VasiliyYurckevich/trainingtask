package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Шаблонная страница приложения.
 *
 * @author Q-YVV
 */
public class BasePage extends WebPage {

    private final String pageTitle;

    /**
     * Конструктор.
     */
    public BasePage(String pageTitle) {
        this(new PageParameters(), pageTitle);
    }

    /**
     * Конструктор.
     *
     * @param parameters Обернутые параметры строки запроса
     */
    public BasePage(final PageParameters parameters, final String pageTitle) {
        super(parameters);
        this.pageTitle = pageTitle;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        WebMarkupContainer css = new WebMarkupContainer("style");
        add(css);

        Header header = new Header("header");
        add(header);

        add(new Label("pageTitle", pageTitle));
    }
}
