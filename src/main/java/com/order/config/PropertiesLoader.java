package com.order.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties load(String... args) {
        InputStream in;
        try {
            if (args.length == 0) {
                in = PropertiesLoader.class.getResourceAsStream("/app.properties");
            } else {
                in = new FileInputStream(args[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toProperties(in);
    }

    public static Properties toProperties(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
            stream.close();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
