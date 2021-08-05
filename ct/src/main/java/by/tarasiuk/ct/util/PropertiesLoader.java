package by.tarasiuk.ct.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PropertiesLoader instance = PropertiesLoader.getInstance();

    private PropertiesLoader() {
    }

    protected static PropertiesLoader getInstance() {
        return instance;
    }

    public static Properties getProperties(String propertiesFileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(propertiesFileName);

        Properties currentProperties = new Properties();

        try {
            currentProperties.load(inputStream);
        } catch (IOException e) {
            LOGGER.fatal("Properties cannot be loaded.", e);
            throw new RuntimeException("Properties cannot be loaded.", e);
        }

        return currentProperties;
    }
}
