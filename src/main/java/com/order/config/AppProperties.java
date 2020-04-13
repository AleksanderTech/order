package com.order.config;

import java.util.Properties;

public class AppProperties {

    public final Integer SERVER_PORT;
    public final String JDBC_USER;
    public final String JDBC_PASSWORD;
    public final String JDBC_URL;
    private final Properties properties;

    public AppProperties(Properties properties) {
        this.properties = properties;
        SERVER_PORT = Integer.parseInt(getProperty("server.port"));
        JDBC_USER = getProperty("jdbc.user");
        JDBC_PASSWORD = getProperty("jdbc.password");
        JDBC_URL = getProperty("jdbc.url");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
