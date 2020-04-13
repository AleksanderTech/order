package com.order;

import io.javalin.Javalin;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

import static com.order.database.jooq.Tables.ORDER_USER;


public class App {

    private final TemplateEngine templateEngine = new TemplateEngine();

    public static void main(String[] args) throws SQLException {
        var app = new App();
        var properties = loadProperties(args);
        app.configure();
        var lin = Javalin.create().start(7000);
        lin.get("/", ctx -> {
            ctx.header("Content-Type", "text/html");
            ctx.result(app.process());
        });
        testDbConnection();
    }

    private static void testDbConnection() {
        String user = "order";
        String password = "order";
        String url = "jdbc:postgresql://localhost:5432/order";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(ORDER_USER).fetch();
            for (Record r : result) {
                Long id = r.getValue(ORDER_USER.ID);
                String name = r.getValue(ORDER_USER.NAME);
                String email = r.getValue(ORDER_USER.EMAIL);
                System.out.println("ID: " + id + " name: " + name + " email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DSLContext create(String jdbcUrl, String username, String password, SQLDialect dialect) {
        var configuration = new DefaultConfiguration().set(dialect);
        return DSL.using(configuration);
    }

    public static DSLContext create(String jdbcUrl, String username, String password) {
        return create(jdbcUrl, username, password, SQLDialect.POSTGRES);
    }

    private static Properties loadProperties(String... args) {
        return Config.properties(args);
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
