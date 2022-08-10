package com.qulix.yurkevichvv.trainingtask.wicket;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TrainingTaskApp extends WebApplication {

    public TrainingTaskApp() {
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking().disabled();
    }
}


