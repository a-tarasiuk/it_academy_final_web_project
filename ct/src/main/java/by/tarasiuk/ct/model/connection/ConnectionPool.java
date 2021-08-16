package by.tarasiuk.ct.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool for database.
 */
public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_POOL_MAX_ACTIVE_CONNECTIONS = 10;
    private static final int INITIAL_COUNTER_ATTEMPTS_CREATE_CONNECTION = 0;
    private static final int COUNT_ATTEMPTS_CREATE_CONNECTIONS = 3;
    private static final int DEFAULT_TIMEOUT_SECONDS = 0;
    private static final AtomicBoolean statusPoolCreated = new AtomicBoolean(false);;
    private static final ReentrantLock currentProxyConnectionLock= new ReentrantLock();
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeProxyConnections;
    private final BlockingQueue<ProxyConnection> activeProxyConnections;

    private ConnectionPool() {
        freeProxyConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_MAX_ACTIVE_CONNECTIONS);
        activeProxyConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_MAX_ACTIVE_CONNECTIONS);

        int countAttempts = INITIAL_COUNTER_ATTEMPTS_CREATE_CONNECTION;

        while (freeProxyConnections.size() < DEFAULT_POOL_MAX_ACTIVE_CONNECTIONS) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                LOGGER.info("{} was created.", connection);

                freeProxyConnections.offer(proxyConnection);
                LOGGER.info("{} was added on free proxy connections, size: {}.", connection, freeProxyConnections.size());
            } catch (SQLException e) {
                countAttempts++;
                LOGGER.warn("Database access error occurs or the url.", e);
            }

            if(countAttempts == COUNT_ATTEMPTS_CREATE_CONNECTIONS) {
                LOGGER.fatal("Can not created connection pool.");
                throw new RuntimeException("Can't created connection pool");
            }
        }
    }

    /**
     * Get connection pool instance.
     * @return instance connection pool
     */
    public static ConnectionPool getInstance() {
        if (!statusPoolCreated.get()) {
            currentProxyConnectionLock.lock();

            if (instance == null) {
                instance = new ConnectionPool();
                statusPoolCreated.set(true);
            }

            currentProxyConnectionLock.unlock();
        }

        return instance;
    }

    /**
     * Take free proxy connection.
     * @return proxy connection
     */
    public Connection getConnection() {
        ProxyConnection currentProxyConnection = null;

        try {
            currentProxyConnection = freeProxyConnections.take();
            activeProxyConnections.put(currentProxyConnection);
        } catch (InterruptedException e) {
            LOGGER.warn("{} interrupted.", Thread.currentThread().getName(), e);
            Thread.currentThread().interrupt();
        }

        return currentProxyConnection;
    }


    /**
     * Checking if a connection object is an object proxy connection.
     * If successful, try remove this proxy connection from active connections and put into free connections.
     * @param connection - Connection
     */
    public void putConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            LOGGER.error("Found incorrect type of connection: {}.", connection);
            return;
        }

        ProxyConnection currentConnection;
        try {
            currentConnection = activeProxyConnections.remove(connection) && connection.isValid(DEFAULT_TIMEOUT_SECONDS) ?
                    (ProxyConnection) connection : ConnectionFactory.createConnection();

            if(!currentConnection.getAutoCommit()) {
                currentConnection.setAutoCommit(true);
            }

            freeProxyConnections.put(currentConnection);
        } catch (SQLException e) {
            LOGGER.warn("Timeout period expires before the operation completes.", e);
        } catch (InterruptedException e) {
            LOGGER.warn("{} interrupted.", Thread.currentThread().getName(), e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Shutdown pool when application stops and then trying deregister drivers.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_MAX_ACTIVE_CONNECTIONS; i++) {
            try {
                freeProxyConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                LOGGER.warn("Can't really close the connection.", e);
            }
        }

        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver currentDriver = drivers.nextElement();

            try {
                DriverManager.deregisterDriver(currentDriver);
            } catch (SQLException e) {
                LOGGER.error("Cannot deregister driver: {}.", currentDriver, e);
            }
        }
    }
}