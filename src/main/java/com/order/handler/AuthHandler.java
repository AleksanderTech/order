package com.order.handler;

import com.order.model.User;
import com.order.service.AuthService;
import com.order.validator.Validators;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.SignInVM;
import com.order.view.model.SignUpVM;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class AuthHandler extends Handler {

    private final Presenter presenter;
    private final AuthService authService;

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_CONFIRM_PASSWORD = "confirm-password";

    public AuthHandler(Presenter presenter, AuthService authService) {
        this.presenter = presenter;
        this.authService = authService;
    }

    @Override
    public void register(Javalin lin) {

        get("/sign-in", lin, ctx -> {
            ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(new HashMap<>())));
        });

        get("/sign-up", lin, ctx -> {
            ctx.html(presenter.template(Views.SIGN_UP, new SignUpVM(new HashMap<>())));
        });

        lin.post("/sign-in", ctx -> {
            String username = ctx.formParam(PARAMETER_USERNAME);
            String password = ctx.formParam(PARAMETER_PASSWORD);
            Map<String, String> errors = Validators.signIn(username, password);
            if (!errors.isEmpty()) {
                ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(errors)));
            } else {
                var user = new User(username, password);
                user = authService.signIn(user);
                newSession(ctx, user.id);
                ctx.res.sendRedirect("/thoughts");
            }
        });

        lin.post("/sign-up", ctx -> {
            String username = ctx.formParam(PARAMETER_USERNAME);
            String email = ctx.formParam(PARAMETER_EMAIL);
            String password = ctx.formParam(PARAMETER_PASSWORD);
            String confirmPassword = ctx.formParam(PARAMETER_CONFIRM_PASSWORD);
            Map<String, String> errors = Validators.signUp(username, email, password, confirmPassword);
            if (!errors.isEmpty()) {
                ctx.html(presenter.template(Views.SIGN_UP, new SignUpVM(errors)));
            } else {
                var user = new User(username, email, password);
                authService.signUp(user);
                ctx.res.sendRedirect("/");
            }
        });

        lin.post("/sign-out", ctx -> {
            invalidateSession(ctx);
            ctx.res.sendRedirect("/");
        });
    }
}
