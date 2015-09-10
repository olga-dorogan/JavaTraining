package com.custom.system;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olga on 04.08.15.
 */
@Singleton
@Startup
public class PropertyFileResolverImpl implements PropertyFileResolver {
    private static final String SYSTEM_PROPERTY = "application.properties";
    private Map<String, String> properties = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        System.out.println("==============================");
        System.out.println("property file resolver -- before init");
        //matches the property name as defined in the system-properties element in WildFly
        String propertyFile = System.getProperty(SYSTEM_PROPERTY);
        File file = new File(propertyFile);
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("Unable to load properties file" + e);
        }

        System.out.println("==============================");
        System.out.println("Properties: " + properties.toString());
        System.out.println("==============================");
        HashMap hashMap = new HashMap<>(properties);
        this.properties.putAll(hashMap);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }
}
