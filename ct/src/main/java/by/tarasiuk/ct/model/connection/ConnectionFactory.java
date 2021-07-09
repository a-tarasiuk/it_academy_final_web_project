package by.tarasiuk.ct.model.connection;

import by.tarasiuk.ct.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties connectionProperties;

    private static final String PROPERTIES_FILE = "database.properties";
    private static final String DB_URL = "database.url";
    private static final String DB_USER = "database.user";
    private static final String DB_PASSWORD = "database.password";

    static {
        try {
            connectionProperties = PropertiesLoader.getProperties(PROPERTIES_FILE);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());   // todo (см. properties файл database.propertioes
        } catch (IOException e) {
            LOGGER.fatal("Properties cannot be loaded", e);
            throw new RuntimeException("Properties cannot be loaded", e);
        } catch (SQLException e) {
            LOGGER.fatal("Can't register driver: ", e);
            throw new RuntimeException("Can't register driver: ", e);
        }
    }

    /**
     * Prevent the {@code ConnectionFactory} class from being instantiated.
     */
    private ConnectionFactory() {
    }

    /**
     * Attempts to establish a connection to the given database URL.
     * The {@code DriverManager} attempts to select an appropriate driver
     * from the set of registered JDBC drivers.
     * <p>
     * @return A Connection to the URL {@code MYSQL_CONNECTION_URL} with
     * properties {@code connectionProperties}.
     * @throws SQLException if a database access error occurs or the url is
     *         {@code null}.
     */
    static ProxyConnection createConnection() throws SQLException {
        String dbUrlConnection = connectionProperties.getProperty(DB_URL);
        String dbUser = connectionProperties.getProperty(DB_USER);
        String dbPassword = connectionProperties.getProperty(DB_PASSWORD);

        Connection connection = DriverManager.getConnection(dbUrlConnection, dbUser, dbPassword);

        return new ProxyConnection(connection);
    }
}