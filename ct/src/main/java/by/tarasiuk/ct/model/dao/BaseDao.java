package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao <E extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected ConnectionPool connectionPool = ConnectionPool.getInstance();

    public abstract boolean createEntity(E entity) throws DaoException;
    public abstract List<E> findAll() throws DaoException;
    public abstract E updateEntity(E entity) throws DaoException;
    public abstract Optional<E> findEntityById(long id) throws DaoException;

    public void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                if(!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }

                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close connection '{}'.", connection, e);
            }
        }
    }

    public void closeStatement(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close statement '{}'.", statement, e);
            }
        }
    }
}
