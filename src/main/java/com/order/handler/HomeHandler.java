package com.order.handler;

import io.javalin.Javalin;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

public class HomeHandler {

    private ITemplateEngine templateEngine;

    public HomeHandler(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void register(Javalin lin) {
        Context context = new Context();
        lin.get("/", ctx -> {
            ctx.header("Content-Type", "text/html");
            context.setVariable("name", "foo");
            context.setVariable("date", LocalDateTime.now().toString());
            ctx.result(templateEngine.process("greeting.html", context));
        });
    }
}
