package by.tarasiuk.ct.model.connection;

import by.tarasiuk.ct.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connections creator.
 */
class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties connectionProperties;
    private static final String PROPERTIES_FILE = "database.properties";
    private static final String DB_URL = "database.url";
    private static final String DB_USER = "database.user";
    private static final String DB_PASSWORD = "database.password";

    /**
     * Static initialization.
     *
     */
    static {
        connectionProperties = PropertiesLoader.getProperties(PROPERTIES_FILE);

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Can't register driver: ", e);
            throw new RuntimeException("Can't register driver: ", e);
        }
    }

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