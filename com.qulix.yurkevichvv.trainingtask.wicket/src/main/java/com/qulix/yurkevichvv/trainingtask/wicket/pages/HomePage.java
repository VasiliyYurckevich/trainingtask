package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;

/**
 *  Домашняя страница приложения.
 *
 * @author Q-YVV
 */
public class HomePage extends BasePage {

    /**
     * Конструктор.
     */
    public HomePage() {
        super();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject("Приложение для управления задачами");
    }
}
