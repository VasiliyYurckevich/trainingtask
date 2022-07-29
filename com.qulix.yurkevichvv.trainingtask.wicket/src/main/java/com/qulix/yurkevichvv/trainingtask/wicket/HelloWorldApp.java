package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.protocol.http.WebApplication;

public class HelloWorldApp extends WebApplication {

    public HelloWorldApp() {
    }

    @Override
    public Class getHomePage() {
        return HelloWicket.class;
    }

    @Override
    public void init() {
        super.init();
    }
}
