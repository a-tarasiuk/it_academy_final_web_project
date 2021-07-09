package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    public void beginTransaction(BaseDao baseDao, BaseDao ... baseDaos) {
        if(connection == null) {
            connection = connectionPool.getConnection();
        } else {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("In the connection: {} failed to set auto commit in \"false\"", connection, e);
            }

            //baseDao.setConnection(connection);

            for (BaseDao daoElement: baseDaos) {
                //daoElement.setConnection(connection);
            }
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to complete the transaction with the connection: {}.", connection, e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Method is called on a closed connection or this Connection object is in auto-commit mode.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Auto-commit has been disabled.", e);
        }
    }
}
