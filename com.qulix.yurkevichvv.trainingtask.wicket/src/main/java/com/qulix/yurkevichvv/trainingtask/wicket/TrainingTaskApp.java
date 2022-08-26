package com.qulix.yurkevichvv.trainingtask.wicket;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.RequestCycleSettings;

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
        getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);

    }
}


