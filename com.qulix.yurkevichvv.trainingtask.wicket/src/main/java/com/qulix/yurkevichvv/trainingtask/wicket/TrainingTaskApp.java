package com.qulix.yurkevichvv.trainingtask.wicket;

import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.HomePage;

/**
 * Базовый класс приложения.
 *
 * @author Q-YVV
 */
public class    TrainingTaskApp extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking().disabled();
    }

    @Override
    public Session newSession(Request request, Response response) {
        return super.newSession(request, response).setLocale(new Locale("ru"));
    }
}


