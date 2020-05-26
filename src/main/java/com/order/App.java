package com.order;

import com.order.config.AppProperties;
import com.order.config.PropertiesLoader;
import com.order.config.ThymeleafConfig;
import com.order.handler.AuthHandler;
import com.order.handler.Handler;
import com.order.handler.WelcomeHandler;
import com.order.view.TemplatePresenter;
import io.javalin.Javalin;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class App {

    public static final String IMAGES_PATH = "/images";
    public static final String STYLE_PATH = "/css";
    public static final String JS_PATH = "/js";

    public static void main(String[] args) {
        var appProps = new AppProperties(loadProperties(args));
        var dslContext = loadDbContext(appProps);
        var lin = Javalin.create(config -> {
            config.addStaticFiles(IMAGES_PATH);
            config.addStaticFiles(STYLE_PATH);
            config.addStaticFiles(JS_PATH);
        });
        List<Handler> handlers = handlers();
        handlers.forEach(h->h.register(lin));
        lin.start(appProps.SERVER_PORT);
    }

    private static List<Handler> handlers() {
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        var startHandler = new WelcomeHandler(presenter);
        var authHandler = new AuthHandler(presenter);
        return List.of(startHandler, authHandler);
    }

    private static Properties loadProperties(String... args) {
        return PropertiesLoader.load(args);
    }

    private static DSLContext loadDbContext(AppProperties props) {
        var user = props.JDBC_USER;
        var password = props.JDBC_USER;
        var url = props.JDBC_URL;
        try {
            var connection = DriverManager.getConnection(url, user, password);
            return DSL.using(connection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
