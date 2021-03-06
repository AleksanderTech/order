package com.order;


import com.order.config.AppProperties;
import com.order.config.PropertiesLoader;
import com.order.config.ThymeleafConfig;
import com.order.handler.*;
import com.order.handler.handlers.*;
import com.order.repository.SqlUserRepository;
import com.order.repository.TagRepository;
import com.order.repository.ThoughtRepository;
import com.order.repository.ThoughtsViewMetricsRepository;
import com.order.rest.handler.TagRestHandler;
import com.order.rest.handler.ThoughtRestHandler;
import com.order.service.AuthService;
import com.order.service.Hasher;
import com.order.service.TagService;
import com.order.service.ThoughtService;
import com.order.view.TemplatePresenter;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    public static final String IMAGES_PATH = "/images";
    public static final String STYLE_PATH = "/css";
    public static final String JS_PATH = "/js";
    private static final ScheduledExecutorService sessionWiper = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        var appProps = new AppProperties(loadProperties(args));
        var dslContext = loadDbContext(appProps);
        var sessionHandler = fileSessionHandler();
        var lin = Javalin.create(config -> {
            config.sessionHandler(() -> sessionHandler);
            config.addStaticFiles(IMAGES_PATH);
            config.addStaticFiles(STYLE_PATH);
            config.addStaticFiles(JS_PATH);
        });
        JavalinJackson.configure(JsonMapper.MAPPER);

        List<Handler> handlers = handlers(appProps);
        handlers.forEach(h -> h.register(lin));

        lin.start(appProps.SERVER_PORT);
        sessionWiper.scheduleAtFixedRate(sessionHandler::scavenge, 1, 8, TimeUnit.SECONDS);
    }

    private static SessionHandler fileSessionHandler() {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(fileSessionDataStore());
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.getSessionCookieConfig().setHttpOnly(true);
        sessionHandler.scavenge();
        return sessionHandler;
    }

    private static FileSessionDataStore fileSessionDataStore() {
        FileSessionDataStore fileSessionDataStore = new FileSessionDataStore();
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        File storeDir = new File(baseDir, "javalin-session-store");
        fileSessionDataStore.setStoreDir(storeDir);
        return fileSessionDataStore;
    }

    private static List<Handler> handlers(AppProperties appProperties) {
        var dslContext = loadDbContext(appProperties);
        var presenter = new TemplatePresenter(ThymeleafConfig.templateEngine());
        var hasher = new Hasher();
        var tagRepository = new TagRepository(dslContext);
        var thoughtsViewMetricsRepository = new ThoughtsViewMetricsRepository(dslContext);
        var tagService = new TagService(tagRepository);
        var authRepository = new SqlUserRepository(dslContext);
        var thoughtRepository = new ThoughtRepository(dslContext);
        var authService = new AuthService(authRepository, hasher);
        var thoughtService = new ThoughtService(thoughtRepository,thoughtsViewMetricsRepository);
        var startHandler = new WelcomeHandler(presenter);
        var authHandler = new AuthHandler(presenter, authService);
        var errorHandler = new ErrorHandler(presenter);
        var thoughtsHandler = new ThoughtsHandler(presenter, thoughtService);
        var searchHandler = new SearchHandler(presenter);
        var tagRestHandler = new TagRestHandler(tagService);
        var thoughtRestHandler = new ThoughtRestHandler(thoughtService);
        return List.of(startHandler, authHandler, errorHandler, thoughtsHandler, searchHandler, tagRestHandler, thoughtRestHandler);
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
