package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.connection.ConnectionPool;
import by.tarasiuk.ct.model.dao.provider.DaoProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao <E extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final DaoProvider daoProvider = DaoProvider.getInstance();
    protected ConnectionPool connectionPool = ConnectionPool.getInstance();

    public abstract List<Entity> findAll() throws DaoException;
    public abstract E update(E entity) throws DaoException;
    public abstract Optional<E> getEntityById(int id) throws DaoException;

    public void closeConnection(Connection connection) throws DaoException {
        if(connection != null) {
            try {
                if(!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }

                connection.close();
            } catch (SQLException e) {
                // todo
            }
        }
    }

    public void closeStatement(Statement statement) throws DaoException {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Database access error occurs when trying to close Statement object.", e);
                throw new DaoException("Database access error occurs when trying to close Statement object." + e);
            }
        }
    }
}
