package com.order.handler.handlers;

import com.order.error.Errors;
import com.order.error.HttpStatus;
import com.order.entity.User;
import com.order.handler.Handler;
import com.order.handler.Message;
import com.order.handler.Routes;
import com.order.service.AuthService;
import com.order.service.Response;
import com.order.validator.Validators;
import com.order.view.Presenter;
import com.order.view.Views;
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
        lin.get(Routes.SIGN_UP_SUCCESS_ROUTE, this::signUpSuccess);
        lin.post(Routes.SIGN_IN_ROUTE, this::signInPost);
        lin.post(Routes.SIGN_UP_ROUTE, this::signUpPost);
        lin.post(Routes.SIGN_OUT_ROUTE, this::signOutPost);
    }

    private void signUpSuccess(Context ctx) {
        ctx.html(presenter.template(Views.SIGN_UP, SignUpVM.withMessages(Map.of(Message.ACCOUNT_CREATED.name(), Message.ACCOUNT_CREATED.getMessage()))));
    }

    public void signInGet(Context ctx) {
        ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(new HashMap<>())));
    }

    public void signUpGet(Context ctx) {
        ctx.html(presenter.template(Views.SIGN_UP, SignUpVM.emptyInstance()));
    }

    public void signInPost(Context ctx) {
        String email = ctx.formParam(PARAMETER_EMAIL);
        String password = ctx.formParam(PARAMETER_PASSWORD);
        Map<String, String> errors = Validators.signIn(email, password);
        signIn(email, password, errors, ctx);
    }

    public void signUpPost(Context ctx) {
        String username = ctx.formParam(PARAMETER_USERNAME);
        String email = ctx.formParam(PARAMETER_EMAIL);
        String password = ctx.formParam(PARAMETER_PASSWORD);
        String confirmPassword = ctx.formParam(PARAMETER_CONFIRM_PASSWORD);
        Map<String, String> errors = Validators.signUp(username, email, password, confirmPassword);
        signUp(username, email, password, errors, ctx);
    }

    public void signOutPost(Context ctx) {
        invalidateSession(ctx);
        try {
            ctx.res.sendRedirect(Routes.WELCOME_ROUTE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void signIn(String email, String password, Map<String, String> errors, Context ctx) {
        if (!errors.isEmpty()) {
            ctx.status(HttpStatus.BAD_REQUEST.getStatusCode());
            ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(errors)));
        } else {
            var user = new User(email, password);
            Response<User> userResponse = authService.signIn(user);
            resolveSignInResponse(ctx, errors, userResponse);
        }
    }

    public void signUp(String username, String email, String password, Map<String, String> errors, Context ctx) {
        if (!errors.isEmpty()) {
            ctx.status(HttpStatus.BAD_REQUEST.getStatusCode());
            ctx.html(presenter.template(Views.SIGN_UP, SignUpVM.withErrors(errors)));
        } else {
            var user = new User(username, email, password);
            Response<Void> response = authService.signUp(user);
            resolveSignUpResponse(response, ctx, errors);
        }
    }

    public void resolveSignInResponse(Context ctx, Map<String, String> errors, Response<User> response) {
        if (response.hasErrors()) {
            if (response.getErrors().contains(Errors.USER_NOT_FOUND)) {
                errors.put(Validators.EMAIL, Errors.USER_NOT_FOUND);
            }
            if (response.getErrors().contains(Errors.INCORRECT_PASSWORD)) {
                errors.put(Validators.PASSWORD, Errors.INCORRECT_PASSWORD);
            }
            ctx.status(response.getHttpStatus().getStatusCode());
            ctx.html(presenter.template(Views.SIGN_IN, new SignInVM(errors)));
        } else {
            newSession(ctx, response.getValue().id);
            redirect(ctx, Views.THOUGHTS);
        }
    }

    public void resolveSignUpResponse(Response<Void> response, Context ctx, Map<String, String> errors) {
        if (response.hasErrors()) {
            if (response.getErrors().contains(Errors.EMAIL_EXISTS)) {
                errors.put(Validators.EMAIL, Errors.EMAIL_EXISTS);
            }
            ctx.html(presenter.template(Views.SIGN_UP, SignUpVM.withErrors(errors)));
        } else {
            ctx.status(response.getHttpStatus().getStatusCode());
            redirect(ctx, Views.SIGN_UP_SUCCESS);
        }
    }
}
