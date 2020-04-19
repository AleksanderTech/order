package com.order;

import com.order.config.AppProperties;
import com.order.config.PropertiesLoader;
import com.order.config.ThymeleafConfig;
import com.order.handler.StartHandler;
import com.order.view.TemplatePresenter;
import io.javalin.Javalin;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class App {

    public static final String IMAGES_PATH = "/images";
    public static final String STYLE_PATH = "/style";
    public static final String JS_PATH = "/js";

    public static void main(String[] args) {
        var appProps = new AppProperties(loadProperties(args));
        var dslContext = loadDbContext(appProps);
        var lin = Javalin.create(config -> {
            config.addStaticFiles(IMAGES_PATH);
            config.addStaticFiles(STYLE_PATH);
            config.addStaticFiles(JS_PATH);
        });
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        var startHandler = new StartHandler(presenter);
        startHandler.register(lin);
        lin.start(appProps.SERVER_PORT);
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
