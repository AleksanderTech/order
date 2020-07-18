package com.order.handler;

import com.order.handler.handlers.AuthHandler;
import com.order.service.AuthService;
import com.order.view.Presenter;
import io.javalin.Javalin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthHandlerTest {

    @Test
    void register_registerRoutes() {
        Javalin javalin = Mockito.mock(Javalin.class);
        Presenter presenter = Mockito.mock(Presenter.class);
        AuthService authService = Mockito.mock(AuthService.class);
        AuthHandler authHandler = new AuthHandler(presenter, authService);
        authHandler.register(javalin);

        Mockito.verify(javalin).get(Mockito.eq(Routes.SIGN_IN_ROUTE), Mockito.any(io.javalin.http.Handler.class));
        Mockito.verify(javalin).post(Mockito.eq(Routes.SIGN_IN_ROUTE), Mockito.any(io.javalin.http.Handler.class));
        Mockito.verify(javalin).get(Mockito.eq(Routes.SIGN_UP_ROUTE), Mockito.any(io.javalin.http.Handler.class));
        Mockito.verify(javalin).post(Mockito.eq(Routes.SIGN_UP_ROUTE), Mockito.any(io.javalin.http.Handler.class));
        Mockito.verify(javalin).post(Mockito.eq(Routes.SIGN_OUT_ROUTE), Mockito.any(io.javalin.http.Handler.class));
    }

    @Test
    void signInGet() {

    }

    @Test
    void signUpGet() {

    }

    @Test
    void errorPage() {

    }

    @Test
    void signOutPost() {

    }
}