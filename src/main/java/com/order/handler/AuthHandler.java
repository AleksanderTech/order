package com.order.handler;

import com.order.model.User;
import com.order.service.AuthService;
import com.order.validators.UserValidator;
import com.order.validators.Validator;
import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.SignUpModel;
import com.order.view.model.ViewModel;
import io.javalin.Javalin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AuthHandler implements Handler {

    private final Presenter presenter;
    private final AuthService authService;
    private final String PARAMETER_USERNAME = "username";
    private final String PARAMETER_EMAIL = "email";
    private final String PARAMETER_PASSWORD = "password";
    private final String PARAMETER_CONFIRM_PASSWORD = "confirm-password";

    public AuthHandler(Presenter presenter, AuthService authService) {
        this.presenter = presenter;
        this.authService = authService;
    }

    @Override
    public void register(Javalin lin) {

        lin.before(ctx -> {
            ctx.sessionAttribute("my-key", "My value");
            System.out.println(ctx.sessionAttributeMap());
            System.out.println((String) ctx.sessionAttribute("current-user"));
        });
        lin.get("/sign-in", ctx -> {
            ctx.html(presenter.template(Views.SIGN_IN));
        });
        lin.post("/sign-in", ctx -> {
            System.out.println(ctx.body());
            var user = new User(ctx.formParam(PARAMETER_USERNAME), ctx.formParam(PARAMETER_PASSWORD));
            authService.signIn(user);

            HttpSession oldHttpSession = ctx.req.getSession(false);
            if (oldHttpSession != null) {
                oldHttpSession.invalidate();
            }
            HttpSession newSession = ctx.req.getSession(true);
            newSession.setMaxInactiveInterval(5 * 60);
            Cookie message = new Cookie("message", "Welcome");
            ctx.res.addCookie(message);
            ctx.res.sendRedirect("home");

        });
        lin.get("/sign-up", ctx -> {
            ViewModel<SignUpModel> model = new SignUpModel(new ArrayList<>());
            ctx.html(presenter.template(Views.SIGN_UP, model));
        });
        lin.post("/sign-up", ctx -> {
            String username = ctx.formParam(PARAMETER_USERNAME);
            String email = ctx.formParam(PARAMETER_EMAIL);
            String password = ctx.formParam(PARAMETER_PASSWORD);
            String confirmPassword = ctx.formParam(PARAMETER_CONFIRM_PASSWORD);
            Validator userValidator = new UserValidator();
            List<String> errors = userValidator.validate(username, email, password, confirmPassword);
            ViewModel<SignUpModel> model = new SignUpModel(errors);
            if (errors.size() > 0) {
                ctx.html(presenter.template(Views.SIGN_UP, model));
            } else {
                authService.signUp(new User(username, email, password, false));
                ctx.html(presenter.template(Views.SIGN_IN));
            }
        });
    }
}
