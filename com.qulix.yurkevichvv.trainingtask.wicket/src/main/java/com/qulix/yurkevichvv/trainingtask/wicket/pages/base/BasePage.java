package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Шаблонная страница приложения.
 *
 * @author Q-YVV
 */
public class BasePage extends WebPage {

    /**
     * Заголовок страницы.
     */
    private final String pageTitle;

    /**
     * Конструктор.
     *
     * @param pageTitle заголовок страницы
     */
    public BasePage(String pageTitle) {
        super();
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
