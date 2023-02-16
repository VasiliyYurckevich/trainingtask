package com.qulix.yurkevichvv.trainingtask.wicket;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.HomePage;
import org.apache.wicket.settings.RequestCycleSettings;

/**
 * Базовый класс приложения.
 *
 * @author Q-YVV
 */
public class TrainingTaskApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        getCspSettings().blocking().add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF);
        getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.REDIRECT_TO_RENDER);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return super.newSession(request, response).setLocale(new Locale("ru", "RU"))
            .setAttribute("TOKEN_LIST", new ArrayList<String>());
    }
}


