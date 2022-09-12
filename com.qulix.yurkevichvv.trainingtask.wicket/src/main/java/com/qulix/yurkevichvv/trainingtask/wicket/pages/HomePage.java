package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.wicket.companents.Header;
import org.apache.wicket.markup.html.basic.Label;

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
