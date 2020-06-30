package com.order.handler;

import com.order.model.User;
import com.order.service.AuthService;
import com.order.validators.ValidatorManager;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.SignUpVM;
import com.order.view.model.ViewModel;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public class AuthHandler extends Handler {

    private final Presenter presenter;
    private final AuthService authService;
    private final ValidatorManager validatorManager;
    private final String PARAMETER_USERNAME = "username";
    private final String PARAMETER_EMAIL = "email";
    private final String PARAMETER_PASSWORD = "password";
    private final String PARAMETER_CONFIRM_PASSWORD = "confirm-password";

    public AuthHandler(Presenter presenter, AuthService authService, ValidatorManager validatorManager) {
        this.presenter = presenter;
        this.authService = authService;
        this.validatorManager = validatorManager;
    }

    @Override
    public void register(Javalin lin) {
        get("/sign-in", lin, ctx -> {
            ctx.html(presenter.template(Views.SIGN_IN));
        });
        get("/sign-up", lin, ctx -> {
            ViewModel<SignUpVM> model = new SignUpVM(new ArrayList<>());
            ctx.html(presenter.template(Views.SIGN_UP, model));
        });

        lin.post("/sign-in", ctx -> {
            Map<String, String> errors = validatorManager.validateUser(ctx.formParam(PARAMETER_USERNAME), ctx.formParam(PARAMETER_PASSWORD));
            // field, error message

            var user = new User(ctx.formParam(PARAMETER_USERNAME), ctx.formParam(PARAMETER_PASSWORD));

            user = authService.signIn(user);
            HttpSession oldHttpSession = ctx.req.getSession(false);
            if (oldHttpSession != null) {
                oldHttpSession.invalidate();
            }
            HttpSession newSession = ctx.req.getSession(true);
            newSession.setMaxInactiveInterval(60 * 60 * 12);
            newSession.setAttribute("userId", user.id);
            ctx.res.sendRedirect("home");
        });

        lin.post("/sign-up", ctx -> {
            String username = ctx.formParam(PARAMETER_USERNAME);
            String email = ctx.formParam(PARAMETER_EMAIL);
            String password = ctx.formParam(PARAMETER_PASSWORD);
            String confirmPassword = ctx.formParam(PARAMETER_CONFIRM_PASSWORD);
//            Validator userValidator = new UserValidator();
//            List<String> errors = userValidator.validate(username, email, password, confirmPassword);
//            ViewModel<SignUpVM> model = new SignUpVM(errors);
//            if (errors.size() > 0) {
//                ctx.html(presenter.template(Views.SIGN_UP, model));
//            } else {
            authService.signUp(new User(username, email, password, false));
            ctx.html(presenter.template(Views.SIGN_IN));
//            }
        });
        lin.post("/sign-out", ctx -> {
            HttpSession session = ctx.req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            ctx.res.sendRedirect("/");
        });
    }
}
