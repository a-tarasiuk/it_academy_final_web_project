package by.tarasiuk.ct.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {
    private static final PropertiesLoader instance = PropertiesLoader.getInstance();

    private PropertiesLoader() {
    }

    protected static PropertiesLoader getInstance() {
        return instance;
    }

    public static Properties getProperties(String propertiesFileName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(propertiesFileName);

        Properties currentProperties = new Properties();
        currentProperties.load(inputStream);

        return currentProperties;
    }
}
