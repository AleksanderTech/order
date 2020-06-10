package com.order;


import com.order.config.AppProperties;
import com.order.config.PropertiesLoader;
import com.order.config.ThymeleafConfig;
import com.order.handler.AuthHandler;
import com.order.handler.Handler;
import com.order.handler.HomeHandler;
import com.order.handler.WelcomeHandler;
import com.order.repository.SqlUserRepository;
import com.order.service.AuthService;
import com.order.service.Hasher;
import com.order.view.TemplatePresenter;
import io.javalin.Javalin;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.FileSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.File;
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
            config.sessionHandler(App::fileSessionHandler);
            config.addStaticFiles(IMAGES_PATH);
            config.addStaticFiles(STYLE_PATH);
            config.addStaticFiles(JS_PATH);
        });
        List<Handler> handlers = handlers(appProps);
        handlers.forEach(h -> h.register(lin));

        lin.start(appProps.SERVER_PORT);
    }

    private static SessionHandler fileSessionHandler() {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(fileSessionDataStore());
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.getSessionCookieConfig().setHttpOnly(true);
        return sessionHandler;
    }

    private static FileSessionDataStore fileSessionDataStore() {
        System.out.println(System.getProperty("java.io.tmpdir"));
        FileSessionDataStore fileSessionDataStore = new FileSessionDataStore();
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        File storeDir = new File(baseDir, "javalin-session-store");
        storeDir.mkdir();
        fileSessionDataStore.setStoreDir(storeDir);
        return fileSessionDataStore;
    }

    private static List<Handler> handlers(AppProperties appProperties) {
        var dslContext = loadDbContext(appProperties);
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        var hasher = new Hasher();
        var authRepository = new SqlUserRepository(dslContext);
        var authService = new AuthService(authRepository, hasher);
        var startHandler = new WelcomeHandler(presenter);
        var authHandler = new AuthHandler(presenter, authService);
        var homeHandler = new HomeHandler(presenter);
        return List.of(startHandler, authHandler, homeHandler);
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
