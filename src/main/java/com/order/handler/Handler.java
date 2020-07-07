package com.order.handler;

import com.order.model.User;
import io.javalin.Javalin;
import io.javalin.http.Context;

import javax.servlet.http.HttpSession;
import java.util.function.Consumer;

public abstract class Handler {

    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_TYPE = "Content-Type";
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

    public void newSession(Context ctx, long userId) {
        HttpSession oldHttpSession = ctx.req.getSession(false);
        if (oldHttpSession != null) {
            oldHttpSession.invalidate();
        }
        HttpSession newSession = ctx.req.getSession(true);
        newSession.setMaxInactiveInterval(SESSION_INACTIVE_INTERVAL);
        newSession.setAttribute(USER_ID, userId);
    }

    public void get(String path, Javalin javalin, Consumer<Context> consumer) {
        javalin.get(path, ctx -> {
            ctx.header(CONTENT_TYPE, TEXT_HTML);
            consumer.accept(ctx);
        });
    }

    public void setHeaders(Context ctx) {
        ctx.header(CONTENT_TYPE, TEXT_HTML);
    }

    public void post(String path, Javalin javalin, Consumer<Context> consumer) {
        javalin.post(path, consumer::accept);
    }

    public abstract void handle(Context ctx);
}
