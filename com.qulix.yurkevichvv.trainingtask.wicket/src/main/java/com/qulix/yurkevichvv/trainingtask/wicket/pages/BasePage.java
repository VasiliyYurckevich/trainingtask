package com.qulix.yurkevichvv.trainingtask.wicket.pages;

import com.qulix.yurkevichvv.trainingtask.wicket.panel.Header;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {

    public BasePage() {
        this(new PageParameters());
    }

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