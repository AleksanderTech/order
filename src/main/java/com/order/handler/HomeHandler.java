package com.order.handler;

import com.order.view.Presenter;
import com.order.view.Views;
import com.order.view.model.HomeModel;
import io.javalin.Javalin;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;


public class HomeHandler implements Handler {

    private final Presenter presenter;

    public HomeHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void register(Javalin lin) {
        lin.get("/home", ctx -> {
            Long userId = null;
            String userIdName = "userId";
            if (ctx.sessionAttribute(userIdName) != null) {
                userId = ctx.sessionAttribute(userIdName);
            }
            if (userId == null) {
                ctx.res.sendRedirect("error");
            }
            String message = null;
            String sessionID = null;
            Cookie[] cookies = ctx.req.getCookies();
            Map<String, String> cookiesMap = new HashMap<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        sessionID = cookie.getValue();
                        cookiesMap.put("JSESSIONID", sessionID);
                    }
                }
            }
            ctx.header("Content-Type", "text/html");
            ctx.result(presenter.template(Views.HOME, new HomeModel(cookiesMap, userId)));
        });
    }
}
