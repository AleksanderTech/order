package com.order.handler;

import com.order.view.TemplatePresenter;
import com.order.view.Views;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WelcomeHandlerTest {

    private static final TemplatePresenter templatePresenter = Mockito.mock(TemplatePresenter.class);

    @Test
    void register_registerRoutes() {
        Javalin javalin = Mockito.mock(Javalin.class);
        WelcomeHandler welcomeHandler = new WelcomeHandler(templatePresenter);
        welcomeHandler.register(javalin);
        Mockito.verify(javalin).get(Mockito.eq(Routes.WELCOME_ROUTE), Mockito.any(io.javalin.http.Handler.class));
    }

    @Test
    void welcome_passesWelcomeView() {
        Context context = Mockito.mock(Context.class);
        WelcomeHandler welcomeHandler = new WelcomeHandler(templatePresenter);
        Mockito.when(templatePresenter.template(Mockito.eq(Views.WELCOME))).thenReturn("welcome");
        welcomeHandler.welcome(context);
        Mockito.verify(context).html("welcome");
    }
}