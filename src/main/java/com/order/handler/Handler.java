package com.order.handler;

import io.javalin.Javalin;
import io.javalin.http.Context;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public abstract class Handler {

    private static final int SESSION_INACTIVE_INTERVAL = 60 * 60 * 12;
    private static final String USER_ID = "userId";

    public abstract void register(Javalin lin);

    public HttpSession invalidateSession(Context ctx) {
        HttpSession session = ctx.req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return session;
    }

    public Optional<Long> userId(Context context) {
        return Optional.ofNullable(context.sessionAttribute("userId"));
    }

    public void newSession(Context ctx, long userId) {
        HttpSession oldHttpSession = ctx.req.getSession(false);
        if (oldHttpSession != null) {
            oldHttpSession.invalidate();
        }
        HttpSession newSession = ctx.req.getSession(true);
        newSession.setMaxInactiveInterval(SESSION_INACTIVE_INTERVAL);
        newSession.setAttribute(USER_ID, userId);
    }

    protected void redirect(Context ctx, String route) {
        try {
            ctx.res.sendRedirect(route);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
