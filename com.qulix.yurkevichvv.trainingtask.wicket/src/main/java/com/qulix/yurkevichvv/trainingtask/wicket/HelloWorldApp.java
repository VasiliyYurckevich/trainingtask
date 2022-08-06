package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class    HelloWorldApp extends WebApplication {

    public HelloWorldApp() {
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    protected void init() {
        super.init();
    }
}


