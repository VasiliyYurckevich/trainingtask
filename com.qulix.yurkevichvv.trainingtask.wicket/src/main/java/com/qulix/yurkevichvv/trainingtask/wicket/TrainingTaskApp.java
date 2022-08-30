package com.qulix.yurkevichvv.trainingtask.wicket;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.HomePage;
import org.apache.wicket.Localizer;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

/**
 * Базовый класс приложения
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
        getStoreSettings().setMaxSizePerSession(Bytes.megabytes(25));
    }
}


