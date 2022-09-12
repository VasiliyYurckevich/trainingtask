package com.qulix.yurkevichvv.trainingtask.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.settings.RequestCycleSettings;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.HomePage;

import javax.servlet.http.Cookie;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

/**
 * Базовый класс приложения.
 *
 * @author Q-YVV
 */
public class TrainingTaskApp extends WebApplication {

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
    @Override
    public Session newSession(Request request, Response response)
    {
        return super.newSession(request, response).setLocale(new Locale("ru"));
    }
}


