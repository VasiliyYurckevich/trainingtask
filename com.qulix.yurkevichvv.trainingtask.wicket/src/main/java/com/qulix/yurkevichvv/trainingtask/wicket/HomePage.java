package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;

public class HomePage extends WebPage {

    public HomePage()
    {
        super();
        init();
    }

    private void init(){
        Header headerPanel = new Header("header");
        add(headerPanel);
        add(new Label("msg"));
        Form<?> form = new Form("form");
        form.add(new TextField<>("msgInput"));
        add(form);
    }

}