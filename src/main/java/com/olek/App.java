package com.olek;

import io.javalin.Javalin;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.LocalDateTime;


public class App {

    private final TemplateEngine templateEngine = new TemplateEngine();

    public static void main(String[] args) {
        App app = new App();
        app.configure();
        Javalin lin = Javalin.create().start(7000);
        lin.get("/", ctx -> {
            ctx.header("Content-Type", "text/html");
            ctx.result(app.process());
        });
    }

    public String process() {
        Context ct = new Context();
        ct.setVariable("name", "foo");
        ct.setVariable("date", LocalDateTime.now().toString());
        return templateEngine.process("greeting.html", ct);
    }

    private void configure() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);
    }
}
