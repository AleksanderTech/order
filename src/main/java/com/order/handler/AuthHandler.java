package com.order.handler;

import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.error.OrderException;
import com.order.model.User;
import com.order.service.AuthService;
import com.order.service.Response;
import com.order.validator.Validators;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.ErrorVM;
import com.order.view.model.SignInVM;
import com.order.view.model.SignUpVM;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
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
        lin.get(Routes.SIGN_IN_ROUTE, this::signInGet);
        lin.get(Routes.SIGN_UP_ROUTE, this::signUpGet);
        lin.post(Routes.SIGN_IN_ROUTE, this::signInPost);
        lin.post(Routes.SIGN_UP_ROUTE, this::signUpPost);
        lin.post(Routes.SIGN_OUT_ROUTE, this::signOutPost);
    }

    public void signInGet(Context ctx) {
        ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(new HashMap<>())));
    }

    public void signUpGet(Context ctx) {
        ctx.html(presenter.template(Views.SIGN_UP, new SignUpVM(new HashMap<>())));
    }

    public void signInPost(Context ctx) {
        String username = ctx.formParam(PARAMETER_USERNAME);
        String password = ctx.formParam(PARAMETER_PASSWORD);
        Map<String, String> errors = Validators.signIn(username, password);
        ifOrElse(!errors.isEmpty(),
                () ->
                        ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(errors))),
                () -> {
                    var user = new User(username, password);
                    Response<User> userResponse = authService.signIn(user);
                    user = userResponse.getValue();
                    if (user == null) {
                        ctx.html(presenter.template(Views.ERROR, new ErrorVM(HttpStatus.NOT_FOUND.getStatusCode(), userResponse.getErrors())));
                        return;
                    }
                    newSession(ctx, user.id);
                    redirect(ctx, Views.THOUGHTS);
                });
    }

    public void signUpPost(Context ctx) {
        String username = ctx.formParam(PARAMETER_USERNAME);
        String email = ctx.formParam(PARAMETER_EMAIL);
        String password = ctx.formParam(PARAMETER_PASSWORD);
        String confirmPassword = ctx.formParam(PARAMETER_CONFIRM_PASSWORD);
        Map<String, String> errors = Validators.signUp(username, email, password, confirmPassword);
        ifOrElse(!errors.isEmpty(),
                () ->
                        ctx.html(presenter.template(Views.SIGN_UP, new SignUpVM(errors))),
                () -> {
                    var user = new User(username, email, password);
                    authService.signUp(user);
                    redirect(ctx, Views.THOUGHTS);
                });
    }

    public void signOutPost(Context ctx) {
        invalidateSession(ctx);
        try {
            ctx.res.sendRedirect(Routes.WELCOME_ROUTE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
