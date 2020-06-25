package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.HomeVM;
import io.javalin.Javalin;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HomeHandler extends Handler {

    private final Presenter presenter;

    public HomeHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        get("/home", lin, context -> {
            Long userId = null;
            String userIdName = "userId";
            if (context.sessionAttribute(userIdName) != null) {
                userId = context.sessionAttribute(userIdName);
            }
            if (userId == null) {
                try {
                    context.res.sendRedirect("error");
                } catch (IOException e) {
                    throw new RuntimeException("redirection failed");
                }
            }
            String message = null;
            String sessionID = null;
            Cookie[] cookies = context.req.getCookies();
            Map<String, String> cookiesMap = new HashMap<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        sessionID = cookie.getValue();
                        cookiesMap.put("JSESSIONID", sessionID);
                    }
                }
            }
            context.header("Content-Type", "text/html");
            context.result(presenter.template(Views.THOUGHTS, new HomeVM(cookiesMap, userId)));
        });
    }
}
